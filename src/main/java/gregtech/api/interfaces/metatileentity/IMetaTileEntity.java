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
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IGregtechWailaProvider;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import gregtech.api.render.ISBRInventoryContext;
import gregtech.api.render.ISBRWorldContext;
import io.netty.buffer.ByteBuf;

public interface IMetaTileEntity extends ISidedInventory, IFluidTank, IFluidHandler, IMachineBlockUpdateable,
    IGregtechWailaProvider, IGetGUITextureSet, IGregTechDeviceInformation, CapabilityProvider, IGuiHolder<PosGuiData> {

    IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    ItemStack getStackForm(long aAmount);

    IGregTechTileEntity getBaseMetaTileEntity();

    void setBaseMetaTileEntity(IGregTechTileEntity aBaseMetaTileEntity);

    void initDefaultModes(NBTTagCompound aNBT);

    void saveNBTData(NBTTagCompound aNBT);

    void loadNBTData(NBTTagCompound aNBT);

    void setItemNBT(NBTTagCompound aNBT);

    void onConfigLoad();

    boolean allowCoverOnSide(ForgeDirection side, ItemStack coverItem);

    void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool);

    boolean onWrenchRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer entityPlayer, float aX,
        float aY, float aZ, ItemStack aTool);

    boolean onWireCutterRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer entityPlayer,
        float aX, float aY, float aZ, ItemStack aTool);

    boolean onSolderingToolRightClick(ForgeDirection side, ForgeDirection wrenchingSide, EntityPlayer entityPlayer,
        float aX, float aY, float aZ, ItemStack aTool);

    void onExplosion();

    void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity);

    void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

    void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick);

    boolean needsClientTick();

    void onClientSoundStateChanged();

    void inValidate();

    void onRemoval();

    default void onUnload() {}

    boolean isFacingValid(ForgeDirection facing);

    boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side, ItemStack aStack);

    boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side, ItemStack aStack);

    boolean isValidSlot(int aIndex);

    default boolean isIOSlot(int slot) {
        return true;
    }

    default int getStackSizeLimit(int slot, @Nullable ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack == null ? 64 : stack.getMaxStackSize());
    }

    default int getSlotLimit(int slot) {
        return getInventoryStackLimit();
    }

    boolean shouldDropItemAt(int index);

    ArrayList<ItemStack> getDroppedItem();

    boolean setStackToZeroInsteadOfNull(int aIndex);

    boolean isLiquidInput(ForgeDirection side);

    boolean isLiquidOutput(ForgeDirection side);

    String getMetaName();

    boolean isAccessAllowed(EntityPlayer aPlayer);

    boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, ForgeDirection side, float aX,
        float aY, float aZ);

    void onLeftclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer);

    void onValueUpdate(byte aValue);

    byte getUpdateData();

    void receiveClientEvent(byte aEventID, byte aValue);

    void doSound(byte aIndex, double aX, double aY, double aZ);

    void startSoundLoop(byte aIndex, double aX, double aY, double aZ);

    void stopSoundLoop(byte aValue, double aX, double aY, double aZ);

    void sendSound(byte aIndex);

    void sendLoopStart(byte aIndex);

    void sendLoopEnd(byte aIndex);

    void doExplosion(long aExplosionPower);

    boolean doTickProfilingMessageDuringThisTick();

    ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, int aLogLevel,
        ArrayList<String> aList);

    String[] getDescription();

    default String addFormattedString(String formattedStr) {
        return "%%%" + formattedStr + "%%%";
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface SkipGenerateDescription {}

    String getSpecialVoltageToolTip();

    ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean active, boolean redstoneLevel);

    @SideOnly(Side.CLIENT)
    void registerIcons(IIconRegister aBlockIconRegister);

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

    void onColorChangeServer(byte aColor);

    default void onColorChangeClient(byte aColor) {}

    default void writeToStream(ByteBuf buffer) {}

    default void readFromStream(ByteBuf buffer) {}

    default int getGUIColorization() {
        if (getBaseMetaTileEntity() != null) {
            return getBaseMetaTileEntity().getGUIColorization();
        } else {
            return Dyes.GUI_METAL.toInt();
        }
    }

    int getLightOpacity();

    boolean allowGeneralRedstoneOutput();

    void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB,
        List<AxisAlignedBB> outputAABB, Entity collider);

    AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ);

    void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider);

    void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer);

    boolean hasAlternativeModeText();

    String getAlternativeModeText();

    @Override
    default boolean isMachineBlockUpdateRecursive() {
        return true;
    }

    default void onAdjacentBlockChange(int x, int y, int z) {}

    @SideOnly(Side.CLIENT)
    default void onRandomDisplayTick(IGregTechTileEntity aBaseMetaTileEntity) {}

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

    default void addAdditionalTooltipInformation(ItemStack stack, List<String> tooltip) {}

    @Nullable
    default List<ItemStack> getItemsForHoloGlasses() {
        return null;
    }

    String getGuiId();

    default void onTextureUpdate() {}
}
