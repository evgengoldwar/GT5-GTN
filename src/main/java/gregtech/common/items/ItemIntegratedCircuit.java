package gregtech.common.items;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.GregTech;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.GuiFactories;
import com.cleanroommc.modularui.factory.PlayerInventoryGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GTLoggers;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.INetworkUpdatableItem;
import gregtech.api.items.GTGenericItem;
import gregtech.api.modularui2.GTGuiThemes;
import gregtech.api.modularui2.GTModularScreen;
import gregtech.common.gui.modularui.item.IntegratedCircuitGui;
import gregtech.crossmod.backhand.Backhand;

public class ItemIntegratedCircuit extends GTGenericItem
    implements INetworkUpdatableItem, IGuiHolder<PlayerInventoryGuiData> {

    public static final int MAX_CIRCUIT_NUMBER = 24;
    public static final List<ItemStack> NON_ZERO_VARIANTS = new ArrayList<>(MAX_CIRCUIT_NUMBER);
    public static final List<ItemStack> ALL_VARIANTS = new ArrayList<>(MAX_CIRCUIT_NUMBER + 1);
    protected final IIcon[] iconDamage = new IIcon[25];

    public ItemIntegratedCircuit() {
        super("integrated_circuit", "Programmed Circuit", "");
        setHasSubtypes(true);
        setMaxDamage(0);

        ItemList.Circuit_Integrated.set(this);

        ALL_VARIANTS.add(new ItemStack(this, 0, 0));
        for (int i = 1; i <= MAX_CIRCUIT_NUMBER; i++) {
            ItemStack aStack = new ItemStack(this, 0, i);
            NON_ZERO_VARIANTS.add(aStack);
            ALL_VARIANTS.add(aStack);
        }
    }

    private static String getModeString(int meta) {
        return switch ((byte) (meta >>> 8)) {
            case 0 -> "==";
            case 1 -> "<=";
            case 2 -> ">=";
            case 3 -> "<";
            case 4 -> ">";
            default -> "";
        };
    }

    private static String getConfigurationString(int meta) {
        return getModeString(meta) + " " + (byte) (meta & 0xFF);
    }

    @Override
    public void addAdditionalToolTips(List<String> list, ItemStack stack, EntityPlayer player) {
        super.addAdditionalToolTips(list, stack, player);
        list.add(
            StatCollector.translateToLocalFormatted(
                "GT5U.item.programmed_circuit.tooltip.0",
                getConfigurationString(getDamage(stack))));
        list.add(StatCollector.translateToLocal("GT5U.item.programmed_circuit.tooltip.1"));
        list.add(StatCollector.translateToLocal("GT5U.item.programmed_circuit.tooltip.2"));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        for (int i = 0; i < iconDamage.length; i++) {
            iconDamage[i] = iconRegister.registerIcon(GregTech.getResourcePath(getUnlocalizedName() + "/" + i));
        }
        if (GregTechAPI.sPostloadFinished) {
            GT_FML_LOGGER.debug("GTMod: Starting Item Icon Load Phase");
            GT_FML_LOGGER.info("GTMod: Starting Item Icon Load Phase");
            GregTechAPI.sItemIcons = iconRegister;
            for (Runnable tRunnable : GregTechAPI.sGTItemIconload) {
                try {
                    tRunnable.run();
                } catch (Exception e) {
                    GTLoggers.GT_FML_LOGGER.error("Error registering icons", e);
                }
            }
            GT_FML_LOGGER.debug("GTMod: Finished Item Icon Load Phase");
            GT_FML_LOGGER.info("GTMod: Finished Item Icon Load Phase");
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        byte circuitMode = ((byte) (meta & 0xFF)); // Mask out the MSB Comparison Mode Bits. See: getModeString
        return iconDamage[circuitMode < iconDamage.length ? circuitMode : 0];
    }

    @Override
    public boolean receive(ItemStack itemStack, EntityPlayerMP player, NBTTagCompound tag) {
        int meta = tag.hasKey("meta", Constants.NBT.TAG_BYTE) ? tag.getByte("meta") : -1;
        if (meta < 0 || meta > 24) {
            return true;
        }
        itemStack.setItemDamage(meta);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!(player instanceof FakePlayer) && !world.isRemote) {

            if (itemStack == Backhand.getOffhandItem(player)) {
                GuiFactories.playerInventory()
                    .openFromPlayerInventory(player, Backhand.getOffhandSlot(player));
            } else {
                GuiFactories.playerInventory()
                    .openFromMainHand(player);
            }
        }

        return super.onItemRightClick(itemStack, world, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModularScreen createScreen(PlayerInventoryGuiData data, ModularPanel mainPanel) {
        return new GTModularScreen(mainPanel, GTGuiThemes.STANDARD);
    }

    @Override
    public ModularPanel buildUI(PlayerInventoryGuiData data, PanelSyncManager syncManager, UISettings settings) {
        return new IntegratedCircuitGui(data).build();
    }
}
