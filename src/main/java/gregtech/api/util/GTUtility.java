package gregtech.api.util;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;
import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.COMPASS_DIRECTIONS;
import static gregtech.api.enums.GTValues.E;
import static gregtech.api.enums.GTValues.M;
import static gregtech.api.enums.GTValues.NW;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.Materials.FLUID_MAP;
import static gregtech.api.enums.Mods.Translocator;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UNKNOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Contract;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.google.auto.value.AutoValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.gtnewhorizon.gtnhlib.item.ItemStackNBT;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.alignment.enumerable.Flip;
import com.gtnewhorizon.structurelib.alignment.enumerable.Rotation;
import com.mojang.authlib.GameProfile;

import buildcraft.api.transport.IPipeTile;
import codechicken.translocator.TileItemTranslocator;
import cofh.api.transport.IItemDuct;
import cofh.asmhooks.block.BlockTickingWater;
import cofh.asmhooks.block.BlockWater;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModAPIManager;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GTLoggers;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.damagesources.GTDamageSources;
import gregtech.api.damagesources.GTDamageSources.DamageSourceHotItem;
import gregtech.api.enchants.EnchantmentRadioactivity;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.SoundResource;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.Textures;
import gregtech.api.hazards.HazardProtection;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.item.ItemStackSizeCalculator;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GTGenericItem;
import gregtech.api.items.ItemEnergyArmor;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.net.GTPacketSound;
import gregtech.api.objects.CollectorUtils;
import gregtech.api.objects.GTItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.threads.RunnableSound;
import gregtech.common.items.ItemGTToolbox;
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.common.items.toolbox.ToolboxUtil;
import gregtech.nei.FluidDisplayStackMode;
import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2Potion;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import it.unimi.dsi.fastutil.objects.Reference2LongOpenHashMap;

public class GTUtility {

    private static final Map<Fluid, List<ItemStack>> fluidToContainersMap = new IdentityHashMap<>(1024);
    public static boolean TE_CHECK = false, BC_CHECK = false, CHECK_ALL = true, RF_CHECK = false;
    public static Map<GTPlayedSound, Integer> sPlayedSoundMap = new HashMap<>();
    public static UUID defaultUuid = null;
    private static final Splitter NEWLINE_SPLITTER = Splitter.on("\\n")
        .omitEmptyStrings();
    private static Block DISTILLED_WATER_BLOCK;

    public static int safeInt(long number, int margin) {
        return number > Integer.MAX_VALUE - margin ? Integer.MAX_VALUE - margin : (int) number;
    }

