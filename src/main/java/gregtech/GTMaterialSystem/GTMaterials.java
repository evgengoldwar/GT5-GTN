package gregtech.GTMaterialSystem;

import gregtech.GTMaterialSystem.Api.GTMaterial;
import gregtech.GTMaterialSystem.Api.GTMaterialBuilder;
import gregtech.GTMaterialSystem.Api.GTMaterialRegistry;

public class GTMaterials {

    public static GTMaterial Steel;
    public static GTMaterial Bronze;

    public static void init() {
        GTMaterialRegistry.getInstance()
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

        Steel = new GTMaterialBuilder("Steel").meta(0)
            .color(200, 200, 200)
            .addIngot()
            .addBlock()
            .addPlate()
            .addCasing()
            .build();

        Bronze = new GTMaterialBuilder("Bronze").meta(1)
            .color(100, 100, 100)
            .addIngot()
            .addBlock()
            .build();
    }
}
