package gregtech.api.graphs;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.graphs.consumers.ConsumerNode;
import gregtech.api.graphs.consumers.EmptyPowerConsumer;
import gregtech.api.graphs.consumers.NodeEnergyConnected;
import gregtech.api.graphs.consumers.NodeGTBaseMetaTile;
import gregtech.api.graphs.paths.NodePath;
import gregtech.api.graphs.paths.PowerNodePath;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.MetaPipeEntity;
import gregtech.api.metatileentity.implementations.MTECable;

// node map generator for power distribution
public class GenerateNodeMapPower extends GenerateNodeMap {

    public GenerateNodeMapPower(BaseMetaPipeEntity aTileEntity) {
        generateNode(aTileEntity, null, 1, null, ForgeDirection.UNKNOWN, new ArrayList<>(), new HashSet<>());
    }

    @Override
    protected boolean isPipe(TileEntity aTileEntity) {
        return super.isPipe(aTileEntity) && ((BaseMetaPipeEntity) aTileEntity).getMTE() instanceof MTECable;
    }

    @Override
    protected boolean addConsumer(TileEntity aTileEntity, ForgeDirection side, int aNodeValue,
        ArrayList<ConsumerNode> aConsumers) {
        if (aTileEntity instanceof BaseMetaTileEntity tBaseTileEntity) {
            if (tBaseTileEntity.inputEnergyFrom(side, false)) {
                ConsumerNode tConsumerNode = new NodeGTBaseMetaTile(aNodeValue, tBaseTileEntity, side, aConsumers);
                aConsumers.add(tConsumerNode);
                return true;
            }

        } else if (aTileEntity instanceof IEnergyConnected tTileEntity) {
            if (tTileEntity.inputEnergyFrom(side, false)) {
                ConsumerNode tConsumerNode = new NodeEnergyConnected(aNodeValue, tTileEntity, side, aConsumers);
                aConsumers.add(tConsumerNode);
                return true;
            }
        }
        return false;
    }

    @Override
    protected NodePath getNewPath(MetaPipeEntity[] aPipes) {
        return new PowerNodePath(aPipes);
    }

    // used to apply voltage on dead ends
    @Override
    protected Node getEmptyNode(int aNodeValue, ForgeDirection side, TileEntity aTileEntity,
        ArrayList<ConsumerNode> aConsumers) {
        ConsumerNode tNode = new EmptyPowerConsumer(aNodeValue, aTileEntity, side, aConsumers);
        aConsumers.add(tNode);
        return tNode;
    }

    @Override
    protected Node getPipeNode(int aNodeValue, ForgeDirection side, TileEntity aTileEntity,
        ArrayList<ConsumerNode> aConsumers) {
        return new PowerNode(aNodeValue, aTileEntity, aConsumers);
    }
}
