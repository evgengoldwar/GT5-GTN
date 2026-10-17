package gregtech.api.modpack.hatch;

/**
 * Kinds of hatches a multiblock can accept in a structure position.
 */
public enum HatchType {

    INPUT_BUS("Input Bus", "input_bus"),
    OUTPUT_BUS("Output Bus", "output_bus"),
    INPUT_HATCH("Input Hatch", "input_hatch"),
    OUTPUT_HATCH("Output Hatch", "output_hatch"),
    ENERGY("Energy Hatch", "energy"),
    DYNAMO("Dynamo Hatch", "dynamo"),
    /** Your own hatch kinds (muffler, coolant, ...): override {@code addHatch} in the multiblock to handle them. */
    OTHER("Hatch", "other");

    public final String localName;
    public final String textureName;

    HatchType(String localName, String textureName) {
        this.localName = localName;
        this.textureName = textureName;
    }

    /** All item and fluid in/out hatches + energy: what a typical processing multiblock accepts. */
    public static final HatchType[] PROCESSING = { INPUT_BUS, OUTPUT_BUS, INPUT_HATCH, OUTPUT_HATCH, ENERGY };
}
