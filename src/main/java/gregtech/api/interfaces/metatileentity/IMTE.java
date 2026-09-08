package gregtech.api.interfaces.metatileentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.utils.item.IItemHandlerModifiable;
import com.gtnewhorizon.gtnhlib.capability.CapabilityProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.modularui.IGetGUITextureSet;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IGregtechWailaProvider;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import gregtech.api.render.ISBRInventoryContext;
import gregtech.api.render.ISBRWorldContext;
import io.netty.buffer.ByteBuf;

public interface IMTE extends ISidedInventory, IFluidTank, IFluidHandler, IMachineBlockUpdateable,
    IGregtechWailaProvider, IGetGUITextureSet, IGregTechDeviceInformation, CapabilityProvider, IGuiHolder<PosGuiData> {

    IMTE newMTE(IGTE gte);

    ItemStack getStackForm(long amount);

    IGTE getGTE();

    void setGTE(IGTE gte);

    void initDefaultModes(NBTTagCompound nbt);

    void saveNBTData(NBTTagCompound nbt);

    void loadNBTData(NBTTagCompound nbt);

    void setItemNBT(NBTTagCompound nbt);

    void onConfigLoad();

    boolean allowCoverOnSide(ForgeDirection side, ItemStack coverItem);

    void onScrewdriverRightClick(ForgeDirection side, EntityPlayer player, float x, float y, float z, ItemStack tool);

    boolean onWrenchRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player, float x, float y,
        float z, ItemStack tool);

    boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player, float x,
        float y, float z, ItemStack tool);

    boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer player, float x,
        float y, float z, ItemStack tool);

    void onExplosion();

    void onFirstTick(IGTE gte);

    void onPreTick(IGTE gte, long tick);

    void onPostTick(IGTE gte, long tick);

    boolean needsClientTick();

    void onClientSoundStateChanged();

    void inValidate();

    void onRemoval();

    default void onUnload() {}

    boolean isFacingValid(ForgeDirection facing);

    boolean allowPullStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack);

    boolean allowPutStack(IGTE gte, int index, ForgeDirection side, ItemStack itemStack);

    boolean isValidSlot(int index);

    default boolean isIOSlot(int slot) {
        return true;
    }

    default int getStackSizeLimit(int slot, @Nullable ItemStack itemStack) {
        return Math.min(getSlotLimit(slot), itemStack == null ? 64 : itemStack.getMaxStackSize());
    }

    default int getSlotLimit(int slot) {
        return getInventoryStackLimit();
    }

    boolean shouldDropItemAt(int index);

    ArrayList<ItemStack> getDroppedItem();

    boolean setStackToZeroInsteadOfNull(int index);

    boolean isLiquidInput(ForgeDirection side);

    boolean isLiquidOutput(ForgeDirection side);

    String getMetaName();

    boolean isAccessAllowed(EntityPlayer player);

    boolean onRightClick(IGTE gte, EntityPlayer player, ForgeDirection side, float x, float y, float z);

    void onLeftClick(IGTE gte, EntityPlayer player);

    void onValueUpdate(byte value);

    byte getUpdateData();

    void receiveClientEvent(byte eventId, byte value);

    void doSound(byte index, double x, double y, double z);

    void startSoundLoop(byte index, double x, double y, double z);

    void stopSoundLoop(byte index, double x, double y, double z);

    void sendSound(byte index);

    void sendLoopStart(byte index);

    void sendLoopEnd(byte index);

    void doExplosion(long explosionPower);

    boolean doTickProfilingMessageDuringThisTick();

    ArrayList<String> getSpecialDebugInfo(IGTE gte, EntityPlayer player, int logLevel, ArrayList<String> aList);

    String[] getDescription();

    default String addFormattedString(String formattedStr) {
        return "%%%" + formattedStr + "%%%";
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SkipGenerateDescription {}

    String getSpecialVoltageToolTip();

    ITexture[] getTexture(IGTE gte, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active,
        boolean redstoneLevel);

    @SideOnly(Side.CLIENT)
    void registerIcons(IIconRegister iconRegister);

    @SideOnly(Side.CLIENT)
    boolean renderInInventory(ISBRInventoryContext ctx);

    @Nullable
    @SideOnly(Side.CLIENT)
    default ITexture[][] getInventoryTextures() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    boolean renderInWorld(ISBRWorldContext ctx);

    byte getComparatorValue(ForgeDirection side);

    float getExplosionResistance(ForgeDirection side);

    ItemStack[] getRealInventory();

    boolean connectsToItemPipe(ForgeDirection side);

    void onColorChangeServer(byte color);

    default void onColorChangeClient(byte color) {}

    default void writeToStream(ByteBuf buffer) {}

    default void readFromStream(ByteBuf buffer) {}

    default int getGUIColorization() {
        if (getGTE() != null) {
            return getGTE().getGUIColorization();
        } else {
            return Dyes.GUI_METAL.toInt();
        }
    }

    int getLightOpacity();

    boolean allowGeneralRedstoneOutput();

    void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB inputAABB,
        List<AxisAlignedBB> outputAABB, Entity collider);

    AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z);

    void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity collider);

    void onCreated(ItemStack itemStack, World world, EntityPlayer player);

    boolean hasAlternativeModeText();

    String getAlternativeModeText();

    default void onAdjacentBlockChange(int x, int y, int z) {}

    @SideOnly(Side.CLIENT)
    default void onRandomDisplayTick(IGTE gte) {}

    default int getGUIWidth() {
        return 176;
    }

    default int getGUIHeight() {
        return 166;
    }

    default IItemHandlerModifiable getInventoryHandler() {
        return null;
    }

    default String getLocalNameKey() {
        return "GT5U.gui.title.unknown";
    }

    default String getLocalName() {
        return StatCollector.translateToLocal(getLocalNameKey());
    }

    default boolean doesBindPlayerInventory() {
        return true;
    }

    default int getTextColorOrDefault(String textType, int defaultColor) {
        return defaultColor;
    }

    default void onBlockDestroyed() {}

    default void addAdditionalTooltipInformation(ItemStack itemStack, List<String> tooltip) {}

    @Nullable
    default List<ItemStack> getItemsForHoloGlasses() {
        return null;
    }

    String getGuiId();

    default void onTextureUpdate() {}
}
