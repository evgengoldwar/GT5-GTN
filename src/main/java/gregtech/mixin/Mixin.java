package gregtech.mixin;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixin implements IMixins {

    // spotless:off
    // Minecraft
    GregtechCapes(new MixinBuilder()
        .addClientMixins("minecraft.AbstractClientPlayerMixin")
        .setPhase(Phase.EARLY)),
    StringTranslateMixin(new MixinBuilder("Keep track of currently translating mods")
        .addCommonMixins(
            "minecraft.StringTranslateMixin",
            "minecraft.LanguageRegistryMixin")
        .setPhase(Phase.EARLY)),
    LocaleMixin(new MixinBuilder("Keep track of currently translating client mods")
        .addClientMixins("minecraft.LocaleMixin")
        .setPhase(Phase.EARLY)),
    VANILLA_ACCESSORS(new MixinBuilder()
        .addCommonMixins(
            "minecraft.accessors.BlockStemMixin",
            "minecraft.accessors.ChunkCacheMixin",
            "minecraft.accessors.VanillaShapedRecipeMixin",
            "minecraft.accessors.VanillaShapelessRecipeMixin",
            "minecraft.accessors.ForgeShapedRecipeMixin",
            "minecraft.accessors.ForgeShapelessRecipeMixin",
            "minecraft.accessors.ItemArmorMixin",
            "minecraft.accessors.PotionMixin",
            "minecraft.accessors.EntityPlayerMPMixin",
            "minecraft.accessors.WeightedRandomFishableMixin",
            "minecraft.accessors.EntityMixin",
            "minecraft.accessors.LanguageRegistryMixin",
            "minecraft.accessors.EntityItemMixin")
        .addClientMixins(
            "minecraft.accessors.GuiTextFieldMixin",
            "minecraft.accessors.TessellatorMixin")
        .setPhase(Phase.EARLY)),
    RemoveItemStack(new MixinBuilder()
        .addCommonMixins(
            "minecraft.ItemStackMixin_MetaItemRemover"
        )
        .setPhase(Phase.EARLY)
    ),
    ItemMixinCoverFix(new MixinBuilder("Allow cover items to bypass sneak checks")
        .addCommonMixins("minecraft.ItemMixin")
        .setPhase(Phase.EARLY)),
    SplitPhysicRenderingPipeBoundingBox(
        new MixinBuilder("Allows pipes to have different bounding box for the rendering, physics and player pickblock")
            .addClientMixins("minecraft.MinecraftMixin_MouseOver")
            .setPhase(Phase.EARLY)),

    HEEAccessors(new MixinBuilder("Various accessors for Hardcore Ender Expansion")
        .addCommonMixins("hee.ChunkProviderHardcoreEndMixin", "hee.MapGenIslandMixin")
        .addRequiredMod(TargetedMod.HEE)
        .setPhase(Phase.LATE)),

    ForgeHooksMixin(new MixinBuilder("Adds missing hooks in ForgeHooks")
        .addCommonMixins("forge.ForgeHooksMixin")
        .setPhase(Phase.EARLY)),

    IC2_MACHINE_WRENCHING(new MixinBuilder("Changes the behavior of the wrenching mechanic for IC2 machines")
        .addCommonMixins(
            "ic2.MixinDamageDropped",
            "ic2.MixinHarvestTool",
            "ic2.MixinItemDropped")
        .addRequiredMod(TargetedMod.IC2)
        .setPhase(Phase.LATE)),

    IC2_REMOVE_FISSION_FUELS(new MixinBuilder()
        .addCommonMixins("ic2.MixinIc2FissionFuelRemoval")
        .addRequiredMod(TargetedMod.IC2)
        .setPhase(Phase.LATE)),
    // Hazmat armors
    IC2_HAZMAT(new MixinBuilder()
        .setPhase(Phase.LATE)
        .addCommonMixins(
            "ic2.MixinIc2Hazmat",
            "ic2.MixinIc2Nano",
            "ic2.MixinIc2Quantum")
        .addRequiredMod(TargetedMod.IC2)
        .addExcludedMod(TargetedMod.GT6)),
    ADV_SOLAR_HAZMAT(new MixinBuilder("Applies Hazmat API to Advanced Solar helmets")
        .setPhase(Phase.LATE)
        .addCommonMixins("advanced_solar_panels.MixinAdvancedSolarHelmet")
        .addRequiredMod(TargetedMod.ADVANCED_SOLAR_PANELS)),

    VANILLA_TRADING(new MixinBuilder()
        .setPhase(Phase.EARLY)
        .addCommonMixins("minecraft.VanillaTradingMixin"));
    // spotless:on

    private final MixinBuilder builder;

    Mixin(MixinBuilder builder) {
        this.builder = builder;
    }

    @NotNull
    @Override
    public MixinBuilder getBuilder() {
        return this.builder;
    }
}
