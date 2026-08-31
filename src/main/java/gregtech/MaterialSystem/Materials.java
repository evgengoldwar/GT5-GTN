package gregtech.MaterialSystem;

import gregtech.MaterialSystem.Api.Material;
import gregtech.MaterialSystem.Api.MaterialBuilder;
import gregtech.MaterialSystem.Api.MaterialRegistry;

public class Materials {

    public static Material Steel;
    public static Material Bronze;

    public static void init() {
        MaterialRegistry.getInstance().init();

        Steel = new MaterialBuilder("Steel")
            .meta(0)
            .color(200, 200, 200)
            .addIngot()
            .addPlate()
            .build();

        Bronze = new MaterialBuilder("Bronze")
            .meta(1)
            .color(100, 100, 100)
            .addIngot()
            .build();

        for (int i = 2; i < 65_000; i++) {
            new MaterialBuilder("Name_" + i)
                .meta(i)
                .addIngot()
                .addPlate()
                .build();
        }
    }
}
