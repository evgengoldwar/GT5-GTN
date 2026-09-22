package gregtech.api.util;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.COMPASS_DIRECTIONS;
import static gregtech.api.enums.GTValues.E;
import static gregtech.api.enums.GTValues.NW;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UNKNOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
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

import org.jetbrains.annotations.Contract;

import com.google.auto.value.AutoValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;

import cofh.asmhooks.block.BlockTickingWater;
import cofh.asmhooks.block.BlockWater;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.GTMod;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Mods;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.items.GTGenericItem;
import gregtech.api.net.GTPacketSound;
import gregtech.api.objects.CollectorUtils;
import gregtech.api.objects.GTItemStack;
import gregtech.api.threads.RunnableSound;
import gregtech.common.items.ItemIntegratedCircuit;
import gregtech.nei.FluidDisplayStackMode;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

public class GTUtility {

    private static final Map<Fluid, List<ItemStack>> fluidToContainersMap = new IdentityHashMap<>(1024);
    public static Map<GTPlayedSound, Integer> sPlayedSoundMap = new HashMap<>();
    public static UUID defaultUuid = null;
    private static final Splitter NEWLINE_SPLITTER = Splitter.on("\\n")
        .omitEmptyStrings();

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

    public static String getClassName(Object aObject) {
        if (aObject == null) return "null";
        return aObject.getClass()
            .getName()
            .substring(
                aObject.getClass()
                    .getName()
                    .lastIndexOf(".") + 1);
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

    public static String getColoredTierNameFromVoltage(long voltage) {
        return getColoredTierNameFromTier(getTier(voltage));
    }

    public static String getColoredTierNameFromTier(byte tier) {
        return GTValues.TIER_COLORS[tier] + GTValues.VN[tier] + EnumChatFormatting.RESET;
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

    public static void sendChatTrans(EntityPlayer player, @Nonnull String messageKey, Object... args) {
        player.addChatComponentMessage(new ChatComponentTranslation(messageKey, args));
    }

    public static void sendChatComp(EntityPlayer player, @Nonnull IChatComponent component) {
        player.addChatComponentMessage(component);
    }

    public static boolean isServer() {
        return FMLCommonHandler.instance()
            .getEffectiveSide()
            .isServer();
    }

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

    public static final String[] COVER_DIRECTION_NAMES = new String[] { "GT5U.interface.coverTabs.down",
        "GT5U.interface.coverTabs.up", "GT5U.interface.coverTabs.north", "GT5U.interface.coverTabs.south",
        "GT5U.interface.coverTabs.west", "GT5U.interface.coverTabs.east" };

    public static String getUnlocalizedSideName(ForgeDirection side) {
        return GTUtility.COVER_DIRECTION_NAMES[side.ordinal()];
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

    public static boolean stackEqual(ItemStack left, ItemStack right, boolean ignoreDamage, boolean ignoreNBT) {
        if (left == null || right == null) {
            return false;
        }

        if (left.getItem() != right.getItem()) {
            return false;
        }

        if (!ignoreDamage) {
            int leftDamage = Items.feather.getDamage(left);
            int rightDamage = Items.feather.getDamage(right);

            if (leftDamage != rightDamage && leftDamage != WILDCARD && rightDamage != WILDCARD) {
                return false;
            }
        }

        if (!ignoreNBT) {
            NBTTagCompound leftTag = left.getTagCompound();
            NBTTagCompound rightTag = right.getTagCompound();

            return Objects.equals(leftTag, rightTag);
        }

        return true;
    }

    public static boolean stackEqual(ItemStack left, ItemStack right) {
        return stackEqual(left, right, false, false);
    }

    public static boolean areStacksEqualOrNull(ItemStack stack1, ItemStack stack2) {
        return (stack1 == null && stack2 == null) || GTUtility.areStacksEqual(stack1, stack2);
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
            // tNBT.setString("mFluidMaterialName", FLUID_MAP.get(aFluid.getFluid()).mName);
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
        if (aStack.getItem() == Items.potionitem || aStack.getItem() == Items.experience_bottle)
            return new ItemStack(Items.glass_bottle, 1);

        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem
            && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            ItemStack tStack = copyAmount(1, aStack);
            ((IFluidContainerItem) aStack.getItem()).drain(tStack, Integer.MAX_VALUE, true);
            if (!areStacksEqual(aStack, tStack)) return tStack;
            return null;
        }

        int tCapsuleCount = GTModHandler.getCapsuleCellContainerCount(aStack);
        if (tCapsuleCount > 0) return ItemList.Cell_Empty.get(tCapsuleCount);

        return null;
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

    public static boolean listContains(Object aObject, Collection<?> aObjects) {
        if (aObjects == null) return false;
        return aObjects.contains(aObject);
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

    public static Block getBlockFromItem(Item item) {
        return Block.getBlockFromItem(item);
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

    @Deprecated
    public static boolean isStackInvalid(Object aStack) {
        return !(aStack instanceof ItemStack stack) || isStackInvalid(stack);
    }

    public static boolean isStackInvalid(ItemStack aStack) {
        return aStack == null || aStack.getItem() == null || aStack.stackSize < 0;
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

    public static ItemStack setStack(ItemStack aSetStack, ItemStack aToStack) {
        if (isStackInvalid(aSetStack) || isStackInvalid(aToStack)) return null;
        aSetStack.func_150996_a(aToStack.getItem());
        aSetStack.stackSize = aToStack.stackSize;
        Items.feather.setDamage(aSetStack, Items.feather.getDamage(aToStack));
        aSetStack.setTagCompound(aToStack.getTagCompound());
        return aSetStack;
    }

    @Deprecated
    public static ItemStack copy(Object... aStacks) {
        for (Object tStack : aStacks) if (isStackValid(tStack)) return ((ItemStack) tStack).copy();
        return null;
    }

    public static ItemStack copy(ItemStack aStack) {
        if (isStackValid(aStack)) return aStack.copy();
        return null;
    }

    @Contract("null -> null")
    public static ItemStack copyOrNull(ItemStack stack) {
        if (isStackValid(stack)) return stack.copy();
        return null;
    }

    public static FluidStack copyAmount(int aAmount, FluidStack aStack) {
        if (aStack == null) return null;
        FluidStack rStack = aStack.copy();
        rStack.amount = aAmount;
        return rStack;
    }

    @Deprecated
    private static ItemStack firstStackOrNull(Object... aStacks) {
        for (Object tStack : aStacks) if (tStack instanceof ItemStack stack) return stack;
        return null;
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

    public static ItemStack copyAmountUnsafe(int aAmount, ItemStack aStack) {
        ItemStack rStack = copy(aStack);
        if (isStackInvalid(rStack)) return null;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = aAmount;
        return rStack;
    }

    public static ItemStack copyAmountAndMetaData(int aAmount, int aMetaData, ItemStack aStack) {
        ItemStack rStack = copyAmount(aAmount, aStack);
        if (isStackInvalid(rStack)) return null;
        Items.feather.setDamage(rStack, aMetaData);
        return rStack;
    }

    @Deprecated
    public static ItemStack copyAmountAndMetaData(long aAmount, long aMetaData, ItemStack aStack) {
        return copyAmountAndMetaData((int) aAmount, (int) aMetaData, aStack);
    }

    public static ItemStack loadItem(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack tRawStack = ItemStack.loadItemStackFromNBT(aNBT);
        int tRealStackSize = 0;
        if (tRawStack != null && aNBT.hasKey("Count", NBT.TAG_INT)) {
            tRealStackSize = aNBT.getInteger("Count");
            tRawStack.stackSize = tRealStackSize;
        }
        return tRawStack;
    }

    public static NBTTagCompound saveItem(ItemStack aStack) {
        if (aStack == null) return new NBTTagCompound();
        NBTTagCompound t = new NBTTagCompound();
        aStack.writeToNBT(t);
        if (aStack.stackSize > Byte.MAX_VALUE) t.setInteger("Count", aStack.stackSize);
        return t;
    }

    public static boolean isStackInList(ItemStack aStack, Collection<GTItemStack> aList) {
        if (aStack == null) {
            return false;
        }
        return isStackInList(new GTItemStack(aStack), aList);
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

    public static ItemStack getIntegratedCircuit(int config) {
        return ItemList.Circuit_Integrated.getWithDamage(0, config);
    }

    public static List<ItemStack> getAllIntegratedCircuits() {
        return ItemIntegratedCircuit.NON_ZERO_VARIANTS;
    }

    public static FakePlayer getFakePlayer(IGTE aBaseMetaTileEntity) {
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

    @Nullable
    public static IMTE getMetaTileEntity(TileEntity tileEntity) {
        if (tileEntity instanceof IGTE gtTE && gtTE.canAccessData()) {
            return gtTE.getMTE();
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

        player.destroyCurrentEquippedItem();
    }

    public static long clamp(long val, long lo, long hi) {
        return Math.min(hi, Math.max(val, lo));
    }

    public static int clamp(int val, int lo, int hi) {
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

    public static long ceilDiv(long lhs, long rhs) {
        if (rhs == 0) throw new ArithmeticException("/ by zero");

        return lhs / rhs + ((lhs ^ rhs) > 0 && lhs % rhs != 0 ? 1 : 0);
    }

    public static int mod(int value, int divisor) {
        return ((value % divisor) + divisor) % divisor;
    }

    public static double powInt(double base, int exp) {
        if (exp > 0) return powBySquaring(base, exp);
        if (exp < 0) return 1.0 / powBySquaring(base, -exp);
        return 1.0;
    }

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

    public static long log4(long a) {
        if (a <= 1) return 0;
        return 63 - Long.numberOfLeadingZeros(a) >> 1;
    }

    public static long log4ceil(long a) {
        if (a <= 1) return 0;
        return 65 - Long.numberOfLeadingZeros(a - 1) >> 1;
    }

    public static boolean isCellEmpty(ItemStack itemStack) {
        if (itemStack == null) return false;
        ItemStack tStack = ItemList.Cell_Empty.get(1);
        tStack.stackSize = itemStack.stackSize;
        return GTUtility.areStacksEqual(itemStack, tStack);
    }

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

    public static <T, K, U> Collector<T, ?, ImmutableMap<K, U>> toImmutableMapSerial(
        Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        // petty type inference cannot work out the correct type parameter
        return Collector.<T, ImmutableMap.Builder<K, U>, ImmutableMap<K, U>>of(
            ImmutableMap::builder,
            (b, t) -> b.put(keyMapper.apply(t), valueMapper.apply(t)),
            (b1, b2) -> b1.putAll(b2.build()),
            ImmutableMap.Builder::build);
    }

    public static boolean isArrayEmptyOrNull(Object[] arr) {
        return arr == null || arr.length == 0;
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

    public static boolean isCOFHWater(Block block) {
        return Mods.COFHCore.isModLoaded() && (block instanceof BlockWater || block instanceof BlockTickingWater);
    }

    public static boolean isWater(Block block) {
        return block == Blocks.flowing_water || block == Blocks.water || isCOFHWater(block);
    }
}
