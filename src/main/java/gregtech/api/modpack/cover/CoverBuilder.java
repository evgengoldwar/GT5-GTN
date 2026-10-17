package gregtech.api.modpack.cover;

import net.minecraft.item.ItemStack;

import gregtech.api.covers.CoverFactory;
import gregtech.api.covers.CoverPlacementPredicate;
import gregtech.api.covers.CoverPlacer;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.interfaces.ITexture;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.item.ItemEntry;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Turns an item into a GregTech cover (shift + right click on a machine, pipe or cable with the item).
 *
 * <pre>{@code
 * // items for the covers (in registerItems)
 * SOLAR_COVER = COVERS.add(1, "solar_panel").name("Solar Panel").build();
 * CONVEYOR_LV = COVERS.add(2, "conveyor_lv").name("LV Conveyor Cover").tooltip("1 stack every second").build();
 *
 * // covers (in registerCovers)
 * CoverBuilder.of(SOLAR_COVER).texture("covers/solar_panel").behavior(CoverBehaviors.solarPanel(8)).register();
 * CoverBuilder.of(CONVEYOR_LV).texture("covers/conveyor").behavior(CoverBehaviors.conveyor(1, 20)).register();
 *
 * // pure decoration, no logic
 * CoverBuilder.of(Materials.Steel.getPlate()).texture("covers/steel_plate").decorative().register();
 *
 * // completely custom cover class
 * CoverBuilder.of(MY_ITEM).texture("covers/my").factory(context -> new MyCover(context, texture)).register();
 * }</pre>
 *
 * Textures are block textures, e.g. {@code assets/gregtech/textures/blocks/covers/solar_panel.png}.
 */
public final class CoverBuilder {

    private final ItemStack item;
    private ITexture texture;
    private CoverBehavior behavior;
    private CoverFactory factory;
    private boolean decorative;
    private boolean primitive;
    private boolean blocksGui;
    private CoverPlacementPredicate predicate;

    private CoverBuilder(ItemStack item) {
        this.item = item;
    }

    public static CoverBuilder of(ItemStack item) {
        return new CoverBuilder(item);
    }

    public static CoverBuilder of(ItemEntry item) {
        return new CoverBuilder(item.get());
    }

    /** Block texture path drawn on the covered face. */
    public CoverBuilder texture(String path) {
        this.texture = TextureFactory.of(ResourceIcon.block(path));
        return this;
    }

    public CoverBuilder texture(ITexture texture) {
        this.texture = texture;
        return this;
    }

    public CoverBuilder behavior(CoverBehavior behavior) {
        this.behavior = behavior;
        return this;
    }

    /** Own {@link gregtech.common.covers.Base.Cover} subclass. */
    public CoverBuilder factory(CoverFactory factory) {
        this.factory = factory;
        return this;
    }

    /** No logic, screwdriver toggles what passes through (like GregTech plate covers). */
    public CoverBuilder decorative() {
        this.decorative = true;
        return this;
    }

    /** Can also be placed on non GregTech blocks that support covers (e.g. primitive machines). */
    public CoverBuilder allowOnPrimitive() {
        this.primitive = true;
        return this;
    }

    /** Right click on the covered side does not open the machine GUI. */
    public CoverBuilder blocksMachineGui() {
        this.blocksGui = true;
        return this;
    }

    /** Extra placement condition, e.g. only on machines: {@code (side, stack, tile) -> tile instanceof IGTE}. */
    public CoverBuilder onlyIf(CoverPlacementPredicate predicate) {
        this.predicate = predicate;
        return this;
    }

    public void register() {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Cover");
        if (item == null) {
            throw new IllegalArgumentException("Cover item is null");
        }
        ITexture coverTexture = texture;
        if (decorative) {
            CoverRegistry.registerDecorativeCover(item, coverTexture);
            return;
        }
        CoverFactory coverFactory = factory;
        if (coverFactory == null) {
            CoverBehavior coverBehavior = behavior != null ? behavior : new CoverBehavior() {};
            coverFactory = context -> new SimpleCover(context, coverTexture, coverBehavior);
        }
        CoverPlacer.CoverPlacerBuilder placer = CoverPlacer.builder();
        if (primitive) {
            placer.allowOnPrimitiveBlock();
        }
        if (blocksGui) {
            placer.blocksCoverableGuiOpening();
        }
        if (predicate != null) {
            placer.onlyPlaceIf(predicate);
        }
        CoverRegistry.registerCover(item, coverTexture, coverFactory, placer.build());
    }
}
