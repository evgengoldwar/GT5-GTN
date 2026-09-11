package gregtech.api.modularui2;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

import org.jetbrains.annotations.Nullable;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.AbstractUIFactory;
import com.cleanroommc.modularui.factory.GuiData;
import com.cleanroommc.modularui.factory.GuiManager;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.network.NetworkUtils;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.api.interfaces.tileentity.IGTE;
import gregtech.api.metatileentity.BaseGTE;
import gregtech.api.metatileentity.MTE;
import gregtech.api.util.FieldsAreNonnullByDefault;
import gregtech.api.util.MethodsReturnNonnullByDefault;

/**
 * This GUI may be opened when the corresponding TileEntity is not loaded on the client!
 * On the client side, the mte is completely fictional
 * On the server side, the value must be synced to the actual tile entity
 */
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class ProxiedMteGui implements IGuiHolder<ProxiedMteGui.ProxiedMteGuiData> {

    public static final ProxiedMteGuiFactory GUI = new ProxiedMteGuiFactory();

    private final MTE mte;

    public ProxiedMteGui(MTE mte) {
        this.mte = mte;
    }

    public static void open(MTE mte, EntityPlayerMP player, IMTE[] mteArray) {
        GuiManager.open(GUI, new ProxiedMteGuiData(player, mte, mteArray), player);
    }

    @SideOnly(Side.CLIENT)
    public ModularScreen createScreen(ProxiedMteGuiData data, ModularPanel mainPanel) {
        return new GTModularScreen(mainPanel, mte.getGuiTheme());
    }

    public ModularPanel buildUI(ProxiedMteGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        IGTE base = mte.getGTE();
        ModularPanel panel = mte.buildUI(
            new PosGuiData(data.getPlayer(), base.getXCord(), base.getYCord(), base.getZCord()),
            syncManager,
            uiSettings);

        return panel;
    }

    final public static class ProxiedMteGuiData extends GuiData {

        final private @Nullable MTE serverMTE;
        final private int mid;
        final private int x;
        final private int y;
        final private int z;
        private IMTE[] mteArray;

        public ProxiedMteGuiData(EntityPlayer player, MTE serverMTE, IMTE[] mteArray) {
            super(player);
            this.serverMTE = serverMTE;
            IGTE base = serverMTE.getGTE();
            this.mid = base.getMeta();
            this.x = base.getXCord();
            this.y = base.getYCord();
            this.z = base.getZCord();
            this.mteArray = mteArray;
        }

        public ProxiedMteGuiData(EntityPlayer player, int mid, int x, int y, int z) {
            super(player);
            this.serverMTE = null;
            this.mid = mid;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public @Nullable MTE getServerMTE() {
            return serverMTE;
        }

        public int getMetaId() {
            return mid;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    final public static class ProxiedMteGuiFactory extends AbstractUIFactory<ProxiedMteGuiData> {

        ProxiedMteGuiFactory() {
            super("gregtech:crafting_input");
        }

        @Override
        public IGuiHolder<ProxiedMteGuiData> getGuiHolder(ProxiedMteGuiData data) {
            MTE mte = data.getServerMTE();
            if (mte == null) {
                // On the server, we must sync to the real MTE. A check that the real MTE is supplied.
                assert NetworkUtils.isClient();
                // Create empty fake MTE on client
                BaseGTE fakeBase = new BaseGTE();
                fakeBase.xCoord = data.getX();
                fakeBase.yCoord = data.getY();
                fakeBase.zCoord = data.getZ();
                fakeBase.setInitialValuesAsNBT(null, (short) data.getMetaId());
                mte = (MTE) fakeBase.getMTE();
                assert mte != null;
            }
            return new ProxiedMteGui(mte);
        }

        @Override
        public boolean canInteractWith(EntityPlayer player, ProxiedMteGuiData guiData) {
            IMTE mte = guiData.getServerMTE();
            // Note: seems only called server side and accessing the server only mte is safe, and we can assume it is
            // never null
            assert mte != null;
            IGTE base = mte.getGTE();
            return super.canInteractWith(player, guiData) && base != null && base.canAccessData();
        }

        @Override
        public void writeGuiData(ProxiedMteGuiData guiData, PacketBuffer buffer) {
            buffer.writeInt(guiData.getMetaId());
            buffer.writeInt(guiData.getX());
            buffer.writeInt(guiData.getY());
            buffer.writeInt(guiData.getZ());
        }

        @Override
        public ProxiedMteGuiData readGuiData(EntityPlayer player, PacketBuffer buffer) {
            return new ProxiedMteGuiData(
                player,
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt());
        }
    }
}
