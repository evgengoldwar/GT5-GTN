package gregtech.api.util;

import net.minecraft.item.ItemStack;

import gregtech.api.interfaces.IGTScannerHandler;

public class GTScannerResult {
    public int eut;
    public int duration;
    public int inputConsume;
    public int specialConsume;
    public int fluidConsume;
    public ItemStack output;
    public IGTScannerHandler handler;

    public GTScannerResult(int eut, int duration, int inputConsume, int specialConsume, int fluidConsume,
        ItemStack output) {
        this.eut = eut;
        this.duration = duration;
        this.inputConsume = inputConsume;
        this.specialConsume = specialConsume;
        this.fluidConsume = fluidConsume;
        this.output = output;
        this.handler = null;
    }
}
