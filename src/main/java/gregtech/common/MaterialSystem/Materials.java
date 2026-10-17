package gregtech.common.MaterialSystem;

import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialBuilder;

public class Materials {

    public static Material Steel;
    public static Material Bronze;

    public static void init() {
        // MaterialRegistry.init() is called by ModpackAPI before this method.
        Steel = new MaterialBuilder("Steel").meta(1)
            .color(128, 128, 0)
            .addIngot()
            .addBlock()
            .addCasing()
            .addFluid()
            .build();

        Bronze = new MaterialBuilder("Bronze").meta(2)
            .color(200, 100, 50)
            .addIngot()
            .addPlate()
            .addBlock()
            .addCasing()
            .addFluid()
            .build();

        // Random random = new Random();
        //
        // for (int i = 1; i < 5_000; i++) {
        // int r = random.nextInt(256);
        // int g = random.nextInt(256);
        // int b = random.nextInt(256);
        //
        // new MaterialBuilder("Steel_" + i).meta(i)
        // .color(r, g, b)
        // .addIngot()
        // .addPlate()
        // .addBlock()
        // .addCasing()
        // .addFluid()
        // .build();
        // }
    }
}
