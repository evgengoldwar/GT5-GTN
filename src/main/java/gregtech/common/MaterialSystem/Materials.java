package gregtech.common.MaterialSystem;

import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialBuilder;
import gregtech.common.MaterialSystem.Api.MaterialRegistry;

import java.util.Random;

public class Materials {

    public static Material Steel;
    public static Material Bronze;

    public static void init() {
        MaterialRegistry.getInstance()
            .init();

        Random random = new Random();

        for (int i = 1; i < 5_000; i++) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            new MaterialBuilder("Steel_" + i).meta(i)
                .color(r, g, b)
                .addIngot()
                .addPlate()
                .addBlock()
                .addCasing()
                .addFluid()
                .build();
        }
    }
}