    public static int safeInt(long number) {
        return number > V[V.length - 1] ? safeInt(V[V.length - 1], 1)
            : number < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) number;
    }

    public static int longToInt(long number) {
        return (int) Math.min(Integer.MAX_VALUE, number);
    }

    public static Field getField(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class<?>) aObject).getDeclaredField(aField)
                : (aObject instanceof String) ? Class.forName((String) aObject)
                    .getDeclaredField(aField)
                    : aObject.getClass()
                        .getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField;
        } catch (Exception e) {
            if (aLogErrors) GT_FML_LOGGER.error(e);
        }
        return null;
    }

    public static Object getFieldContent(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class<?>) aObject).getDeclaredField(aField)
                : (aObject instanceof String) ? Class.forName((String) aObject)
                    .getDeclaredField(aField)
                    : aObject.getClass()
                        .getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField.get(aObject instanceof Class || aObject instanceof String ? null : aObject);
        } catch (Exception e) {
            if (aLogErrors) GT_FML_LOGGER.error(e);
        }
        return null;
    }

    public static Object callMethod(Object aObject, String aMethod, boolean aPrivate, boolean aUseUpperCasedDataTypes,
        boolean aLogErrors, Object... aParameters) {
        try {
            Class<?>[] tParameterTypes = new Class<?>[aParameters.length];
            for (byte i = 0; i < aParameters.length; i++) {
                if (aParameters[i] instanceof Class) {
                    tParameterTypes[i] = (Class<?>) aParameters[i];
                    aParameters[i] = null;
                } else {
                    tParameterTypes[i] = aParameters[i].getClass();
                }
                if (!aUseUpperCasedDataTypes) {
                    if (tParameterTypes[i] == Boolean.class) tParameterTypes[i] = boolean.class;
                    else if (tParameterTypes[i] == Byte.class) tParameterTypes[i] = byte.class;
                    else if (tParameterTypes[i] == Short.class) tParameterTypes[i] = short.class;
                    else if (tParameterTypes[i] == Integer.class) tParameterTypes[i] = int.class;
                    else if (tParameterTypes[i] == Long.class) tParameterTypes[i] = long.class;
                    else if (tParameterTypes[i] == Float.class) tParameterTypes[i] = float.class;
                    else if (tParameterTypes[i] == Double.class) tParameterTypes[i] = double.class;
                }
            }

            Method tMethod = (aObject instanceof Class) ? ((Class<?>) aObject).getMethod(aMethod, tParameterTypes)
                : aObject.getClass()
                    .getMethod(aMethod, tParameterTypes);
            if (aPrivate) tMethod.setAccessible(true);
            return tMethod.invoke(aObject, aParameters);
        } catch (Exception e) {
            if (aLogErrors) GT_FML_LOGGER.error(e);
        }
        return null;
    }

    public static String getClassName(Object aObject) {
        if (aObject == null) return "null";
        return aObject.getClass()
            .getName()
            .substring(
                aObject.getClass()
                    .getName()
                    .lastIndexOf(".") + 1);
    }

    public static boolean getFullInvisibility(EntityPlayer player) {
        if (!player.isInvisible()) return false;
        for (ItemStack stack : player.inventory.armorInventory) {
            if (stack == null || !(stack.getItem() instanceof ItemEnergyArmor)) continue;
            if ((((ItemEnergyArmor) stack.getItem()).mSpecials & 512) != 0) {
                if (GTModHandler.canUseElectricItem(stack, 10000)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the voltage tier corresponding to an amount of EU, capped to 15 (MAX+)
     *
     * @param l The amount of EU
     * @return Corresponding voltage tier in the range 0-15
     */
    public static byte getTier(long l) {
        if (l > V[14]) return 15;
        if (l <= V[0]) return 0;

        // numberOfLeadingZeros is implemented in hardware by x86 LZCNT
        // and is extremely efficient (takes only a couple of hardware cycles)
        // (64 - numberOfLeadingZeros(l - 1)) = ceil(log_2(l))
        int log2L = 64 - Long.numberOfLeadingZeros(l - 1);

        return (byte) ((log2L - 2) / 2);
    }

    public static long getAmperageForTier(long voltage, byte tier) {
        return ceilDiv(voltage, GTValues.V[tier]);
    }

    public static double getExactAmperageForTier(long voltage, byte tier) {
        if (GTValues.V[tier] <= 0) {
            return 0.0D;
        }

        return (double) voltage / (double) GTValues.V[tier];
    }

    public static String getColoredTierNameFromVoltage(long voltage) {
        return getColoredTierNameFromTier(getTier(voltage));
    }

    public static String getColoredTierNameFromTier(byte tier) {
        return GTValues.TIER_COLORS[tier] + GTValues.VN[tier] + EnumChatFormatting.RESET;
    }

    /**
     * Applies secondary formatting to tooltip text
     *
     * @param tooltip the tooltip text
     * @return formatted tooltip string
     */
    public static String getColoredSecondaryTooltip(String tooltip) {
        return EnumChatFormatting.GRAY + tooltip + EnumChatFormatting.RESET;
    }

    /**
     * @return e.g. {@code " (LV)"}
     */
    @Nonnull
    public static String getTierNameWithParentheses(long voltage) {
        byte tier = getTier(voltage);
        tier = tier < 1 ? 1 : tier;
        String color = GTValues.TIER_COLORS[tier];
        return translateToLocalFormatted(
            "GT5U.gui.text.tier_name_with_parentheses",
            color + GTValues.VN[tier] + EnumChatFormatting.RESET);
    }

    public static String getForgeDirectionNameKey(ForgeDirection side) {
        return switch (side) {
            case DOWN -> "GT5U.waila.facing.down";
            case UP -> "GT5U.waila.facing.up";
            case NORTH -> "GT5U.waila.facing.north";
            case SOUTH -> "GT5U.waila.facing.south";
            case WEST -> "GT5U.waila.facing.west";
            case EAST -> "GT5U.waila.facing.east";
            case UNKNOWN -> "GT5U.waila.facing.unknown";
        };
    }

    /**
     * @deprecated Use {@link #sendChatTrans} instead.
     */
    @Deprecated
    public static void sendChatToPlayer(EntityPlayer player, String message) {
        if (message != null) {
            message = processFormatStacks(message);
            player.addChatComponentMessage(new ChatComponentText(message));
        }
    }

    /**
     * Send a translated chat message to the player.
     * Example usage: {@code GTUtility.sendChatTrans(player, "gregtech.chat.example", arg1, arg2);}
     *
     * @param player     The player who will receive the message.
     * @param messageKey The lang key of the translation. The text corresponding to the key must only contain
     *                   placeholder '%s' or positioned one '%1$s'; otherwise, it cannot be translated.
     * @param args       Substitutions for `%s` in the translation. {@link IChatComponent} will be converted to their
     *                   unformatted text.
     */
    public static void sendChatTrans(EntityPlayer player, @Nonnull String messageKey, Object... args) {
        player.addChatComponentMessage(new ChatComponentTranslation(messageKey, args));
    }

    /**
     * Send a chat component to the player. Sometimes we need it to send complex messages,
     * such as multiple chat components in a single line.
     *
     * @param player    The player who will receive the message.
     * @param component The chat component to be sent.
     */
    public static void sendChatComp(EntityPlayer player, @Nonnull IChatComponent component) {
        player.addChatComponentMessage(component);
    }

    /** Uses thread analysis, works on dedicated servers. */
    public static boolean isServer() {
        return FMLCommonHandler.instance()
            .getEffectiveSide()
            .isServer();
    }

    /** Uses thread analysis, works on dedicated servers. */
    public static boolean isClient() {
        return FMLCommonHandler.instance()
            .getEffectiveSide()
            .isClient();
    }

    private static final char FORMAT_ESCAPE = '§';
    public static final String FORMAT_PUSH_STACK = FORMAT_ESCAPE + "s";
    public static final String FORMAT_POP_STACK = FORMAT_ESCAPE + "t";

    /**
     * Pre-processes a localized chat message with custom format push/pop instructions. This allows nested localizations
     * to set their own format without clobbering whatever came before, without needing any extra context info.
     * <p>
     * </p>
     * Example: {@code §a§lHello §s§eWorld§t!} is transformed into {@code §a§lHello §eWorld§a§l!}
     */
    public static String processFormatStacks(String message) {
        // Short circuit if there aren't any pops, because pops mutate while pushes don't.
        // Invalid codes are ignored by the font renderer so it won't cause problems if we ignore erroneous pushes.
        if (!message.contains(FORMAT_POP_STACK)) return message;

        StringBuilder out = new StringBuilder();
        out.ensureCapacity(message.length() * 6 / 5);

        int len = message.length();

        ArrayDeque<String> stack = new ArrayDeque<>();
        String currentFormat = "";

        int start = 0;

        while (start < len) {
            // Find the next format escape char
            int end = message.indexOf(FORMAT_ESCAPE, start);

            // If we hit the end of the string, push the rest of the input and stop
            if (end == -1) {
                out.append(message, start, len);
                break;
            }

            // If there was any text from the end of the previous code to the start of this one, add it to the output
            // buffer
            if (end > start) {
                out.append(message, start, end);
            }

            // If the current format escape char has a code after it, check it
            if (end < len - 1) {
                char code = message.charAt(end + 1);

                if (code >= '0' && code <= '9' || code >= 'a' && code <= 'f') {
                    // Colours, use as-is and erase the previous format
                    currentFormat = "" + FORMAT_ESCAPE + code;
                    out.append(FORMAT_ESCAPE)
                        .append(code);
                } else if (code >= 'k' && code <= 'o') {
                    // Styles, use as-is and append to the colour style (note: this may act up with repeated style
                    // codes but it shouldn't break anything).
                    currentFormat = currentFormat + FORMAT_ESCAPE + code;
                    out.append(FORMAT_ESCAPE)
                        .append(code);
                } else if (code == 'r') {
                    // Reset, use as-is and clear the format
                    currentFormat = "";
                    out.append(FORMAT_ESCAPE)
                        .append(code);
                } else if (code == 's') {
                    // Push, save the current format and don't emit to the output buffer
                    stack.push(currentFormat.isEmpty() ? "§r" : currentFormat);
                } else if (code == 't') {
                    // Pop, restore the top format and don't emit to the output buffer
                    currentFormat = stack.isEmpty() ? "§r" : stack.pop();
                    out.append(currentFormat);
                }

                // Skip the format escape along with its code
                start = end + 2;
            }
        }

        // A format code at the very end has nothing left to format, so a pop that lands there only emits noise.
        // Drop it, otherwise consumers that match whole lines (tooltip separators, for one) never see a clean line.
        while (out.length() >= 2 && out.charAt(out.length() - 2) == FORMAT_ESCAPE) {
            out.setLength(out.length() - 2);
        }

        return out.toString();
    }

    public static void addSeparatorIfNeeded(List<String> tooltip) {
        if (!tooltip.isEmpty() && !tooltip.get(tooltip.size() - 1)
            .trim()
            .isEmpty()) {
            tooltip.add("");
        }
    }

    public static final String[] COVER_DIRECTION_NAMES = new String[] { "GT5U.interface.coverTabs.down",
        "GT5U.interface.coverTabs.up", "GT5U.interface.coverTabs.north", "GT5U.interface.coverTabs.south",
        "GT5U.interface.coverTabs.west", "GT5U.interface.coverTabs.east" };

    public static String getUnlocalizedSideName(ForgeDirection side) {
        return GTUtility.COVER_DIRECTION_NAMES[side.ordinal()];
    }

    public static void checkAvailabilities() {
        if (CHECK_ALL) {
            TE_CHECK = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|transport");
            BC_CHECK = ModAPIManager.INSTANCE.hasAPI("BuildCraftAPI|transport");
            RF_CHECK = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");
            CHECK_ALL = false;
        }
    }

    public static boolean isConnectableNonInventoryPipe(TileEntity tileEntity, ForgeDirection side) {
        if (tileEntity == null) return false;
        checkAvailabilities();
        if (TE_CHECK && tileEntity instanceof IItemDuct) return true;
        if (BC_CHECK && tileEntity instanceof IPipeTile pipeTile) return pipeTile.isPipeConnected(side);
        return Translocator.isModLoaded() && tileEntity instanceof TileItemTranslocator;
    }

    public static List<ItemStack> wrapInventory(IInventory inv) {
        int sizeInventory = inv.getSizeInventory();

        return new AbstractList<>() {

            @Override
            public ItemStack get(int index) {
                return inv.getStackInSlot(index);
            }

            @Override
            public ItemStack set(int index, ItemStack element) {
                ItemStack existing = inv.getStackInSlot(index);
                inv.setInventorySlotContents(index, element);
                return existing;
            }

            @Override
            public int size() {
                return sizeInventory;
            }
        };
    }

    public static void compactInventory(IMetaTileEntity imte) {
        compactInventory(imte, 0, imte.getSizeInventory());
    }

    public static boolean compactInventory(IMetaTileEntity imte, int start, int end) {
        ItemStackSizeCalculator stackSizes = (slot, stack) -> imte.getStackSizeLimit(slot + start, stack);

        if (compactInventory(wrapInventory(imte).subList(start, end), stackSizes)) {
            imte.markDirty();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Only use if you are certain you can ignore {@link IInventory#getInventoryStackLimit}
     */
    public static boolean compactInventory(List<ItemStack> inv, int start, int end) {
        return compactInventory(inv.subList(start, end), (slot, stack) -> stack.getMaxStackSize());
    }

    private static final ThreadLocal<Map<ItemStack, ObjectArrayList<ObjectIntPair<ItemStack>>>> slotsSupplier = ThreadLocal
        .withInitial(() -> new Object2ObjectOpenCustomHashMap<>(GTItemStack.ITEMSTACK_HASH_STRATEGY_NBT_SENSITIVE));

    /**
     * Compacts an inventory like this:
     * Empty_0, Iron_48, Iron_64, Empty_0, Gold_32, Iron_32, Gold_16
     * into this:
     * Iron_64, Iron_64, Gold_48, Iron_16
     */
    public static boolean compactInventory(List<ItemStack> inv, ItemStackSizeCalculator stackSizes) {
        int len = inv.size();

        // Filter each ItemStack into their own lists (grouped by Item, meta, and NBT).
        Map<ItemStack, ObjectArrayList<ObjectIntPair<ItemStack>>> slots = slotsSupplier.get();
        slots.clear();

        for (int i = 0; i < len; i++) {
            ItemStack stack = inv.get(i);

            if (stack == null) continue;

            slots.computeIfAbsent(stack, ignored -> new ObjectArrayList<>())
                .add(ObjectIntPair.of(stack, i));
        }

        MutableBoolean didSomething = new MutableBoolean(false);

        // For each ItemStack, merge stacks from the end of the list to the front
        slots.forEach((ignored, stacks) -> {
            int stackLen = stacks.size();

            int insert = 0;
            int extract = stackLen - 1;

            while (insert < stackLen && insert < extract) {
                // Grab the next stack from the front of the list, to insert into if possible
                var toInflate = stacks.get(insert);
                ItemStack inflateStack = toInflate.left();

                int maxStack = stackSizes.getSlotStackLimit(toInflate.rightInt(), inflateStack);
                int remaining = maxStack - inflateStack.stackSize;

                // Scan from the end of the list to the current stack, and try to move items from those stacks into the
                // current stack
                while (insert < extract) {
                    var toBeExtracted = stacks.get(extract);

                    int toTransfer = Math.min(toBeExtracted.left().stackSize, remaining);

                    toBeExtracted.left().stackSize -= toTransfer;
                    inflateStack.stackSize += toTransfer;
                    remaining -= toTransfer;

                    didSomething.setValue(true);

                    if (toBeExtracted.left().stackSize <= 0) {
                        inv.set(toBeExtracted.rightInt(), null);
                        extract--;
                    }

                    if (inflateStack.stackSize >= maxStack) {
                        break;
                    }
                }

                insert++;
            }
        });

        int insert = 0;

        // Put all stacks into the first slots, contiguously
        while (insert < len) {
            if (inv.get(insert) == null) {
                ItemStack stack = null;

                int extract = insert + 1;

                while (extract < len && (stack = inv.get(extract)) == null) {
                    extract++;
                }

                if (stack != null) {
                    inv.set(insert, stack);
                    inv.set(extract, null);
                    didSomething.setValue(true);
                } else {
                    break;
                }
            }

            insert++;
        }

        slots.clear();

        return didSomething.booleanValue();
    }

    public static boolean cleanInventory(IInventory inv) {
        if (cleanInventory(wrapInventory(inv))) {
            inv.markDirty();
            return true;
        } else {
            return false;
        }
    }

    public static boolean cleanInventory(IMetaTileEntity imte, int start, int end) {
        if (cleanInventory(wrapInventory(imte).subList(start, end))) {
            imte.markDirty();
            return true;
        } else {
            return false;
        }
    }

    public static boolean cleanInventory(List<ItemStack> inv) {
        boolean didSomething = false;

        for (int i = 0, invSize = inv.size(); i < invSize; i++) {
            ItemStack stack = inv.get(i);

            if (stack != null && (stack.getItem() == null || stack.stackSize <= 0)) {
                inv.set(i, null);
                didSomething = true;
            }
        }

        return didSomething;
    }

    /**
     * Drops the item to the world.
     * <p>
     * NOTE: the stack is directly passed to the entity, so you should not continue using it.
     *
     * @param world    the world to spawn the item
     * @param x        the x coord to spawn the item
     * @param y        the y coord to spawn the item
     * @param z        the z coord to spawn the item
     * @param stack    the stack to spawn
     * @param noMotion {@code true} to remove the initial motion when spawned to the world
     */
    public static void dropItem(World world, double x, double y, double z, @Nullable ItemStack stack,
        boolean noMotion) {
        if (isStackInvalid(stack)) return;
        EntityItem entity = new EntityItem(world, x, y, z, stack);
        if (noMotion) {
            // remove initial motion
            entity.motionX = 0;
            entity.motionY = 0;
            entity.motionZ = 0;
        }
        world.spawnEntityInWorld(entity);
    }

    /**
     * @see #dropItem(World, double, double, double, ItemStack, boolean)
     */
    public static void dropItem(World world, double x, double y, double z, @Nullable ItemStack stack) {
        dropItem(world, x, y, z, stack, false);
    }

    /**
     * Move up to maxAmount amount of fluid from source to dest, with optional filtering via allowMove. note that this
     * filter cannot bypass filtering done by IFluidHandlers themselves.
     * <p>
     * this overload will assume the fill side is the opposite of drainSide
     *
     * @param source    tank to drain from. method become noop if this is null
     * @param dest      tank to fill to. method become noop if this is null
     * @param drainSide side used during draining operation
     * @param maxAmount max amount of fluid to transfer. method become noop if this is not a positive integer
     * @param allowMove filter. can be null to signal all fluids are accepted
     */
    public static void moveFluid(IFluidHandler source, IFluidHandler dest, ForgeDirection drainSide, int maxAmount,
        @Nullable Predicate<FluidStack> allowMove) {
        moveFluid(source, dest, drainSide, drainSide.getOpposite(), maxAmount, allowMove);
    }

    /**
     * Move up to maxAmount amount of fluid from source to dest, with optional filtering via allowMove. note that this
     * filter cannot bypass filtering done by IFluidHandlers themselves.
     *
     * @param source    tank to drain from. method become noop if this is null
     * @param dest      tank to fill to. method become noop if this is null
     * @param drainSide side used during draining operation
     * @param fillSide  side used during filling operation
     * @param maxAmount max amount of fluid to transfer. method become noop if this is not a positive integer
     * @param allowMove filter. can be null to signal all fluids are accepted
     */
    public static void moveFluid(IFluidHandler source, IFluidHandler dest, ForgeDirection drainSide,
        ForgeDirection fillSide, int maxAmount, @Nullable Predicate<FluidStack> allowMove) {
        if (source == null || dest == null || maxAmount <= 0) return;
        FluidStack liquid = source.drain(drainSide, maxAmount, false);
        if (liquid == null) return;
        liquid = liquid.copy();
        liquid.amount = dest.fill(fillSide, liquid, false);
        if (liquid.amount > 0 && (allowMove == null || allowMove.test(liquid))) {
            dest.fill(fillSide, source.drain(drainSide, liquid.amount, true), true);
        }
    }

    public static boolean areFluidsEqual(FluidStack aFluid1, FluidStack aFluid2) {
        return areFluidsEqual(aFluid1, aFluid2, false);
    }

    public static boolean areFluidsEqual(FluidStack aFluid1, FluidStack aFluid2, boolean aIgnoreNBT) {
        return aFluid1 != null && aFluid2 != null
            && aFluid1.getFluid() == aFluid2.getFluid()
            && (aIgnoreNBT || ((aFluid1.tag == null) == (aFluid2.tag == null))
                && (aFluid1.tag == null || aFluid1.tag.equals(aFluid2.tag)));
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
        return areStacksEqual(aStack1, aStack2, false);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return aStack1 != null && aStack2 != null
            && aStack1.getItem() == aStack2.getItem()
            && (Items.feather.getDamage(aStack1) == Items.feather.getDamage(aStack2)
                || Items.feather.getDamage(aStack1) == WILDCARD
                || Items.feather.getDamage(aStack2) == WILDCARD)
            && (aIgnoreNBT || (((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null))
                && (aStack1.getTagCompound() == null || aStack1.getTagCompound()
                    .equals(aStack2.getTagCompound()))));
    }

    public static boolean areStacksEqualOrNull(ItemStack stack1, ItemStack stack2) {
        return (stack1 == null && stack2 == null) || GTUtility.areStacksEqual(stack1, stack2);
    }

    private static boolean areStacksEqualExtended(ItemStack lhs, ItemStack rhs, boolean ignoreStackSize,
        boolean ignoreNBT) {
        if (lhs == null) return rhs == null;
        if (rhs == null) return false;
        return lhs.getItem() == rhs.getItem()
            && (ignoreNBT || Objects.equals(lhs.stackTagCompound, rhs.stackTagCompound))
            && (ignoreStackSize || lhs.stackSize == rhs.stackSize);
    }

    public static boolean areUnificationsEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return areStacksEqual(
            GTOreDictUnificator.get_nocopy(aStack1),
            GTOreDictUnificator.get_nocopy(aStack2),
            aIgnoreNBT);
    }

    public static String getFluidName(Fluid aFluid, boolean aLocalized) {
        if (aFluid == null) return E;
        if (!aLocalized) return aFluid.getUnlocalizedName();
        return aFluid.getLocalizedName();
    }

    public static String getFluidName(FluidStack aFluid, boolean aLocalized) {
        if (aFluid == null) return E;
        return getFluidName(aFluid.getFluid(), aLocalized);
    }

    public static void insertInFluidToContainersMap(FluidContainerData data) {
        fluidToContainersMap.computeIfAbsent(data.fluid.getFluid(), $ -> new ArrayList<>())
            .add(data.filledContainer);
    }

    public static List<ItemStack> getContainersFromFluid(FluidStack fluidStack) {
        if (fluidStack != null) {
            List<ItemStack> containers = fluidToContainersMap.get(fluidStack.getFluid());
            if (containers != null) return containers;
        }
        return new ArrayList<>();
    }

    public static ItemStack fillFluidContainer(FluidStack fluid, ItemStack stack, boolean removeFluidDirectly,
        boolean checkIFluidContainerItems) {
        if (isStackInvalid(stack) || fluid == null) return null;

        ItemStack bottle = new ItemStack(Items.glass_bottle);

        if (GTModHandler.isWater(fluid) && stack.isItemEqual(bottle)) {
            if (fluid.amount >= 1000) {
                return new ItemStack(Items.potionitem, 1, 0);
            }
            return null;
        }

        if (checkIFluidContainerItems && stack.getItem() instanceof IFluidContainerItem container
            && container.getFluid(stack) == null
            && container.getCapacity(stack) > 0
            && container.getCapacity(stack) <= fluid.amount) {

            ItemStack stackCopy = copyAmount(1, stack);
            int filled = container.fill(stackCopy, fluid, true);
            if (removeFluidDirectly) {
                fluid.amount -= filled;
            }
            return stackCopy;
        }

        ItemStack fullContainer = FluidContainerRegistry.fillFluidContainer(fluid, stack);
        if (fullContainer == null) return null;
        if (removeFluidDirectly) {
            fluid.amount -= FluidContainerRegistry.getContainerCapacity(fullContainer);
        }
        return fullContainer;
    }

    public static int calculateRecipeEU(Materials aMaterial, int defaultRecipeEUPerTick) {
        return aMaterial.getProcessingMaterialTierEU() == 0 ? defaultRecipeEUPerTick
            : aMaterial.getProcessingMaterialTierEU();
    }

    public static ItemStack getFluidDisplayStack(FluidStack fluid, FluidDisplayStackMode stackMode) {
        return getFluidDisplayStack(
            fluid,
            stackMode == FluidDisplayStackMode.SHOWN,
            stackMode == FluidDisplayStackMode.HIDDEN);
    }

    public static ItemStack getFluidDisplayStack(Fluid aFluid) {
        return aFluid == null ? null : getFluidDisplayStack(new FluidStack(aFluid, 0), false);
    }

    public static ItemStack getFluidDisplayStack(FluidStack aFluid, boolean aUseStackSize) {
        return getFluidDisplayStack(aFluid, aUseStackSize, false);
    }

    public static ItemStack getFluidDisplayStack(FluidStack aFluid, boolean aUseStackSize, boolean aHideStackSize) {
        if (aFluid == null || aFluid.getFluid() == null) return null;
        int tmp = 0;
        try {
            tmp = aFluid.getFluid()
                .getID();
        } catch (Exception e) {
            GT_FML_LOGGER.error(e);
        }
        ItemStack rStack = ItemList.Display_Fluid.getWithDamage(1, tmp);
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setLong("mFluidDisplayAmount", aUseStackSize ? aFluid.amount : 0);
        tNBT.setLong(
            "mFluidDisplayHeat",
            aFluid.getFluid()
                .getTemperature(aFluid));
        tNBT.setBoolean(
            "mFluidState",
            aFluid.getFluid()
                .isGaseous(aFluid));
        tNBT.setBoolean("mHideStackSize", aHideStackSize);
        try {
            tNBT.setString("mFluidMaterialName", FLUID_MAP.get(aFluid.getFluid()).mName);
        } catch (Exception ignored) {}
        rStack.setTagCompound(tNBT);
        return rStack;
    }

    public static FluidStack getFluidFromDisplayStack(ItemStack aDisplayStack) {
        if (!isStackValid(aDisplayStack) || aDisplayStack.getItem() != ItemList.Display_Fluid.getItem()
            || !aDisplayStack.hasTagCompound()) return null;
        Fluid tFluid = FluidRegistry.getFluid(
            ItemList.Display_Fluid.getItem()
                .getDamage(aDisplayStack));
        return new FluidStack(
            tFluid,
            (int) aDisplayStack.getTagCompound()
                .getLong("mFluidDisplayAmount"));
    }

    public static FluidStack getFluidForFilledItem(ItemStack stack, boolean checkIFluidContainerItems) {
        if (isStackInvalid(stack)) return null;

        if (checkIFluidContainerItems && stack.getItem() instanceof IFluidContainerItem container) {
            return container.getFluid(stack);
        }

        return FluidContainerRegistry.getFluidForFilledItem(stack);
    }

    /**
     * Get empty fluid container from filled one.
     */
    public static ItemStack getContainerForFilledItem(ItemStack stack, boolean checkIFluidContainerItems) {
        if (isStackInvalid(stack)) return null;

        if (checkIFluidContainerItems && stack.getItem() instanceof IFluidContainerItem container
            && container.getCapacity(stack) > 0) {

            ItemStack stackCopy = copyAmount(1, stack);
            FluidStack drained = container.drain(stackCopy, Integer.MAX_VALUE, true);
            if (drained == null || drained.amount == 0) {
                return null;
            }
            return stackCopy;
        }

        return FluidContainerRegistry.drainFluidContainer(stack);
    }

    /**
     * This is NOT meant for fluid manipulation! It's for getting item container, which is generally used for crafting
     * recipes. While it also works for many of the fluid containers, some don't.
     * <p>
     * Use {@link #getContainerForFilledItem} for getting empty fluid container.
     */
    public static ItemStack getContainerItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack)) return null;
        if (aStack.getItem()
            .hasContainerItem(aStack))
            return aStack.getItem()
                .getContainerItem(aStack);
        /*
         * These are all special Cases, in which it is intended to have only GT Blocks outputting those Container Items
         */
        if (ItemList.Cell_Empty.isStackEqual(aStack, false, true)) return null;
        if (aStack.getItem() == Items.potionitem || aStack.getItem() == Items.experience_bottle) return new ItemStack(Items.glass_bottle, 1);

        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem
            && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            ItemStack tStack = copyAmount(1, aStack);
            ((IFluidContainerItem) aStack.getItem()).drain(tStack, Integer.MAX_VALUE, true);
            if (!areStacksEqual(aStack, tStack)) return tStack;
            return null;
        }

        int tCapsuleCount = GTModHandler.getCapsuleCellContainerCount(aStack);
        if (tCapsuleCount > 0) return ItemList.Cell_Empty.get(tCapsuleCount);

        if (ItemList.IC2_ForgeHammer.isStackEqual(aStack) || ItemList.IC2_WireCutter.isStackEqual(aStack))
            return copyMetaData(Items.feather.getDamage(aStack) + 1, aStack);
        return null;
    }

    public static FluidStack getFluidFromContainerOrFluidDisplay(ItemStack stack) {
        FluidStack fluidStack = GTUtility.getFluidForFilledItem(stack, true);
        if (fluidStack == null) {
            fluidStack = GTUtility.getFluidFromDisplayStack(stack);
        }
        return fluidStack;
    }

    public static Object2LongOpenHashMap<ItemId> getItemStackHistogram(Iterable<ItemStack> stacks) {
        Object2LongOpenHashMap<ItemId> histogram = new Object2LongOpenHashMap<>();

        if (stacks == null) return histogram;

        for (ItemStack stack : stacks) {
            if (stack == null || stack.getItem() == null) continue;
            histogram.addTo(ItemId.create(stack), stack.stackSize);
        }

        return histogram;
    }

    public static Object2LongOpenHashMap<FluidId> getFluidStackHistogram(Iterable<FluidStack> stacks) {
        Object2LongOpenHashMap<FluidId> histogram = new Object2LongOpenHashMap<>();

        if (stacks == null) return histogram;

        for (FluidStack stack : stacks) {
            if (stack == null || stack.getFluid() == null) continue;
            histogram.addTo(FluidId.create(stack), stack.amount);
        }

        return histogram;
    }

    public static Iterable<NBTTagCompound> getCompoundTagList(NBTTagCompound tag, String name) {
        return tag.getTagList(name, NBT.TAG_COMPOUND).tagList;
    }

    public static synchronized boolean removeIC2BottleRecipe(ItemStack aContainer, ItemStack aInput,
        Map<ICannerBottleRecipeManager.Input, RecipeOutput> aRecipeList, ItemStack aOutput) {
        if ((isStackInvalid(aInput) && isStackInvalid(aOutput) && isStackInvalid(aContainer)) || aRecipeList == null)
            return false;
        boolean rReturn = false;
        Iterator<Map.Entry<ICannerBottleRecipeManager.Input, RecipeOutput>> tIterator = aRecipeList.entrySet()
            .iterator();
        aOutput = GTOreDictUnificator.get(aOutput);
        while (tIterator.hasNext()) {
            Map.Entry<ICannerBottleRecipeManager.Input, RecipeOutput> tEntry = tIterator.next();
            if (aInput == null || tEntry.getKey()
                .matches(aContainer, aInput)) {
                List<ItemStack> tList = tEntry.getValue().items;
                if (tList != null) for (ItemStack tOutput : tList)
                    if (aOutput == null || areStacksEqual(GTOreDictUnificator.get(tOutput), aOutput)) {
                        tIterator.remove();
                        rReturn = true;
                        break;
                    }
            }
        }
        return rReturn;
    }

    public static synchronized boolean removeSimpleIC2MachineRecipe(ItemStack aInput,
        Map<IRecipeInput, RecipeOutput> aRecipeList, ItemStack aOutput) {
        if ((isStackInvalid(aInput) && isStackInvalid(aOutput)) || aRecipeList == null) return false;
        boolean rReturn = false;
        Iterator<Map.Entry<IRecipeInput, RecipeOutput>> tIterator = aRecipeList.entrySet()
            .iterator();
        aOutput = GTOreDictUnificator.get(aOutput);
        while (tIterator.hasNext()) {
            Map.Entry<IRecipeInput, RecipeOutput> tEntry = tIterator.next();
            if (aInput == null || tEntry.getKey()
                .matches(aInput)) {
                List<ItemStack> tList = tEntry.getValue().items;
                if (tList != null) for (ItemStack tOutput : tList)
                    if (aOutput == null || areStacksEqual(GTOreDictUnificator.get(tOutput), aOutput)) {
                        tIterator.remove();
                        rReturn = true;
                        break;
                    }
            }
        }
        return rReturn;
    }

    public static ItemStack getWrittenBook(String aMapping, ItemStack aStackToPutNBT) {
        if (isStringInvalid(aMapping)) return null;
        ItemStack rStack = GregTechAPI.sBookList.get(aMapping);
        if (rStack == null) return aStackToPutNBT;
        if (aStackToPutNBT != null) {
            aStackToPutNBT.setTagCompound(rStack.getTagCompound());
            return aStackToPutNBT;
        }
        return copyAmount(1, rStack);
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength) {
        if (aSoundName == null) return false;
        return doSoundAtClient(aSoundName, aTimeUntilNextSound, aSoundStrength, GTMod.GT.getThePlayer());
    }

    public static boolean doSoundAtClient(SoundResource sound, int aTimeUntilNextSound, float aSoundStrength) {
        return doSoundAtClient(sound.resourceLocation, aTimeUntilNextSound, aSoundStrength, GTMod.GT.getThePlayer());
    }

    public static boolean doSoundAtClient(ResourceLocation aSoundResourceLocation, int aTimeUntilNextSound,
        float aSoundStrength) {
        return doSoundAtClient(aSoundResourceLocation, aTimeUntilNextSound, aSoundStrength, GTMod.GT.getThePlayer());
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength,
        Entity aEntity) {
        if (aEntity == null || aSoundName == null) return false;
        return doSoundAtClient(
            aSoundName,
            aTimeUntilNextSound,
            aSoundStrength,
            aEntity.posX,
            aEntity.posY,
            aEntity.posZ);
    }

    public static boolean doSoundAtClient(ResourceLocation aSoundResourceLocation, int aTimeUntilNextSound,
        float aSoundStrength, Entity aEntity) {
        if (aEntity == null) return false;
        return doSoundAtClient(
            aSoundResourceLocation.toString(),
            aTimeUntilNextSound,
            aSoundStrength,
            aEntity.posX,
            aEntity.posY,
            aEntity.posZ);
    }

    public static boolean doSoundAtClient(ResourceLocation aSoundResourceLocation, int aTimeUntilNextSound,
        float aSoundStrength, double aX, double aY, double aZ) {
        return doSoundAtClient(aSoundResourceLocation, aTimeUntilNextSound, aSoundStrength, 1.01818028F, aX, aY, aZ);
    }

    /**
     * @inheritDoc
     * @deprecated Use {@link #doSoundAtClient(ResourceLocation, int, float, double, double, double)}
     */
    @Deprecated
    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength, double aX,
        double aY, double aZ) {
        if (aSoundName == null) return false;
        return doSoundAtClient(
            new ResourceLocation(aSoundName),
            aTimeUntilNextSound,
            aSoundStrength,
            1.01818028F,
            aX,
            aY,
            aZ);
    }

    public static boolean doSoundAtClient(SoundResource aSound, int aTimeUntilNextSound, float aSoundStrength,
        double aX, double aY, double aZ) {
        return doSoundAtClient(aSound.resourceLocation, aTimeUntilNextSound, aSoundStrength, aX, aY, aZ);
    }

    public static boolean doSoundAtClient(SoundResource aSound, int aTimeUntilNextSound, float aSoundStrength,
        float aSoundModulation, double aX, double aY, double aZ) {
        return doSoundAtClient(
            aSound.resourceLocation,
            aTimeUntilNextSound,
            aSoundStrength,
            aSoundModulation,
            aX,
            aY,
            aZ);
    }

    public static boolean doSoundAtClient(ResourceLocation aSoundResourceLocation, int aTimeUntilNextSound,
        float aSoundStrength, float aSoundModulation, double aX, double aY, double aZ) {
        if (!FMLCommonHandler.instance()
            .getEffectiveSide()
            .isClient() || GTMod.GT.getThePlayer() == null || !GTMod.GT.getThePlayer().worldObj.isRemote) return false;
        if (GregTechAPI.sMultiThreadedSounds) new Thread(
            new RunnableSound(
                GTMod.GT.getThePlayer().worldObj,
                aX,
                aY,
                aZ,
                aTimeUntilNextSound,
                aSoundResourceLocation,
                aSoundStrength,
                aSoundModulation),
            "Sound Effect").start();
        else new RunnableSound(
            GTMod.GT.getThePlayer().worldObj,
            aX,
            aY,
            aZ,
            aTimeUntilNextSound,
            aSoundResourceLocation,
            aSoundStrength,
            aSoundModulation).run();
        return true;
    }

    public static boolean sendSoundToPlayers(World aWorld, String aSoundName, float aSoundStrength,
        float aSoundModulation, double aX, double aY, double aZ) {
        if (isStringInvalid(aSoundName) || aWorld == null || aWorld.isRemote) return false;
        NW.sendPacketToAllPlayersInRange(
            aWorld,
            new GTPacketSound(aSoundName, aSoundStrength, aSoundModulation, aX, aY, aZ),
            MathHelper.floor_double(aX),
            MathHelper.floor_double(aZ));
        return true;
    }

    public static boolean sendSoundToPlayers(World aWorld, SoundResource sound, float aSoundStrength,
        float aSoundModulation, double aX, double aY, double aZ) {
        if (aWorld == null || aWorld.isRemote) return false;
        NW.sendPacketToAllPlayersInRange(
            aWorld,
            new GTPacketSound(sound.resourceLocation.toString(), aSoundStrength, aSoundModulation, aX, aY, aZ),
            MathHelper.floor_double(aX),
            MathHelper.floor_double(aZ));
        return true;
    }

    public static int stackToInt(ItemStack aStack) {
        if (isStackInvalid(aStack)) return 0;
        return itemToInt(aStack.getItem(), Items.feather.getDamage(aStack));
    }

    public static int itemToInt(Item aItem, int aMeta) {
        return Item.getIdFromItem(aItem) | (aMeta << 16);
    }

    public static ItemStack intToStack(int aStack) {
        int tID = aStack & (~0 >>> 16), tMeta = aStack >>> 16;
        Item tItem = Item.getItemById(tID);
        if (tItem != null) return new ItemStack(tItem, 1, tMeta);
        return null;
    }

    public static boolean arrayContains(Object aObject, Object... aObjects) {
        return listContains(aObject, Arrays.asList(aObjects));
    }

    public static boolean listContains(Object aObject, Collection<?> aObjects) {
        if (aObjects == null) return false;
        return aObjects.contains(aObject);
    }

    @SafeVarargs
    public static <T> boolean arrayContainsNonNull(T... aArray) {
        if (aArray != null) for (Object tObject : aArray) if (tObject != null) return true;
        return false;
    }

    public static <A, B> int indexOf(A[] array, B value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }

        return -1;
    }

    public static <A, B> boolean contains(A[] array, B value) {
        return indexOf(array, value) != -1;
    }

    public static <T> T getIndexSafe(T[] array, int index) {
        return index < 0 || index >= array.length ? null : array[index];
    }

    public static <T> T getIndexSafe(List<T> list, int index) {
        return index < 0 || index >= list.size() ? null : list.get(index);
    }

    public static Block getBlockFromStack(ItemStack itemStack) {
        if (isStackInvalid(itemStack)) return Blocks.air;
        return getBlockFromItem(itemStack.getItem());
    }

    public static Block getBlockFromItem(Item item) {
        return Block.getBlockFromItem(item);
    }

    public static boolean isStringValid(Object aString) {
        return aString != null && !aString.toString()
            .isEmpty();
    }

    public static boolean isStringValid(String aString) {
        return aString != null && !aString.isEmpty();
    }

    public static boolean isStringInvalid(Object aString) {
        return aString == null || aString.toString()
            .isEmpty();
    }

    public static boolean isStringInvalid(String aString) {
        return aString == null || aString.isEmpty();
    }

    @Deprecated
    public static boolean isStackValid(Object aStack) {
        return (aStack instanceof ItemStack stack) && isStackValid(stack);
    }

    public static boolean isStackValid(ItemStack aStack) {
        return (aStack != null) && aStack.getItem() != null && aStack.stackSize >= 0;
    }

    public static boolean isStackValid(FluidStack aStack) {
        return (aStack != null) && aStack.getFluid() != null && aStack.amount >= 0;
    }

    @Deprecated
    public static boolean isStackInvalid(Object aStack) {
        return !(aStack instanceof ItemStack stack) || isStackInvalid(stack);
    }

    public static boolean isStackInvalid(ItemStack aStack) {
        return aStack == null || aStack.getItem() == null || aStack.stackSize < 0;
    }

    public static boolean isDebugItem(ItemStack aStack) {
        return /* ItemList.Armor_Cheat.isStackEqual(aStack, T, T) || */ areStacksEqual(
            GTModHandler.getIC2Item("debug", 1),
            aStack,
            true);
    }

    public static ItemStack updateItemStack(ItemStack aStack) {
        if (isStackValid(aStack) && aStack.getItem() instanceof GTGenericItem)
            ((GTGenericItem) aStack.getItem()).isItemStackUsable(aStack);
        return aStack;
    }

    public static boolean isOpaqueBlock(World aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlock(aX, aY, aZ)
            .isOpaqueCube();
    }

    public static boolean isBlockAir(World aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlock(aX, aY, aZ)
            .isAir(aWorld, aX, aY, aZ);
    }

    public static boolean hasBlockHitBox(World aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlock(aX, aY, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ) != null;
    }

    public static void setCoordsOnFire(World aWorld, int aX, int aY, int aZ, boolean aReplaceCenter) {
        if (aReplaceCenter) if (aWorld.getBlock(aX, aY, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ) == null) aWorld.setBlock(aX, aY, aZ, Blocks.fire);
        if (aWorld.getBlock(aX + 1, aY, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX + 1, aY, aZ) == null)
            aWorld.setBlock(aX + 1, aY, aZ, Blocks.fire);
        if (aWorld.getBlock(aX - 1, aY, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX - 1, aY, aZ) == null)
            aWorld.setBlock(aX - 1, aY, aZ, Blocks.fire);
        if (aWorld.getBlock(aX, aY + 1, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY + 1, aZ) == null)
            aWorld.setBlock(aX, aY + 1, aZ, Blocks.fire);
        if (aWorld.getBlock(aX, aY - 1, aZ)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY - 1, aZ) == null)
            aWorld.setBlock(aX, aY - 1, aZ, Blocks.fire);
        if (aWorld.getBlock(aX, aY, aZ + 1)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ + 1) == null)
            aWorld.setBlock(aX, aY, aZ + 1, Blocks.fire);
        if (aWorld.getBlock(aX, aY, aZ - 1)
            .getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ - 1) == null)
            aWorld.setBlock(aX, aY, aZ - 1, Blocks.fire);
    }

    public static ItemStack getProjectile(SubTag aProjectileType, IInventory aInventory) {
        if (aInventory != null) for (int i = 0, j = aInventory.getSizeInventory(); i < j; i++) {
            ItemStack rStack = aInventory.getStackInSlot(i);
            if (isStackValid(rStack) && rStack.getItem() instanceof IProjectileItem
                && ((IProjectileItem) rStack.getItem()).hasProjectile(aProjectileType, rStack))
                return updateItemStack(rStack);
        }
        return null;
    }

    public static void removeNullStacksFromInventory(IInventory aInventory) {
        if (aInventory != null) for (int i = 0, j = aInventory.getSizeInventory(); i < j; i++) {
            ItemStack tStack = aInventory.getStackInSlot(i);
            if (tStack != null && (tStack.stackSize == 0 || tStack.getItem() == null))
                aInventory.setInventorySlotContents(i, null);
        }
    }

    /**
     * Initializes new empty texture page for casings page 0 is old CASING_BLOCKS
     * <p>
     * Then casings should be registered like this: for (byte i = MIN_USED_META; i < MAX_USED_META; i = (byte) (i + 1))
     * { Textures.BlockIcons.casingTexturePages[PAGE][i+START_INDEX] = new GT_CopiedBlockTexture(this, 6, i); }
     *
     * @param page 0 to 127
     * @return true if it made empty page, false if one already existed...
     */
    public static boolean addTexturePage(byte page) {
        if (Textures.BlockIcons.casingTexturePages[page] == null) {
            Textures.BlockIcons.casingTexturePages[page] = new ITexture[128];
            return true;
        }
        return false;
    }

    public static NBTTagCompound getNBTContainingShort(NBTTagCompound aNBT, Object aTag, short aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setShort(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingString(NBTTagCompound aNBT, Object aTag, Object aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        if (aValue == null) return aNBT;
        aNBT.setString(aTag.toString(), aValue.toString());
        return aNBT;
    }

    public static float getHeatDamageFromItem(ItemStack aStack) {
        ItemData tData = GTOreDictUnificator.getItemData(aStack);
        return tData == null ? 0
            : (tData.mPrefix == null ? 0 : tData.mPrefix.mHeatDamage)
                + (tData.hasValidMaterialData() ? tData.mMaterial.mMaterial.mHeatDamage : 0);
    }

    public static int getRadioactivityLevel(ItemStack aStack) {
        ItemData tData = GTOreDictUnificator.getItemData(aStack);
        if (tData != null && tData.hasValidMaterialData()) {
            if (tData.mMaterial.mMaterial.mArmorEnchantment instanceof EnchantmentRadioactivity)
                return tData.mMaterial.mMaterial.mArmorEnchantmentLevel;
            if (tData.mMaterial.mMaterial.mToolEnchantment instanceof EnchantmentRadioactivity)
                return tData.mMaterial.mMaterial.mToolEnchantmentLevel;
        }
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRadioactivity.INSTANCE.effectId, aStack);
    }

    public static boolean applyHeatDamage(EntityLivingBase entity, float damage) {
        return applyHeatDamage(entity, damage, GTDamageSources.getHeatDamage());
    }

    public static boolean applyHeatDamageFromItem(EntityLivingBase entity, float damage, ItemStack item) {
        return applyHeatDamage(entity, damage, new DamageSourceHotItem(item));
    }

    private static boolean applyHeatDamage(EntityLivingBase aEntity, float aDamage, DamageSource source) {
        if (aDamage > 0 && aEntity != null
            && !aEntity.isImmuneToFire()
            && !HazardProtection.isWearingFullHeatHazmat(aEntity)) {
            try {
                return aEntity.attackEntityFrom(source, aDamage);
            } catch (Exception t) {
                GTLoggers.GT_FML_LOGGER.error("Error damaging entity", t);
            }
        }
        return false;
    }

    public static boolean applyFrostDamage(EntityLivingBase aEntity, float aDamage) {
        if (aDamage > 0 && aEntity != null && !HazardProtection.isWearingFullFrostHazmat(aEntity)) {
            return aEntity.attackEntityFrom(GTDamageSources.getFrostDamage(), aDamage);
        }
        return false;
    }

    public static boolean applyElectricityDamage(EntityLivingBase aEntity, long aVoltage, long aAmperage) {
        long aDamage = getTier(aVoltage) * aAmperage * 4;
        if (aDamage > 0 && aEntity != null && !HazardProtection.isWearingFullElectroHazmat(aEntity)) {
            return aEntity.attackEntityFrom(GTDamageSources.getElectricDamage(), aDamage);
        }
        return false;
    }

    public static boolean applyRadioactivity(EntityLivingBase aEntity, int aLevel, int aAmountOfItems) {
        if (aLevel > 0 && aEntity != null
            && aEntity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD
            && aEntity.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD
            && !HazardProtection.isWearingFullRadioHazmat(aEntity)) {
            PotionEffect tEffect;
            aEntity.addPotionEffect(
                new PotionEffect(
                    Potion.moveSlowdown.id,
                    aLevel * 140 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(Potion.moveSlowdown)) == null ? 0
                            : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            aEntity.addPotionEffect(
                new PotionEffect(
                    Potion.digSlowdown.id,
                    aLevel * 150 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(Potion.digSlowdown)) == null ? 0
                            : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            aEntity.addPotionEffect(
                new PotionEffect(
                    Potion.confusion.id,
                    aLevel * 130 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(Potion.confusion)) == null ? 0
                            : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            aEntity.addPotionEffect(
                new PotionEffect(
                    Potion.weakness.id,
                    aLevel * 150 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(Potion.weakness)) == null ? 0
                            : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            aEntity.addPotionEffect(
                new PotionEffect(
                    Potion.hunger.id,
                    aLevel * 130 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(Potion.hunger)) == null ? 0 : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            aEntity.addPotionEffect(
                new PotionEffect(
                    IC2Potion.radiation.id,
                    aLevel * 180 * aAmountOfItems + Math.max(
                        0,
                        ((tEffect = aEntity.getActivePotionEffect(IC2Potion.radiation)) == null ? 0
                            : tEffect.getDuration())),
                    Math.max(0, (5 * aLevel) / 7)));
            return true;
        }
        return false;
    }

    public static ItemStack setStack(ItemStack aSetStack, ItemStack aToStack) {
        if (isStackInvalid(aSetStack) || isStackInvalid(aToStack)) return null;
        aSetStack.func_150996_a(aToStack.getItem());
        aSetStack.stackSize = aToStack.stackSize;
        Items.feather.setDamage(aSetStack, Items.feather.getDamage(aToStack));
        aSetStack.setTagCompound(aToStack.getTagCompound());
        return aSetStack;
    }

    /**
     * @deprecated use {@link #copy(ItemStack)} instead
     */
    @Deprecated
    public static ItemStack copy(Object... aStacks) {
        for (Object tStack : aStacks) if (isStackValid(tStack)) return ((ItemStack) tStack).copy();
        return null;
    }

    public static ItemStack copy(ItemStack aStack) {
        if (isStackValid(aStack)) return aStack.copy();
        return null;
    }

    @Deprecated
    private static ItemStack firstStackOrNull(Object... aStacks) {
        for (Object tStack : aStacks) if (tStack instanceof ItemStack stack) return stack;
        return null;
    }

    @Contract("null -> null")
    public static ItemStack copyOrNull(ItemStack stack) {
        if (isStackValid(stack)) return stack.copy();
        return null;
    }

    @Contract("null -> null")
    public static FluidStack copyOrNull(FluidStack stack) {
        if (isStackValid(stack)) return stack.copy();
        return null;
    }

    public static FluidStack copyAmount(int aAmount, FluidStack aStack) {
        if (aStack == null) return null;
        FluidStack rStack = aStack.copy();
        rStack.amount = aAmount;
        return rStack;
    }

    /**
     * @deprecated use {@link #copyAmount(int, ItemStack)} instead
     */
    @Deprecated
    public static ItemStack copyAmount(long aAmount, Object... aStacks) {
        return copyAmount(aAmount, firstStackOrNull(aStacks));
    }

    /**
     * @deprecated use {@link #copyAmount(int, ItemStack)} instead
     */
    @Deprecated
    public static ItemStack copyAmount(long aAmount, ItemStack aStack) {
        return copyAmount((int) aAmount, aStack);
    }

    @Contract("_, null -> null")
    public static ItemStack copyAmount(int aAmount, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (isStackInvalid(rStack)) return null;
        if (aAmount > 64) aAmount = 64;
        else if (aAmount == -1) aAmount = 111;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = (byte) aAmount;
        return rStack;
    }

    @Contract("_, null -> null")
    public static ItemStack multiplyStack(int aMultiplier, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (isStackInvalid(rStack)) return null;
        int tAmount = rStack.stackSize * aMultiplier;
        if (tAmount > 64) tAmount = 64;
        else if (tAmount == -1) tAmount = 111;
        else if (tAmount < 0) tAmount = 0;
        rStack.stackSize = (byte) tAmount;
        return rStack;
    }

    /**
     * Unlike {@link #copyAmount(int, ItemStack)}, this method does not restrict stack size by 64.
     */
    public static ItemStack copyAmountUnsafe(int aAmount, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (isStackInvalid(rStack)) return null;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = aAmount;
        return rStack;
    }

    public static ItemStack copyMetaData(int aMetaData, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (isStackInvalid(rStack)) return null;
        Items.feather.setDamage(rStack, aMetaData);
        return rStack;
    }

    /**
     * @deprecated use {@link #copyAmountAndMetaData(int, int, ItemStack)} instead
     */
    @Deprecated
    public static ItemStack copyAmountAndMetaData(long aAmount, long aMetaData, ItemStack aStack) {
        return copyAmountAndMetaData((int) aAmount, (int) aMetaData, aStack);
    }

    public static ItemStack copyAmountAndMetaData(int aAmount, int aMetaData, ItemStack aStack) {
        ItemStack rStack = copyAmount(aAmount, aStack);
        if (isStackInvalid(rStack)) return null;
        Items.feather.setDamage(rStack, aMetaData);
        return rStack;
    }

    /**
     * returns a copy of an ItemStack with its Stacksize being multiplied by aMultiplier
     */
    public static ItemStack mul(int aMultiplier, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (rStack == null) return null;
        rStack.stackSize *= aMultiplier;
        return rStack;
    }

    /**
     * Loads an ItemStack properly.
     */
    public static ItemStack loadItem(NBTTagCompound aNBT, String aTagName) {
        return loadItem(aNBT.getCompoundTag(aTagName));
    }

    public static FluidStack loadFluid(NBTTagCompound aNBT, String aTagName) {
        return loadFluid(aNBT.getCompoundTag(aTagName));
    }

    /**
     * Loads an ItemStack properly.
     */
    public static ItemStack loadItem(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack tRawStack = ItemStack.loadItemStackFromNBT(aNBT);
        int tRealStackSize = 0;
        if (tRawStack != null && aNBT.hasKey("Count", NBT.TAG_INT)) {
            tRealStackSize = aNBT.getInteger("Count");
            tRawStack.stackSize = tRealStackSize;
        } else if (tRawStack != null) {
            tRealStackSize = tRawStack.stackSize;
        }
        ItemStack tRet = GTOreDictUnificator.get(true, tRawStack);
        if (tRet != null) tRet.stackSize = tRealStackSize;
        return tRet;
    }

    public static void saveItem(NBTTagCompound aParentTag, String aTagName, ItemStack aStack) {
        if (aStack != null) aParentTag.setTag(aTagName, saveItem(aStack));
    }

    public static NBTTagCompound saveItem(ItemStack aStack) {
        if (aStack == null) return new NBTTagCompound();
        NBTTagCompound t = new NBTTagCompound();
        aStack.writeToNBT(t);
        if (aStack.stackSize > Byte.MAX_VALUE) t.setInteger("Count", aStack.stackSize);
        return t;
    }

    /**
     * Loads an FluidStack properly.
     */
    public static FluidStack loadFluid(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        return FluidStack.loadFluidStackFromNBT(aNBT);
    }

    public static <E> E selectItemInList(int aIndex, E aReplacement, List<E> aList) {
        if (aList == null || aList.isEmpty()) return aReplacement;
        if (aList.size() <= aIndex) return aList.get(aList.size() - 1);
        if (aIndex < 0) return aList.get(0);
        return aList.get(aIndex);
    }

    public static boolean isStackInStackSet(ItemStack aStack, Set<ItemStack> aSet) {
        if (aStack == null) return false;
        if (aSet.contains(aStack)) return true;

        return aSet.contains(GTItemStack.internalCopyStack(aStack, true));
    }

    public static boolean isStackInList(ItemStack aStack, Collection<GTItemStack> aList) {
        if (aStack == null) {
            return false;
        }
        if (aStack.getItem() instanceof ItemGTToolbox) {
            return ToolboxUtil.getSelectedTool(aStack)
                .map(selected -> isStackInList(new GTItemStack(selected), aList))
                .orElse(false);
        } else {
            return isStackInList(new GTItemStack(aStack), aList);
        }
    }

    public static boolean isStackInList(@Nonnull GTItemStack aStack, @Nonnull Collection<GTItemStack> aList) {
        return aList.contains(aStack) || aList.contains(new GTItemStack(aStack.mItem, aStack.mStackSize, WILDCARD));
    }

    public static <X, Y extends Comparable<Y>> LinkedHashMap<X, Y> sortMapByValuesAcending(Map<X, Y> map) {
        return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(CollectorUtils.entriesToMap(LinkedHashMap::new));
    }

    /**
     * Translates a Material Amount into an Amount of Fluid. Second Parameter for things like Bucket Amounts (1000) and
     * similar
     */
    public static long translateMaterialToAmount(long aMaterialAmount, long aAmountPerUnit, boolean aRoundUp) {
        return Math.max(
            0,
            ((aMaterialAmount * aAmountPerUnit) / M)
                + (aRoundUp && (aMaterialAmount * aAmountPerUnit) % M > 0 ? 1 : 0));
    }

    /**
     * This checks if the Dimension is really a Dimension and not another Planet or something. Used for my Teleporter.
     */
    public static boolean isRealDimension(int aDimensionID) {
        if (aDimensionID <= 1 && aDimensionID >= -1 && !GregTechAPI.sDimensionalList.contains(aDimensionID))
            return true;
        return !GregTechAPI.sDimensionalList.contains(aDimensionID)
            && DimensionManager.isDimensionRegistered(aDimensionID);
    }

    private static final Int2ObjectOpenHashMap<String> DIMENSION_NAMES = new Int2ObjectOpenHashMap<>();

    public static String getDimensionName(int dimId) {
        if (!DIMENSION_NAMES.containsKey(dimId)) {
            WorldProvider p = DimensionManager.createProviderFor(dimId);

            if (p != null) {
                DIMENSION_NAMES.put(dimId, p.getDimensionName());
            } else {
                DIMENSION_NAMES.put(dimId, "[unknown dimension]");
            }
        }

        String name = DIMENSION_NAMES.get(dimId);

        String key = "gtnop.world." + name;

        if (StatCollector.canTranslate(key)) {
            return StatCollector.translateToLocal(key);
        } else {
            return name;
        }
    }

    public static boolean moveEntityToDimensionAtCoords(Entity entity, int aDimension, double aX, double aY,
        double aZ) {
        // Credit goes to BrandonCore Author :!:

        if (entity == null || entity.worldObj.isRemote) return false;
        if (entity.ridingEntity != null) entity.mountEntity(null);
        if (entity.riddenByEntity != null) entity.riddenByEntity.mountEntity(null);

        World startWorld = entity.worldObj;
        WorldServer destinationWorld = FMLCommonHandler.instance()
            .getMinecraftServerInstance()
            .worldServerForDimension(aDimension);

        if (destinationWorld == null) {
            return false;
        }

        boolean interDimensional = startWorld.provider.dimensionId != destinationWorld.provider.dimensionId;
        if (!interDimensional) return false;
        startWorld.updateEntityWithOptionalForce(entity, false); // added

        if (entity instanceof EntityPlayerMP player) {
            player.closeScreen(); // added
            player.dimension = aDimension;
            player.playerNetServerHandler.sendPacket(
                new S07PacketRespawn(
                    player.dimension,
                    player.worldObj.difficultySetting,
                    destinationWorld.getWorldInfo()
                        .getTerrainType(),
                    player.theItemInWorldManager.getGameType()));
            ((WorldServer) startWorld).getPlayerManager()
                .removePlayer(player);

            startWorld.playerEntities.remove(player);
            startWorld.updateAllPlayersSleepingFlag();
            int i = entity.chunkCoordX;
            int j = entity.chunkCoordZ;
            if ((entity.addedToChunk) && (startWorld.getChunkProvider()
                .chunkExists(i, j))) {
                startWorld.getChunkFromChunkCoords(i, j)
                    .removeEntity(entity);
                startWorld.getChunkFromChunkCoords(i, j).isModified = true;
            }
            startWorld.loadedEntityList.remove(entity);
            startWorld.onEntityRemoved(entity);
        }

        entity.setLocationAndAngles(aX, aY, aZ, entity.rotationYaw, entity.rotationPitch);

        destinationWorld.theChunkProviderServer.loadChunk((int) aX >> 4, (int) aZ >> 4);

        destinationWorld.theProfiler.startSection("placing");
        if (!(entity instanceof EntityPlayer)) {
            NBTTagCompound entityNBT = new NBTTagCompound();
            entity.isDead = false;
            entityNBT.setString("id", EntityList.getEntityString(entity));
            entity.writeToNBT(entityNBT);
            entity.isDead = true;
            entity = EntityList.createEntityFromNBT(entityNBT, destinationWorld);
            if (entity == null) {
                return false;
            }
            entity.dimension = destinationWorld.provider.dimensionId;
        }
        destinationWorld.spawnEntityInWorld(entity);
        entity.setWorld(destinationWorld);
        entity.setLocationAndAngles(aX, aY, aZ, entity.rotationYaw, entity.rotationPitch);

        destinationWorld.updateEntityWithOptionalForce(entity, false);
        entity.setLocationAndAngles(aX, aY, aZ, entity.rotationYaw, entity.rotationPitch);

        if ((entity instanceof EntityPlayerMP player)) {
            player.mcServer.getConfigurationManager()
                .func_72375_a(player, destinationWorld);
            player.playerNetServerHandler.setPlayerLocation(aX, aY, aZ, player.rotationYaw, player.rotationPitch);
        }

        destinationWorld.updateEntityWithOptionalForce(entity, false);

        if (entity instanceof EntityPlayerMP player) {
            player.theItemInWorldManager.setWorld(destinationWorld);
            player.mcServer.getConfigurationManager()
                .updateTimeAndWeatherForPlayer(player, destinationWorld);
            player.mcServer.getConfigurationManager()
                .syncPlayerInventory(player);

            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), potionEffect));
            }

            player.playerNetServerHandler.sendPacket(
                new S1FPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
            FMLCommonHandler.instance()
                .firePlayerChangedDimensionEvent(
                    player,
                    startWorld.provider.dimensionId,
                    destinationWorld.provider.dimensionId);
        }
        entity.setLocationAndAngles(aX, aY, aZ, entity.rotationYaw, entity.rotationPitch);

        destinationWorld.theProfiler.endSection();
        entity.fallDistance = 0;
        return true;
    }

    /**
     * @deprecated Use {@link StatCollector}
     */
    @Deprecated
    public static String trans(String aKey, String aEnglish) {
        return StatCollector.translateToLocal("Interaction_DESCRIPTION_Index_" + aKey);
    }

    /**
     * @return an Array containing the X and the Y Coordinate of the clicked Point, with the top left Corner as Origin,
     *         like on the Texture Sheet. return values should always be between [0.0F and 0.99F].
     */
    public static float[] getClickedFacingCoords(ForgeDirection side, float aX, float aY, float aZ) {
        return switch (side) {
            case DOWN -> new float[] { clampClickedFacingCoord(1 - aX), clampClickedFacingCoord(aZ) };
            case UP -> new float[] { clampClickedFacingCoord(aX), clampClickedFacingCoord(aZ) };
            case NORTH -> new float[] { clampClickedFacingCoord(1 - aX), clampClickedFacingCoord(1 - aY) };
            case SOUTH -> new float[] { clampClickedFacingCoord(aX), clampClickedFacingCoord(1 - aY) };
            case WEST -> new float[] { clampClickedFacingCoord(aZ), clampClickedFacingCoord(1 - aY) };
            case EAST -> new float[] { clampClickedFacingCoord(1 - aZ), clampClickedFacingCoord(1 - aY) };
            default -> new float[] { 0.5F, 0.5F };
        };
    }

    private static float clampClickedFacingCoord(float coord) {
        return MathHelper.clamp_float(coord, 0.0F, 0.99F);
    }

    /**
     * This Function determines the direction a Block gets when being Wrenched. returns -1 if invalid. Even though that
     * could never happen. Normalizes values into the range [0.0f, 1.0f].
     */
    public static ForgeDirection determineWrenchingSide(ForgeDirection side, float aX, float aY, float aZ) {
        float modX = (aX % 1.0f + 1.0f) % 1.0f;
        float modY = (aY % 1.0f + 1.0f) % 1.0f;
        float modZ = (aZ % 1.0f + 1.0f) % 1.0f;
        ForgeDirection tBack = side.getOpposite();
        // The = here is necessary; Since the hitVec only has a precision of 1/16th on MP and gets rounded down,
        // a value of 0.8 would be 0.75 on MP, which is not > 0.75, returning false.
        switch (side) {
            case DOWN, UP -> {
                if (modX < 0.25) {
                    if (modZ < 0.25) return tBack;
                    if (modZ >= 0.75) return tBack;
                    return WEST;
                }
                if (modX >= 0.75) {
                    if (modZ < 0.25) return tBack;
                    if (modZ >= 0.75) return tBack;
                    return EAST;
                }
                if (modZ < 0.25) return NORTH;
                if (modZ >= 0.75) return SOUTH;
                return side;
            }
            case NORTH, SOUTH -> {
                if (modX < 0.25) {
                    if (modY < 0.25) return tBack;
                    if (modY >= 0.75) return tBack;
                    return WEST;
                }
                if (modX >= 0.75) {
                    if (modY < 0.25) return tBack;
                    if (modY >= 0.75) return tBack;
                    return EAST;
                }
                if (modY < 0.25) return DOWN;
                if (modY >= 0.75) return UP;
                return side;
            }
            case WEST, EAST -> {
                if (modZ < 0.25) {
                    if (modY < 0.25) return tBack;
                    if (modY >= 0.75) return tBack;
                    return NORTH;
                }
                if (modZ >= 0.75) {
                    if (modY < 0.25) return tBack;
                    if (modY >= 0.75) return tBack;
                    return SOUTH;
                }
                if (modY < 0.25) return DOWN;
                if (modY >= 0.75) return UP;
                return side;
            }
        }
        return UNKNOWN;
    }

    /**
     * {@link String#format} without throwing exception. Falls back to {@code format} without {@code args}. Since it
     * suppresses errors, it should be used only when inputs are unreliable, e.g. processing text input by player, or
     * processing placeholders in localization entries.
     */
    @Nonnull
    public static String formatStringSafe(@Nonnull String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (IllegalFormatException ignored) {
            return format;
        }
    }

    public static ItemStack getIntegratedCircuit(int config) {
        return ItemList.Circuit_Integrated.getWithDamage(0, config);
    }

    /**
     * @return A list of every integrated circuit, excluding zero. Do not modify the ItemStacks!
     */
    public static List<ItemStack> getAllIntegratedCircuits() {
        return ItemIntegratedCircuit.NON_ZERO_VARIANTS;
    }

    public static FakePlayer getFakePlayer(IGregTechTileEntity aBaseMetaTileEntity) {
        if (aBaseMetaTileEntity.getWorld() instanceof WorldServer) {
            return FakePlayerFactory.get(
                (WorldServer) aBaseMetaTileEntity.getWorld(),
                new GameProfile(aBaseMetaTileEntity.getOwnerUuid(), aBaseMetaTileEntity.getOwnerName()));
        }
        return null;
    }

    public static int hashCode(int... values) {
        return Arrays.hashCode(values);
    }

    public static long hashCode(long... values) {
        return Arrays.hashCode(values);
    }

    public static int findMatchingStackInList(List<ItemStack> aStacks, ItemStack aStack) {
        if (isStackInvalid(aStack)) return -1;
        for (int i = 0, aStacksSize = aStacks.size(); i < aStacksSize; i++) {
            ItemStack tStack = aStacks.get(i);
            if (areStacksEqual(aStack, tStack)) return i;
        }
        return -1;
    }

    public static Map<GTUtility.ItemId, Long> convertItemListToMap(Collection<ItemStack> itemStacks) {
        Map<GTUtility.ItemId, Long> result = new Object2LongOpenHashMap<>();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.stackSize > 0) {
                GTUtility.ItemId itemId = GTUtility.ItemId.createNoCopy(itemStack);
                result.merge(itemId, (long) itemStack.stackSize, Long::sum);
            }
        }
        return result;
    }

    public static Map<Fluid, Long> convertFluidListToMap(Collection<FluidStack> fluidStacks) {
        Map<Fluid, Long> result = new Reference2LongOpenHashMap<>();
        for (FluidStack fluidStack : fluidStacks) {
            if (fluidStack != null && fluidStack.amount > 0) {
                result.merge(fluidStack.getFluid(), (long) fluidStack.amount, Long::sum);
            }
        }
        return result;
    }

    /**
     * @return Supplied collection that doesn't contain invalid MetaTileEntities.
     */
    public static <T extends Collection<E>, E extends MetaTileEntity> T filterValidMTEs(T metaTileEntities) {
        metaTileEntities.removeIf(mte -> mte == null || !mte.isValid());
        return metaTileEntities;
    }

    /**
     * @return Supplied collection that removes invalid MTEs from the collection while it is being iterated
     */
    public static <T extends Collection<E>, E extends MetaTileEntity> ValidMTEList<T, E> validMTEList(
        T metaTileEntities) {
        return new ValidMTEList<>(metaTileEntities);
    }

    /**
     * Filters a list of MTEs into a list of a subclass
     *
     * @param mtes     The original list of MTEs
     * @param mteClass The MTE subclass to filter
     * @return The filtered list of valid MTEs
     * @param <MTESuper> The MTE superclass
     * @param <MTEImpl>  The MTE implementation class/subclass
     */
    public static <MTESuper extends MetaTileEntity, MTEImpl extends MTESuper> List<MTEImpl> getMTEsOfType(
        List<MTESuper> mtes, Class<MTEImpl> mteClass) {
        List<MTEImpl> out = new ArrayList<>(mtes.size());

        for (int i = 0, mtesSize = mtes.size(); i < mtesSize; i++) {
            MTESuper mte = mtes.get(i);

            if (mte != null && mte.isValid()) {
                if (mteClass.isInstance(mte)) {
                    out.add(mteClass.cast(mte));
                }
            }
        }

        return out;
    }

    @Nullable
    public static IMetaTileEntity getMetaTileEntity(TileEntity tileEntity) {
        if (tileEntity instanceof IGregTechTileEntity gtTE && gtTE.canAccessData()) {
            return gtTE.getMetaTileEntity();
        }
        return null;
    }

    public static ForgeDirection getSideFromPlayerFacing(Entity player) {
        if (player == null) return ForgeDirection.UNKNOWN;
        if (player.rotationPitch >= 65) return ForgeDirection.UP;
        if (player.rotationPitch <= -65) return ForgeDirection.DOWN;
        final byte facing = COMPASS_DIRECTIONS[MathHelper.floor_double(0.5D + 4.0F * player.rotationYaw / 360.0F)
            & 0x3];
        return ForgeDirection.getOrientation(facing);
    }

    public static void destroyCurrentItem(final EntityPlayer player) {
        final ItemStack currentItem = player.getCurrentEquippedItem();
        if (currentItem == null) {
            return;
        }
        if (!(currentItem.getItem() instanceof ItemGTToolbox)) {
            player.destroyCurrentEquippedItem();
        }
    }

    public static class ItemNBT {

        /**
         * Deprecated, use methods from {@link ItemStackNBT}
         */
        @Deprecated
        public static NBTTagCompound getNBT(ItemStack aStack) {
            NBTTagCompound rNBT = aStack.getTagCompound();
            return rNBT == null ? new NBTTagCompound() : rNBT;
        }

        public static String getPunchCardData(ItemStack aStack) {
            return ItemStackNBT.getString(aStack, "GT.PunchCardData");
        }

        public static void setLighterFuel(ItemStack aStack, long aFuel) {
            ItemStackNBT.setLong(aStack, "GT.LighterFuel", aFuel);
        }

        public static long getLighterFuel(ItemStack aStack) {
            return ItemStackNBT.getLong(aStack, "GT.LighterFuel");
        }

        public static short getMapID(ItemStack aStack) {
            if (ItemStackNBT.hasKey(aStack, "map_id")) {
                return ItemStackNBT.getShort(aStack, "map_id");
            }
            return -1;
        }

        public static void setBookTitle(ItemStack aStack, String aTitle) {
            ItemStackNBT.setString(aStack, "title", aTitle);
        }

        public static String getBookTitle(ItemStack aStack) {
            return ItemStackNBT.getString(aStack, "title");
        }

        public static String getBookAuthor(ItemStack aStack) {
            return ItemStackNBT.getString(aStack, "author");
        }

        @Deprecated
        public static void setProspectionData(ItemStack aStack, int aX, int aY, int aZ, int aDim, FluidStack aFluid,
            String... aOres) {
            NBTTagCompound tNBT = ItemStackNBT.get(aStack);
            StringBuilder tData = new StringBuilder(aX + "," + aY + "," + aZ + "," + aDim + ",");
            if (aFluid != null) tData.append(aFluid.amount)
                .append(",")
                .append(aFluid.getLocalizedName())
                .append(",");
            for (String tString : aOres) {
                tData.append(tString)
                    .append(",");
            }
            tNBT.setString("prospection", tData.toString());
        }

        public static void convertProspectionData(ItemStack aStack) {
            NBTTagCompound tNBT = ItemStackNBT.get(aStack);
            byte tTier = tNBT.getByte("prospection_tier");

            if (tTier == 0) { // basic prospection data
                String tData = tNBT.getString("prospection");
                String[] tDataArray = tData.split(",");
                if (tDataArray.length > 6) {
                    tNBT.setString(
                        "author",
                        getProspectionBookTitle(tDataArray[3], tDataArray[0], tDataArray[1], tDataArray[2]));
                    NBTTagList tNBTList = new NBTTagList();
                    StringBuilder tOres = new StringBuilder(" Prospected Ores: ");
                    for (int i = 6; tDataArray.length > i; i++) {
                        tOres.append(tDataArray[i])
                            .append(" ");
                    }
                    tNBTList.appendTag(
                        new NBTTagString(
                            "Tier " + tTier
                                + " Prospecting Data From: X"
                                + tDataArray[0]
                                + " Z:"
                                + tDataArray[2]
                                + " Dim:"
                                + tDataArray[3]
                                + " Produces "
                                + tDataArray[4]
                                + "L "
                                + tDataArray[5]
                                + " "
                                + tOres));
                    tNBT.setTag("pages", tNBTList);
                }
            } else { // advanced prospection data
                String tPos = tNBT.getString("prospection_pos");
                String tRadius = tNBT.getString("prospection_radius");

                String tOresStr = tNBT.getString("prospection_ores");
                String tOilsStr = tNBT.getString("prospection_oils");
                String tOilsPosStr = tNBT.getString("prospection_oils_pos");

                String[] tOres = tOresStr.isEmpty() ? null : tOresStr.split("\\|");
                String[] tOils = tOilsStr.isEmpty() ? null : tOilsStr.split("\\|");

                NBTTagList tNBTList = new NBTTagList();

                String tPageText = getProspectionFrontPage(
                    tPos,
                    tOils == null ? "0" : formatNumber(tOils.length),
                    tRadius);
                tNBTList.appendTag(new NBTTagString(tPageText));

                if (tOres != null) fillBookWithList(tNBTList, "Ores Found %s\n\n", "\n", 7, tOres);

                if (tOils != null) fillBookWithList(tNBTList, "Oils%s\n\n", "\n", 9, tOils);

                tNBTList.appendTag(new NBTTagString(getProspectionGridPage()));
                tNBTList.appendTag(new NBTTagString(getProspectionOilLocationPage(tOilsPosStr)));

                tNBT.setString("author", tPos);
                tNBT.setTag("pages", tNBTList);
            }
        }

        public static String getProspectionFrontPage(String aPos, String aFieldCount, String aRadius) {
            return String.format("""
                Prospector report
                %s

                Oils: %s

                Ores within %s blocks

                Location is center of orevein
                Check NEI to confirm orevein type""", aPos, aFieldCount, aRadius);
        }

        public static String getProspectionOilLocationPage(String aOilsPos) {
            return "Corners of [5] are \n" + aOilsPos + "\n" + "P - Prospector in 8x8 field";
        }

        public static String getProspectionGridPage() {
            return """
                Oil notes

                Prospects from NW to SE 576 chunks(9 8x8 oilfields)
                 around and gives min-max amount

                [1][2][3]
                [4][5][6]
                [7][8][9]

                [5] - Prospector in this 8x8 area""";
        }

        public static String getRawProspectionDataName() {
            // should probably stat collector this, but stuff already relies on this being unlocalized sooo
            // don't break what ain't broke
            return "Raw Prospection Data";
        }

        public static String getProspectionBookTitle(int aDim, int aX, int aY, int aZ) {
            return getProspectionBookTitle(formatNumber(aDim), formatNumber(aX), formatNumber(aY), formatNumber(aZ));
        }

        public static String getProspectionBookTitle(String aDim, String aX, String aY, String aZ) {
            return "Dim: " + aDim + "\nX: " + aX + " Y: " + aY + " Z: " + aZ;
        }

        public static void fillBookWithList(NBTTagList aBook, String aPageHeader, String aListDelimiter,
            int aItemsPerPage, String[] list) {
            String aPageFormatter = " %d/%d";
            int tTotalPages = list.length / aItemsPerPage + (list.length % aItemsPerPage > 0 ? 1 : 0);
            int tPage = 0;
            StringBuilder tPageText;
            do {
                tPageText = new StringBuilder();
                for (int i = tPage * aItemsPerPage; i < (tPage + 1) * aItemsPerPage && i < list.length; i += 1)
                    tPageText.append((tPageText.isEmpty()) ? "" : aListDelimiter)
                        .append(list[i]);

                if (!tPageText.isEmpty()) {
                    String tPageCounter = tTotalPages > 1 ? String.format(aPageFormatter, tPage + 1, tTotalPages) : "";
                    NBTTagString tPageTag = new NBTTagString(String.format(aPageHeader, tPageCounter) + tPageText);
                    aBook.appendTag(tPageTag);
                }

                ++tPage;
            } while (!tPageText.isEmpty());
        }

        /**
         * Adds an enchantment to the item stack or replaces the level of an existing enchantment
         */
        public static void addEnchantment(ItemStack aStack, Enchantment aEnchantment, int aLevel) {
            if (!aStack.hasTagCompound()) {
                aStack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound tNBT = aStack.getTagCompound();
            NBTTagCompound tEnchantmentTag;
            if (!tNBT.hasKey("ench", NBT.TAG_LIST)) {
                tNBT.setTag("ench", new NBTTagList());
            }
            NBTTagList nbttaglist = tNBT.getTagList("ench", NBT.TAG_COMPOUND);

            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                tEnchantmentTag = nbttaglist.getCompoundTagAt(i);
                if (tEnchantmentTag.getShort("id") == aEnchantment.effectId) {
                    tEnchantmentTag.setShort("lvl", (byte) aLevel);
                    return;
                }
            }

            tEnchantmentTag = new NBTTagCompound();
            tEnchantmentTag.setShort("id", (short) aEnchantment.effectId);
            tEnchantmentTag.setShort("lvl", (byte) aLevel);
            nbttaglist.appendTag(tEnchantmentTag);
        }
    }

    public static String toSubscript(long no) {
        char[] chars = Long.toString(no)
            .toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = switch (chars[i]) {
                case '0' -> CustomGlyphs.SUBSCRIPT0.charAt(0);
                case '1' -> '\u2081';
                case '2' -> '\u2082';
                case '3' -> '\u2083';
                case '4' -> '\u2084';
                case '5' -> '\u2085';
                case '6' -> '\u2086';
                case '7' -> '\u2087';
                case '8' -> '\u2088';
                case '9' -> '\u2089';
                case '?' -> CustomGlyphs.SUBSCRIPT_QUESTION_MARK.charAt(0);
                default -> chars[i];
            };
        }
        return new String(chars);
    }

    public static boolean isPartOfMaterials(ItemStack aStack, Materials aMaterials) {
        return GTOreDictUnificator.getAssociation(aStack) != null
            && GTOreDictUnificator.getAssociation(aStack).mMaterial.mMaterial.equals(aMaterials);
    }

    /**
     * Add an itemstack to player inventory, or drop on ground if full. Can be called on client but it probably won't
     * work very well.
     */
    public static void addItemToPlayerInventory(EntityPlayer aPlayer, ItemStack aStack) {
        if (isStackInvalid(aStack)) return;
        if (!aPlayer.inventory.addItemStackToInventory(aStack) && !aPlayer.worldObj.isRemote) {
            EntityItem dropItem = aPlayer.entityDropItem(aStack, 0);
            dropItem.delayBeforeCanPickup = 0;
        }
    }

    public static long getNonnullElementCount(Object[] tArray) {
        return Arrays.stream(tArray)
            .filter(Objects::nonNull)
            .count();
    }

    public static long clamp(long val, long lo, long hi) {
        return Math.min(hi, Math.max(val, lo));
    }

    public static int clamp(int val, int lo, int hi) {
        return Math.min(hi, Math.max(val, lo));
    }

    public static float clamp(float val, float lo, float hi) {
        return Math.min(hi, Math.max(val, lo));
    }

    public static double clamp(double val, double lo, double hi) {
        return Math.min(hi, Math.max(val, lo));
    }

    public static int map(int x, int in_min, int in_max, int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double linearCurve(double x, double x1, double y1, double x2, double y2) {
        x = GTUtility.clamp(x, Math.min(x1, x2), Math.max(x1, x2));

        return map(x, x1, x2, y1, y2);
    }

    public static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static int min(int a, int b, int c, int d) {
        return Math.min(a, Math.min(b, Math.min(c, b)));
    }

    public static int min(int first, int... rest) {
        for (int i = 0; i < rest.length; i++) {
            int l = rest[i];
            if (l < first) first = l;
        }
        return first;
    }

    public static long min(long first, long... rest) {
        for (int i = 0; i < rest.length; i++) {
            long l = rest[i];
            if (l < first) first = l;
        }
        return first;
    }

    public static int max(int first, int... rest) {
        for (int i = 0; i < rest.length; i++) {
            int l = rest[i];
            if (l > first) first = l;
        }
        return first;
    }

    public static long max(long first, long... rest) {
        for (int i = 0; i < rest.length; i++) {
            long l = rest[i];
            if (l > first) first = l;
        }
        return first;
    }

    public static long ceil(double k) {
        long l = (long) k;
        return k > (double) l ? l + 1 : l;
    }

    /// Ceiling division.
    /// Rounds to positive infinity for positive signs, and rounds to zero for negative signs.
    /// Sign = signum(lhs) * signum(rhs), so 7 / -2 = -3.5 = -3
    public static long ceilDiv(long lhs, long rhs) {
        if (rhs == 0) throw new ArithmeticException("/ by zero");

        return lhs / rhs + ((lhs ^ rhs) > 0 && lhs % rhs != 0 ? 1 : 0);
    }

    /** @deprecated Use {@link Integer#signum(int)} instead.} */
    @Deprecated
    public static int signum(int x) {
        return Integer.signum(x);
    }

    /** @deprecated Use {@link Long#signum(long)} instead.} */
    @Deprecated
    public static long signum(long x) {
        return Long.signum(x);
    }

    public static int mod(int value, int divisor) {
        return ((value % divisor) + divisor) % divisor;
    }

    public static NBTTagCompound getOrCreateNbtCompound(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }
        return compound;
    }

    /**
     * Computes base raised to the power of an integer exponent. Typically faster than
     * {@link java.lang.Math#pow(double, double)} when {@code exp} is an integer.
     */
    public static double powInt(double base, int exp) {
        if (exp > 0) return powBySquaring(base, exp);
        if (exp < 0) return 1.0 / powBySquaring(base, -exp);
        return 1.0;
    }

    /**
     * Computes base raised to non-negative integer exponent.
     */
    private static double powBySquaring(double base, int exp) {
        // IEEE 754 double: 1 sign bit, 11 exponent bits, 52 mantissa bits. Exponent is stored with offset 1023.
        // The result is directly constructed for bases 2 and 4 from the exponent bits.
        if (base == 2) return exp > 1023 ? Double.POSITIVE_INFINITY : Double.longBitsToDouble(exp + 1023L << 52);
        if (base == 4) return exp > 511 ? Double.POSITIVE_INFINITY : Double.longBitsToDouble(exp * 2L + 1023L << 52);
        double result = 1.0;
        while (exp > 0) {
            if ((exp & 1) == 1) result *= base;
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    /**
     * Computes base raised to non-negative long exponent.
     */
    private static double powBySquaring(double base, long exp) {
        // IEEE 754 double: 1 sign bit, 11 exponent bits, 52 mantissa bits. Exponent is stored with offset 1023.
        // The result is directly constructed for bases 2 and 4 from the exponent bits.
        if (base == 2) return exp > 1023 ? Double.POSITIVE_INFINITY : Double.longBitsToDouble(exp + 1023L << 52);
        if (base == 4) return exp > 511 ? Double.POSITIVE_INFINITY : Double.longBitsToDouble(exp * 2L + 1023L << 52);
        double result = 1.0;
        while (exp > 0) {
            if ((exp & 1) == 1) result *= base;
            base *= base;
            exp >>= 1;
        }
        return result;
    }

    /**
     * Computes the floor of log base 4 for a positive integer. Uses bitwise operations for fast calculation.
     */
    public static int log4(int a) {
        if (a <= 1) return 0;
        return 31 - Integer.numberOfLeadingZeros(a) >> 1;
    }

    /**
     * Computes the floor of log base 4 for a positive long. Uses bitwise operations for fast calculation.
     */
    public static long log4(long a) {
        if (a <= 1) return 0;
        return 63 - Long.numberOfLeadingZeros(a) >> 1;
    }

    /**
     * Computes the ceil of log base 4 for a positive long. Uses bitwise operations for fast calculation.
     */
    public static long log4ceil(long a) {
        if (a <= 1) return 0;
        return 65 - Long.numberOfLeadingZeros(a - 1) >> 1;
    }

    public static long addSafe(long a, long b) {
        long result = a + b;

        if (a > 0 && b > 0 && result <= 0) {
            return Long.MAX_VALUE;
        }

        if (a < 0 && b < 0 && result >= 0) {
            return Long.MIN_VALUE;
        }

        return result;
    }

    /// Multiplies two longs, clamping to the min/max for a long if the result overflows.
    public static long mulSafe(long a, long b) {
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException ignored) {
            if (a > 0 == b > 0) {
                return Long.MAX_VALUE;
            } else {
                return Long.MIN_VALUE;
            }
        }
    }

    /**
     * Hash an item stack for the purpose of storing hash across launches
     */
    public static int persistentHash(ItemStack aStack, boolean aUseStackSize, boolean aUseNBT) {
        if (aStack == null) return 0;
        int result = Objects.hashCode(GameRegistry.findUniqueIdentifierFor(aStack.getItem()));
        result = result * 31 + Items.feather.getDamage(aStack);

        if (aUseStackSize) result = result * 31 + aStack.stackSize;
        if (aUseNBT) result = result * 31 + Objects.hashCode(aStack.stackTagCompound);
        return result;
    }

    /**
     * Hash an item stack for the purpose of storing hash across launches
     */
    public static int persistentHash(FluidStack aStack, boolean aUseStackSize, boolean aUseNBT) {
        if (aStack == null) return 0;
        int base = Objects.hashCode(
            aStack.getFluid()
                .getName());

        if (aUseStackSize) base = base * 31 + aStack.amount;
        if (aUseNBT) base = base * 31 + Objects.hashCode(aStack.tag);
        return base;
    }

    public static boolean isCellEmpty(ItemStack itemStack) {
        if (itemStack == null) return false;
        ItemStack tStack = ItemList.Cell_Empty.get(1);
        tStack.stackSize = itemStack.stackSize;
        return GTUtility.areStacksEqual(itemStack, tStack);
    }

    /**
     * Convert a cell to fluid. If given itemstack does not contain any fluid, return null. Will correctly multiple
     * output fluid amount if input stack size is greater than 1.
     */
    public static FluidStack convertCellToFluid(ItemStack itemStack) {
        if (itemStack == null) return null;

        FluidStack fluidStack = getFluidForFilledItem(itemStack, true);
        if (fluidStack != null) {
            fluidStack.amount = fluidStack.amount * itemStack.stackSize;
            return fluidStack;
        }
        return null;
    }

    public static boolean isAnyIntegratedCircuit(ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItem() == ItemList.Circuit_Integrated.getItem() && 0 <= itemStack.getItemDamage()
            && itemStack.getItemDamage() < 25;
    }

    public static byte convertRatioToRedstone(long used, long max, int threshold, boolean inverted) {
        byte signal;
        if (used <= 0) { // Empty
            signal = 0;
        } else if (used >= max) { // Full
            signal = 15;
        } else { // Range 1-14
            signal = (byte) (1 + (14 * used) / max);
        }

        if (inverted) {
            signal = (byte) (15 - signal);
        }

        if (threshold > 0) {
            if (inverted && used >= threshold) {
                return 0;
            } else if (!inverted && used < threshold) {
                return 0;
            }
        }

        return signal;
    }

    public static Stream<NBTTagCompound> streamCompounds(NBTTagList list) {
        if (list == null) return Stream.empty();
        return IntStream.range(0, list.tagCount())
            .mapToObj(list::getCompoundTagAt);
    }

    public static boolean equals(ItemStack[] a, ItemStack[] b) {
        // because stupid mojang didn't override equals for us
        if (a == null && b == null) return true;
        if ((a == null) != (b == null)) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!areStacksEqual(a[i], b[i], false)) return false;
        }
        return true;
    }

    /**
     * Guava ImmutableMap variant of Collectors.toMap. Optimized for serial streams.
     */
    public static <T, K, U> Collector<T, ?, ImmutableMap<K, U>> toImmutableMapSerial(
        Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        // petty type inference cannot work out the correct type parameter
        return Collector.<T, ImmutableMap.Builder<K, U>, ImmutableMap<K, U>>of(
            ImmutableMap::builder,
            (b, t) -> b.put(keyMapper.apply(t), valueMapper.apply(t)),
            (b1, b2) -> b1.putAll(b2.build()),
            ImmutableMap.Builder::build);
    }

    public static <NBT extends NBTBase> Collector<NBT, ?, NBTTagList> toNBTTagList() {
        return new Collector<NBT, NBTTagList, NBTTagList>() {

            @Override
            public Supplier<NBTTagList> supplier() {
                return NBTTagList::new;
            }

            @Override
            public BiConsumer<NBTTagList, NBT> accumulator() {
                return NBTTagList::appendTag;
            }

            @Override
            public BinaryOperator<NBTTagList> combiner() {
                return (from, to) -> {
                    to.tagList.addAll(from.tagList);

                    return to;
                };
            }

            @Override
            public Function<NBTTagList, NBTTagList> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return new HashSet<>(Arrays.asList(Characteristics.IDENTITY_FINISH));
            }
        };
    }

    public static boolean isArrayEmptyOrNull(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isArrayOfLength(Object[] arr, int expectedLength) {
        return arr != null && arr.length == expectedLength;
    }

    @SafeVarargs
    public static <E> Collection<E> concat(Collection<E>... colls) {
        return concat(Arrays.asList(colls));
    }

    public static <E> Collection<E> concat(Collection<Collection<E>> colls) {
        return new ConcatCollection<>(colls);
    }

    private static class ConcatCollection<E> extends AbstractCollection<E> {

        private final Collection<Collection<E>> colls;
        private final int size;

        public ConcatCollection(Collection<Collection<E>> lists) {
            Collection<Collection<E>> colls1 = null;
            for (Collection<E> list : lists) {
                if (list == null || list.isEmpty()) {
                    colls1 = lists.stream()
                        .filter(c -> c != null && !c.isEmpty())
                        .collect(Collectors.toList());
                    break;
                }
            }
            if (colls1 == null) colls1 = lists;
            colls = colls1;
            int sum = 0;
            for (Collection<E> list : colls) {
                sum += list.size();
            }
            size = sum;
        }

        @Nonnull
        @Override
        public Iterator<E> iterator() {
            return colls.stream()
                .flatMap(Collection::stream)
                .iterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    public static String[] breakLines(String... lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            for (String part : NEWLINE_SPLITTER.split(line)) {
                result.add(part);
            }
        }
        return result.toArray(new String[0]);
    }

    @AutoValue
    public abstract static class ItemId {

        public static ItemId create(NBTTagCompound tag) {
            return new AutoValue_GTUtility_ItemId(
                Item.getItemById(tag.getShort("item")),
                tag.getShort("meta"),
                tag.hasKey("tag", NBT.TAG_COMPOUND) ? tag.getCompoundTag("tag") : null,
                tag.hasKey("stackSize", NBT.TAG_INT) ? tag.getInteger("stackSize") : null);
        }

        /**
         * This method copies NBT, as it is mutable.
         */
        public static ItemId create(ItemStack itemStack) {
            NBTTagCompound nbt = itemStack.getTagCompound();
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }

            return new AutoValue_GTUtility_ItemId(itemStack.getItem(), Items.feather.getDamage(itemStack), nbt, null);
        }

        /**
         * This method copies NBT, as it is mutable.
         */
        public static ItemId createWithStackSize(ItemStack itemStack) {
            NBTTagCompound nbt = itemStack.getTagCompound();
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }

            return new AutoValue_GTUtility_ItemId(
                itemStack.getItem(),
                Items.feather.getDamage(itemStack),
                nbt,
                itemStack.stackSize);
        }

        /**
         * This method copies NBT, as it is mutable.
         */
        public static ItemId create(Item item, int metaData, @Nullable NBTTagCompound nbt) {
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }
            return new AutoValue_GTUtility_ItemId(item, metaData, nbt, null);
        }

        /**
         * This method copies NBT, as it is mutable.
         */
        public static ItemId create(Item item, int metaData, @Nullable NBTTagCompound nbt,
            @Nullable Integer stackSize) {
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }
            return new AutoValue_GTUtility_ItemId(item, metaData, nbt, stackSize);
        }

        /**
         * This method stores metadata as wildcard and NBT as null.
         */
        public static ItemId createAsWildcard(ItemStack itemStack) {
            return new AutoValue_GTUtility_ItemId(itemStack.getItem(), WILDCARD, null, null);
        }

        /**
         * This method stores NBT as null.
         */
        public static ItemId createWithoutNBT(ItemStack itemStack) {
            return new AutoValue_GTUtility_ItemId(itemStack.getItem(), Items.feather.getDamage(itemStack), null, null);
        }

        /**
         * This method does not copy NBT in order to save time. Make sure not to mutate it!
         */
        public static ItemId createNoCopy(ItemStack itemStack) {
            return new AutoValue_GTUtility_ItemId(
                itemStack.getItem(),
                Items.feather.getDamage(itemStack),
                itemStack.getTagCompound(),
                null);
        }

        public abstract Item item();

        public abstract int metaData();

        @Nullable
        public abstract NBTTagCompound nbt();

        @Nullable
        public abstract Integer stackSize();

        public NBTTagCompound writeToNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setShort("item", (short) Item.getIdFromItem(item()));
            tag.setShort("meta", (short) metaData());
            if (nbt() != null) tag.setTag("tag", nbt());
            if (stackSize() != null) tag.setInteger("stackSize", stackSize());
            return tag;
        }

        @Nonnull
        public ItemStack getItemStack() {
            ItemStack itemStack = new ItemStack(item(), 1, metaData());
            NBTTagCompound nbt = nbt();
            itemStack.setTagCompound(nbt == null ? null : (NBTTagCompound) nbt.copy());
            return itemStack;
        }

        @Nonnull
        public ItemStack getItemStack(int stackSize) {
            ItemStack itemStack = new ItemStack(item(), stackSize, metaData());
            NBTTagCompound nbt = nbt();
            itemStack.setTagCompound(nbt == null ? null : (NBTTagCompound) nbt.copy());
            return itemStack;
        }

        public boolean matches(ItemStack stack) {
            if (item() != stack.getItem()) return false;
            if (metaData() != Items.feather.getDamage(stack)) return false;

            return Objects.equals(nbt(), stack.getTagCompound());
        }
    }

    @AutoValue
    public abstract static class FluidId {

        public static FluidId create(NBTTagCompound tag) {
            return new AutoValue_GTUtility_FluidId(
                FluidRegistry.getFluid(tag.getString("FluidName")),
                tag.hasKey("Tag", NBT.TAG_COMPOUND) ? tag.getCompoundTag("Tag") : null,
                tag.hasKey("Amount", NBT.TAG_INT) ? tag.getInteger("Amount") : null);
        }

        public NBTTagCompound writeToNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("FluidName", fluid().getName());
            if (nbt() != null) tag.setTag("Tag", nbt());
            Integer amount = amount();
            if (amount != null) tag.setInteger("Amount", amount);
            return tag;
        }

        public static FluidId create(FluidStack fluidStack) {
            return createWithCopy(fluidStack.getFluid(), null, fluidStack.tag);
        }

        public static FluidId create(Fluid fluid) {
            return createNoCopy(fluid, null, null);
        }

        public static FluidId createWithCopy(Fluid fluid, Integer amount, @Nullable NBTTagCompound nbt) {
            if (nbt != null) {
                nbt = (NBTTagCompound) nbt.copy();
            }
            return new AutoValue_GTUtility_FluidId(fluid, nbt, amount);
        }

        /**
         * This method does not copy the NBT tag.
         */
        public static FluidId createNoCopy(Fluid fluid, Integer amount, @Nullable NBTTagCompound nbt) {
            return new AutoValue_GTUtility_FluidId(fluid, nbt, amount);
        }

        protected abstract Fluid fluid();

        @Nullable
        protected abstract NBTTagCompound nbt();

        @Nullable
        protected abstract Integer amount();

        @Nonnull
        public FluidStack getFluidStack() {
            NBTTagCompound nbt = nbt();
            return new FluidStack(fluid(), 1, nbt != null ? (NBTTagCompound) nbt.copy() : null);
        }

        @Nonnull
        public FluidStack getFluidStack(int amount) {
            NBTTagCompound nbt = nbt();
            return new FluidStack(fluid(), amount, nbt != null ? (NBTTagCompound) nbt.copy() : null);
        }

        public boolean matches(FluidStack stack) {
            if (fluid() != stack.getFluid()) return false;

            return Objects.equals(nbt(), stack.tag);
        }

        public boolean matches(Fluid fluid) {
            return fluid() == fluid;
        }
    }

    public static int getPlasmaFuelValueInEUPerLiterFromFluid(FluidStack aLiquid) {
        if (aLiquid == null) return 0;
        GTRecipe tFuel = RecipeMaps.plasmaFuels.getBackend()
            .findFuel(aLiquid);
        if (tFuel != null) return tFuel.mSpecialValue;
        return 0;
    }

    public static MovingObjectPosition getPlayerLookingTarget(EntityPlayer viewpoint) {
        double reachDistance = viewpoint instanceof EntityPlayerMP mp ? mp.theItemInWorldManager.getBlockReachDistance()
            : getClientReachDistance();
        Vec3 posVec = Vec3.createVectorHelper(
            viewpoint.posX,
            viewpoint.posY + (viewpoint.getEyeHeight() - viewpoint.getDefaultEyeHeight()),
            viewpoint.posZ);
        Vec3 lookVec = viewpoint.getLook(0);
        Vec3 modifiedPosVec = posVec
            .addVector(lookVec.xCoord * reachDistance, lookVec.yCoord * reachDistance, lookVec.zCoord * reachDistance);

        return viewpoint.worldObj.rayTraceBlocks(posVec, modifiedPosVec);
    }

    public static float getClientReachDistance() {
        return Minecraft.getMinecraft().playerController.getBlockReachDistance();
    }

    public static String formatShortenedLong(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }

        int exp = (int) (Math.log(number) / Math.log(1000));
        char suffix = "kMGTPE".charAt(exp - 1);
        double shortened = number / GTUtility.powInt(1000, exp);

        if (shortened == (long) shortened) {
            return String.format("%d%c", (long) shortened, suffix);
        } else {
            return String.format("%.1f%c", shortened, suffix);
        }
    }

    public static String truncateText(String text, int limit) {
        if (limit < 0) limit = 1;
        if (text == null) {
            return null;
        }
        if (text.length() <= limit) {
            return text;
        } else {
            return text.substring(0, limit) + "...";
        }
    }

    // helper function (from MultiblockBase that creates a string of timed dates
    public static String appendRate(boolean isLiquid, Long amount, boolean isFormatShortened, int maxProgressTicks) {
        final StringBuffer ret = new StringBuffer();
        final DecimalFormat df = new DecimalFormat("0.00");
        final double progressTime = (double) maxProgressTicks / 20;
        double perTick = amount / (double) maxProgressTicks;
        double perSecond = amount / progressTime;
        double perMinute = perSecond * 60;
        double perHour = perSecond * 3_600;
        double perDay = perSecond * 86_400;

        final String amountText = translateToLocal("GT5U.gui.text.amount") + " ";
        final String perTickText = translateToLocal("GT5U.gui.text.per_tick") + " ";
        final String perSecondText = translateToLocal("GT5U.gui.text.per_second") + " ";
        final String perMinuteText = translateToLocal("GT5U.gui.text.per_minute") + " ";
        final String perHourText = translateToLocal("GT5U.gui.text.per_hour") + " ";
        final String perDayText = translateToLocal("GT5U.gui.text.per_day") + " ";

        final Function<Double, Double> roundNumber = (number) -> {
            if (Math.abs(number) < 10) {
                return Math.round(number * 100) / 100.0;
            } else {
                return Math.floor(number);
            }
        };

        if (isFormatShortened) {
            ret.append(" ");
            if (perSecond <= 1) {
                ret.append(
                    translateToLocalFormatted("GT5U.gui.text.rate_short_slow", df.format(progressTime / amount)));
            } else {
                ret.append(
                    translateToLocalFormatted("GT5U.gui.text.rate_short", formatShortenedLong((long) perSecond)));
            }
        } else {
            ret.append(EnumChatFormatting.RESET);
            ret.append(
                amountText + EnumChatFormatting.GOLD
                    + formatNumber(amount)
                    + (isLiquid ? "L" : "")
                    + EnumChatFormatting.RESET);
            ret.append("\n");
            ret.append(
                perTickText + EnumChatFormatting.GOLD
                    + formatNumber(roundNumber.apply(perTick))
                    + (isLiquid ? "L" : "")
                    + (perSecond > 1_000_000 ? " " + translateToLocalFormatted(
                        "GT5U.gui.text.rate_large_suffix",
                        formatShortenedLong((long) perTick)) : "")
                    + EnumChatFormatting.RESET);
            ret.append("\n");
            ret.append(
                perSecondText + EnumChatFormatting.GOLD
                    + formatNumber(roundNumber.apply(perSecond))
                    + (isLiquid ? "L" : "")
                    + (perSecond > 1_000_000 ? " " + translateToLocalFormatted(
                        "GT5U.gui.text.rate_large_suffix",
                        formatShortenedLong((long) perSecond)) : "")
                    + EnumChatFormatting.RESET);
            ret.append("\n");
            ret.append(
                perMinuteText + EnumChatFormatting.GOLD
                    + formatNumber(roundNumber.apply(perMinute))
                    + (isLiquid ? "L" : "")
                    + (perMinute > 1_000_000 ? " " + translateToLocalFormatted(
                        "GT5U.gui.text.rate_large_suffix",
                        formatShortenedLong((long) perMinute)) : "")
                    + EnumChatFormatting.RESET);
            ret.append("\n");
            ret.append(
                perHourText + EnumChatFormatting.GOLD
                    + formatNumber(roundNumber.apply(perHour))
                    + (isLiquid ? "L" : "")
                    + (perHour > 1_000_000 ? " " + translateToLocalFormatted(
                        "GT5U.gui.text.rate_large_suffix",
                        formatShortenedLong((long) perHour)) : "")
                    + EnumChatFormatting.RESET);
            ret.append("\n");
            ret.append(
                perDayText + EnumChatFormatting.GOLD
                    + formatNumber(roundNumber.apply(perDay))
                    + (isLiquid ? "L" : "")
                    + (perDay > 1_000_000 ? " " + translateToLocalFormatted(
                        "GT5U.gui.text.rate_large_suffix",
                        formatShortenedLong((long) perDay)) : "")
                    + EnumChatFormatting.RESET);
        }
        return ret.toString();
    }

    /**
     * A helper method that does rotation calculations for rendering.
     *
     * @param extendedFacing - extendedFacing value of the MTE
     * @return AxisAngle4f that can be used directly with glRotate calls
     */
    public static AxisAngle4f getRotationAxisAngle4f(ExtendedFacing extendedFacing) {
        ForgeDirection direction = extendedFacing.getDirection();
        Rotation rotation = extendedFacing.getRotation();
        Flip flip = extendedFacing.getFlip();

        float faceAngleDeg = switch (direction) {
            case DOWN, WEST -> 90f;
            case UP, EAST -> -90f;
            case SOUTH -> 180f;
            default -> 0f; // NORTH
        };

        Vector3f faceAxis = switch (direction) {
            case UP, DOWN -> new Vector3f(1, 0, 0);
            default -> new Vector3f(0, 1, 0);
        };

        Quaternionf finalQuat = new Quaternionf().fromAxisAngleRad(faceAxis, (float) Math.toRadians(faceAngleDeg));

        // Local rotation
        float localAngleDeg = switch (rotation) {
            case CLOCKWISE -> -90f;
            case COUNTER_CLOCKWISE -> 90f;
            case UPSIDE_DOWN -> 180f;
            default -> 0f; // NORMAL
        };

        float angleSign = switch (direction) {
            case WEST, EAST, NORTH -> -1.0f;
            default -> 1.0f; // UP, DOWN, SOUTH
        };

        if (flip == Flip.HORIZONTAL || flip == Flip.VERTICAL) {
            angleSign *= -1.0f;
        }

        float angleOffset = (direction == ForgeDirection.DOWN) ? 180.0f : 0.0f;
        localAngleDeg = (localAngleDeg * angleSign) + angleOffset;

        // Apply the local rotation
        Quaternionf localQuat = new Quaternionf()
            .fromAxisAngleRad(new Vector3f(0, 0, 1), (float) Math.toRadians(localAngleDeg));
        finalQuat.mul(localQuat);

        // Extract axis-angle form
        AxisAngle4f axisAngle = new AxisAngle4f();
        finalQuat.get(axisAngle);

        // Convert to degrees for consistency
        axisAngle.angle = (float) Math.toDegrees(axisAngle.angle);
        return axisAngle;
    }

    public static boolean canReplaceBlockWithWater(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        boolean isFlowing = isFlowingWater(block, world, x, y, z);
        boolean isAir = block == Blocks.air;
        return (isFlowing || isAir);
    }

    public static boolean isCOFHWater(Block block) {
        return Mods.COFHCore.isModLoaded() && (block instanceof BlockWater || block instanceof BlockTickingWater);
    }

    private static boolean isDistilledWater(Block block) {
        if (DISTILLED_WATER_BLOCK == null) {
            Block b = BlocksItems.getFluidBlock(InternalName.fluidDistilledWater);
            if (b != null) {
                DISTILLED_WATER_BLOCK = b;
            }
        }
        return DISTILLED_WATER_BLOCK != null
            && Block.getIdFromBlock(block) == Block.getIdFromBlock(DISTILLED_WATER_BLOCK);
    }

    public static boolean isWater(Block block) {
        return block == Blocks.flowing_water || block == Blocks.water || isDistilledWater(block) || isCOFHWater(block);
    }

    public static boolean isFlowingWater(Block block, World world, int x, int y, int z) {
        if (isCOFHWater(block)) {
            return world.getBlockMetadata(x, y, z) > 0;
        }
        return block == Blocks.flowing_water || (isWater(block) && world.getBlockMetadata(x, y, z) > 0);
    }

    public static boolean isSourceWater(Block block, World world, int x, int y, int z) {
        return isWater(block) && !isFlowingWater(block, world, x, y, z);
    }
}
