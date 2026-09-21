package gregtech.common.MaterialSystem;

import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialBuilder;
import gregtech.common.MaterialSystem.Api.MaterialRegistry;

public class Materials {

    public static Material Steel;
    public static Material Bronze;

    public static void init() {
        MaterialRegistry.getInstance()
            .init();

        // for (int i = 0; i < 25_000; i++) {
        // int color = i / 98;
        // new MaterialBuilder("Name" + i)
        // .color(color,color,color)
        // .meta(i)
        // .addBlock()
        // .addPlate()
        // .addIngot()
        // .build();
        // }

        Steel = new MaterialBuilder("Steel").meta(0)
            .color(200, 200, 200)
            .addIngot()
            .addBlock()
            .addPlate()
            .addCasing()
            .build();

        Bronze = new MaterialBuilder("Bronze").meta(1)
            .color(100, 100, 100)
            .addIngot()
            .addBlock()
            .build();
    }
}
