package gregtech.api.modpack.example;

import gregtech.common.MaterialSystem.Api.Material;
import gregtech.common.MaterialSystem.Api.MaterialBuilder;
import gregtech.common.MaterialSystem.Api.MaterialType;

/**
 * Example materials. Metas are the stable ids of the materials - never change them after release.
 */
public final class ExampleMaterials {

    public static Material Iron;
    public static Material Copper;
    public static Material Tin;
    public static Material Ruby;
    public static Material Oxygen;

    private ExampleMaterials() {}

    public static void register() {
        // everything a GregTech metal has + plasma
        Iron = new MaterialBuilder("Iron").meta(100)
            .color(0xC8C8C8)
            .addMetalParts() // dust, nugget, ingot, plate, rod, bolt, screw, ring, gear, block, frame, molten
            .addCasing()
            .addPlasma()
            .meltingPoint(1811)
            .tooltip("Fe")
            .build();

        // pick parts one by one
        Copper = new MaterialBuilder("Copper").meta(101)
            .color(0xFF6400)
            .addIngot()
            .addDust()
            .addPlate()
            .addFineWire()
            .addMolten()
            .meltingPoint(1357)
            .tooltip("Cu")
            .build();

        Tin = new MaterialBuilder("Tin").meta(102)
            .color(0xDCDCDC)
            .add(MaterialType.INGOT, MaterialType.PLATE, MaterialType.ROD, MaterialType.MOLTEN)
            .meltingPoint(505)
            .build();

        // gem with its own icon set: textures in MaterialSystems/Items/gem_shiny/<type>.png (fallback to default)
        Ruby = new MaterialBuilder("Ruby").meta(103)
            .color(0xFF6464)
            .addGemParts()
            .iconSet("gem_shiny")
            .tooltip("Al2O3Cr")
            .build();

        // gas only
        Oxygen = new MaterialBuilder("Oxygen").meta(104)
            .color(0x90C8FF)
            .addGas()
            .addPlasma()
            .tooltip("O")
            .build();
    }
}
