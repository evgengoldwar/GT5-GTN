package gregtech.api.interfaces.tileentity;

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import com.gtnewhorizon.gtnhlib.capability.CapabilityProvider;

import gregtech.api.enums.MTEArrays;
import gregtech.api.interfaces.IDescribable;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.modularui.IGetGUITextureSet;
import gregtech.api.util.shutdown.ShutDownReason;

public interface IGTE
    extends ITexturedTileEntity, ICoverable, IFluidHandler, ITurnable, IGregTechDeviceInformation, IUpgradableMachine,
    IDescribable, IMachineBlockUpdateable, IGregtechWailaProvider, IGetGUITextureSet, CapabilityProvider {

    int getMeta();

    void setMeta(short meta);

    IMTE getMTE();

    void setMTE(IMTE mte);

    void setMTEArrays(MTEArrays mteArrays);

    boolean canAccessData();

    void issueTextureUpdate();

    default void issueTileUpdate() {}

    void doExplosion(long explosionEu);

    void setOnFire();

    void setToFire();

    void setOwnerName(String name);

    String getOwnerName();

    UUID getOwnerUuid();

    void setOwnerUuid(UUID uuid);

    void setInitialValuesAsNBT(NBTTagCompound nbt, short meta);

    void onLeftClick(EntityPlayer player);

    boolean onRightClick(EntityPlayer player, ForgeDirection side, float x, float y, float z);

    ArrayList<ItemStack> getDrops();

    int getLightOpacity();

    @Override
    default void onMachineBlockUpdate() {
        if (!isDead() && getMTE() != null && getMTE().getGTE() == this) {
            getMTE().onMachineBlockUpdate();
        }
    }

    @Override
    default boolean isMachineBlockUpdateRecursive() {
        return !isDead() && getMTE() != null && getMTE().getGTE() == this && getMTE().isMachineBlockUpdateRecursive();
    }

    default void setShutdownStatus(boolean newStatus) {}

    default void setShutDownReason(@Nonnull ShutDownReason reason) {}

    void enableTicking();

    void tryDisableTicking();

    default int[] getTimeStatistics() {
        return null;
    }

    default void startTimeStatistics() {}

    default boolean isSteampowered() {
        return false;
    }
}
