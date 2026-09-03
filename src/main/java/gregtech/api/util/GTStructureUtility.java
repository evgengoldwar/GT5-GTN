package gregtech.api.util;

import static com.gtnewhorizon.structurelib.structure.IStructureElement.PlaceResult.ACCEPT;
import static com.gtnewhorizon.structurelib.structure.IStructureElement.PlaceResult.REJECT;
import static com.gtnewhorizon.structurelib.structure.IStructureElement.PlaceResult.SKIP;
import static com.gtnewhorizon.structurelib.util.ItemStackPredicate.NBTMode.EXACT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizon.gtnhlib.util.CoordinatePacker;
import com.gtnewhorizon.structurelib.StructureLibAPI;
import com.gtnewhorizon.structurelib.structure.AutoPlaceEnvironment;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizon.structurelib.structure.IStructureElement;
import com.gtnewhorizon.structurelib.structure.IStructureElementNoPlacement;
import com.gtnewhorizon.structurelib.structure.StructureUtility;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.ITurnable;
import gregtech.api.metatileentity.implementations.MTEMultiBlockBase;
import gregtech.common.blocks.ItemMachines;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public class GTStructureUtility {

    // private static final Map<Class<?>, String> customNames = new HashMap<>();
    private GTStructureUtility() {
        throw new AssertionError("Not instantiable");
    }

    public static <T> HatchElementBuilder<T> buildHatchAdder() {
        return HatchElementBuilder.builder();
    }

    public static <T extends MTEMultiBlockBase> IStructureElement<T> activeCoils(IStructureElement<T> element) {
        return new ProxyStructureElement<>(element) {

            @Override
            public boolean check(T t, World world, int x, int y, int z) {
                if (!element.check(t, world, x, y, z)) return false;

                t.mCoils.add(CoordinatePacker.pack(x, y, z));

                return true;
            }
        };
    }

    @Nonnull
    public static Predicate<ItemStack> filterByMTEClass(List<? extends Class<? extends IMetaTileEntity>> list) {
        return is -> {
            IMetaTileEntity tile = ItemMachines.getMetaTileEntity(is);
            return tile != null && list.stream()
                .anyMatch(c -> c.isInstance(tile));
        };
    }

    public static <TMTE extends IMetaTileEntity> List<TMTE> extractMTEs(Class<TMTE> mteClass, ItemStack... stacks) {
        List<TMTE> mtes = new ArrayList<>();

        for (ItemStack stack : stacks) {
            IMetaTileEntity mte = ItemMachines.getMetaTileEntity(stack);

            assert mte != null;
            if (!mteClass.isAssignableFrom(mte.getClass()))
                throw new IllegalArgumentException(stack.getDisplayName() + " is not a " + mteClass);

            mtes.add(mteClass.cast(mte));
        }

        return mtes;
    }

    public interface MTEAdder<T, TMTE extends IMetaTileEntity> {

        boolean check(T t, TMTE mte, int tier);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static <T, TMTE extends IMetaTileEntity> IStructureElement<T> ofGenericMTETiered(Class<TMTE> mteClass,
        MTEAdder<T, TMTE> adder, List<TMTE> tiers) {
        Int2IntOpenHashMap tierMap = new Int2IntOpenHashMap();

        int i = 1;
        for (TMTE mte : tiers) {
            tierMap.put(
                mte.getBaseMetaTileEntity()
                    .getMetaTileID(),
                i++);
        }

        return new IStructureElement<>() {

            private TMTE getMTE(World world, int x, int y, int z) {
                if (!(world.getTileEntity(x, y, z) instanceof IGregTechTileEntity igte)) return null;

                IMetaTileEntity imte = igte.getMetaTileEntity();

                if (!(mteClass.isAssignableFrom(imte.getClass()))) return null;

                return mteClass.cast(imte);
            }

            private TMTE getPlaceable(ItemStack trigger) {
                int index = GTUtility.clamp(trigger.stackSize, 1, tiers.size());

                return GTDataUtils.getIndexSafe(tiers, index - 1);
            }

            @Override
            public boolean check(T t, World world, int x, int y, int z) {
                TMTE mte = getMTE(world, x, y, z);

                if (mte == null) return false;

                int tier = tierMap.getOrDefault(
                    mte.getBaseMetaTileEntity()
                        .getMetaTileID(),
                    -1);

                if (tier == -1) return false;

                return adder.check(t, mte, tier);
            }

            @Override
            public boolean couldBeValid(T t, World world, int x, int y, int z, ItemStack trigger) {
                TMTE wanted = getPlaceable(trigger);
                TMTE actual = getMTE(world, x, y, z);

                if (actual == null) return false;

                int tier = tierMap.getOrDefault(
                    actual.getBaseMetaTileEntity()
                        .getMetaTileID(),
                    -1);

                if (tier == -1) return false;

                return tiers.get(tier) != wanted;
            }

            @Override
            public boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
                StructureLibAPI.hintParticle(world, x, y, z, GregTechAPI.sBlockMachines, 0);
                return true;
            }

            @Override
            public boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
                TMTE mte = getPlaceable(trigger);

                ItemStack stack = mte.getStackForm(1);

                if (!(stack.getItem() instanceof ItemMachines itemMachines)) return false;

                boolean success = itemMachines
                    .placeBlockAt(stack, null, world, x, y, z, ForgeDirection.UP.ordinal(), 0.5f, 0.5f, 0.5f, 0);

                if (!success) return false;

                if (world.getTileEntity(x, y, z) instanceof ITurnable turnable) {
                    turnable.setFrontFacing(ForgeDirection.SOUTH);
                }

                return true;
            }

            @Override
            public BlocksToPlace getBlocksToPlace(T t, World world, int x, int y, int z, ItemStack trigger,
                AutoPlaceEnvironment env) {

                int index = GTUtility.clamp(trigger.stackSize, 0, tiers.size() - 1);
                TMTE mte = GTDataUtils.getIndexSafe(tiers, index);

                return BlocksToPlace.create(mte.getStackForm(1));
            }

            @Override
            public PlaceResult survivalPlaceBlock(T t, World world, int x, int y, int z, ItemStack trigger,
                AutoPlaceEnvironment env) {

                TMTE wanted = getPlaceable(trigger);
                TMTE actual = getMTE(world, x, y, z);

                if (actual == wanted) return SKIP;

                if (!StructureLibAPI.isBlockTriviallyReplaceable(world, x, y, z, env.getActor())) {
                    return REJECT;
                }

                ItemStack stack = wanted.getStackForm(1);

                PlaceResult result = StructureUtility.survivalPlaceBlock(
                    stack,
                    EXACT,
                    null,
                    false,
                    world,
                    x,
                    y,
                    z,
                    env.getSource(),
                    env.getActor(),
                    env.getChatter());

                if (result != ACCEPT) return result;

                if (world.getTileEntity(x, y, z) instanceof ITurnable turnable) {
                    turnable.setFrontFacing(ForgeDirection.SOUTH);
                }

                return result;
            }
        };
    }

    public static class ProxyStructureElement<T, E extends IStructureElement<T>> implements IStructureElement<T> {

        public final E proxiedElement;

        public ProxyStructureElement(E proxiedElement) {
            this.proxiedElement = proxiedElement;
        }

        @Override
        public boolean check(T t, World world, int x, int y, int z) {
            return proxiedElement.check(t, world, x, y, z);
        }

        @Override
        public boolean couldBeValid(T t, World world, int x, int y, int z, ItemStack trigger) {
            return proxiedElement.couldBeValid(t, world, x, y, z, trigger);
        }

        @Override
        public boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
            return proxiedElement.spawnHint(t, world, x, y, z, trigger);
        }

        @Override
        public boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
            return proxiedElement.placeBlock(t, world, x, y, z, trigger);
        }

        @Override
        public PlaceResult survivalPlaceBlock(T t, World world, int x, int y, int z, ItemStack trigger, IItemSource s,
            EntityPlayerMP actor, Consumer<IChatComponent> chatter) {
            return proxiedElement.survivalPlaceBlock(t, world, x, y, z, trigger, s, actor, chatter);
        }

        @Override
        public @Nullable BlocksToPlace getBlocksToPlace(T t, World world, int x, int y, int z, ItemStack trigger,
            AutoPlaceEnvironment env) {
            return proxiedElement.getBlocksToPlace(t, world, x, y, z, trigger, env);
        }

        @Override
        public PlaceResult survivalPlaceBlock(T t, World world, int x, int y, int z, ItemStack trigger,
            AutoPlaceEnvironment env) {
            return proxiedElement.survivalPlaceBlock(t, world, x, y, z, trigger, env);
        }

        @Override
        public IStructureElementNoPlacement<T> noPlacement() {
            return proxiedElement.noPlacement();
        }

        @Override
        public int getStepA() {
            return proxiedElement.getStepA();
        }

        @Override
        public int getStepB() {
            return proxiedElement.getStepB();
        }

        @Override
        public int getStepC() {
            return proxiedElement.getStepC();
        }

        @Override
        public boolean resetA() {
            return proxiedElement.resetA();
        }

        @Override
        public boolean resetB() {
            return proxiedElement.resetB();
        }

        @Override
        public boolean resetC() {
            return proxiedElement.resetC();
        }

        @Override
        public boolean isNavigating() {
            return proxiedElement.isNavigating();
        }

        @Override
        public @Nullable List<String> getDescription(T context) {
            return proxiedElement.getDescription(context);
        }
    }

    public static class ProxyHatchElement<T> implements IHatchElement<T> {

        public final IHatchElement<? super T> proxiedHatch;

        public ProxyHatchElement(IHatchElement<? super T> proxiedHatch) {
            this.proxiedHatch = proxiedHatch;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return proxiedHatch.mteClasses();
        }

        @Override
        public IGTHatchAdder<? super T> adder() {
            return proxiedHatch.adder();
        }

        @Override
        public String name() {
            return proxiedHatch.name();
        }

        @Override
        public String getDisplayName() {
            return proxiedHatch.getDisplayName();
        }

        @Override
        public long count(T t) {
            return proxiedHatch.count(t);
        }

        @Override
        public boolean matchesHatch(IMetaTileEntity mte) {
            return proxiedHatch.matchesHatch(mte);
        }
    }
}
