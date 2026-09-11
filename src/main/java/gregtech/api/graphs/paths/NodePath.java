package gregtech.api.graphs.paths;

import gregtech.api.graphs.Lock;
import gregtech.api.metatileentity.BasePipeGTE;
import gregtech.api.metatileentity.PipeMTE;

// to contain all info about the path between nodes
public class NodePath {

    protected PipeMTE[] mPipes;
    public Lock lock = new Lock();

    public NodePath(PipeMTE[] aCables) {
        this.mPipes = aCables;
        processPipes();
    }

    public PipeMTE[] getPipes() {
        return mPipes;
    }

    protected void processPipes() {
        for (PipeMTE tPipe : mPipes) {
            BasePipeGTE basePipe = (BasePipeGTE) tPipe.getGTE();
            basePipe.setNodePath(this);
        }
    }

    public void clearPath() {
        for (PipeMTE mPipe : mPipes) {
            BasePipeGTE tBasePipe = (BasePipeGTE) mPipe.getGTE();
            if (tBasePipe != null) {
                tBasePipe.setNodePath(null);
            }
        }
    }

    public void reloadLocks() {
        for (PipeMTE pipe : mPipes) {
            BasePipeGTE basePipe = (BasePipeGTE) pipe.getGTE();
            if (basePipe != null) {
                basePipe.reloadLocks();
            }
        }
    }
}
