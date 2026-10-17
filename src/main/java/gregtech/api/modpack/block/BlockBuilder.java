package gregtech.api.modpack.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.modpack.ModpackAPI;
import gregtech.api.modpack.util.Lang;

/**
 * Builder for plain decorative / crafting blocks.
 *
 * <pre>{@code
 * public static SimpleBlock MARBLE;
 *
 * MARBLE = BlockBuilder.create("marble")
 *     .name("Marble")
 *     .texture("blocks_custom/marble")         // assets/gregtech/textures/blocks/blocks_custom/marble.png
 *     .hardness(1.5F)
 *     .pickaxe(0)
 *     .oreDict("stoneMarble")
 *     .build();
 *
 * // many blocks in a loop
 * for (String color : new String[] { "red", "green", "blue" }) {
 *     BlockBuilder.create("concrete_" + color).name(color + " Concrete").texture("concrete/" + color).build();
 * }
 * }</pre>
 */
public final class BlockBuilder {

    /** All created blocks, in creation order. */
    private static final List<SimpleBlock> BLOCKS = new ArrayList<>();

    @FunctionalInterface
    public interface RightClickHandler {

        /**
         * @return true if the click was handled (prevents item use)
         */
        boolean onRightClick(World world, int x, int y, int z, EntityPlayer player, int side);
    }

    @FunctionalInterface
    public interface LeftClickHandler {

        void onLeftClick(World world, int x, int y, int z, EntityPlayer player);
    }

    @FunctionalInterface
    public interface WalkHandler {

        void onWalk(World world, int x, int y, int z, Entity entity);
    }

    static final class Properties {

        String id;
        String unlocalizedName;
        String localName;
        Material material = Material.rock;
        Block.SoundType sound = Block.soundTypeStone;
        float hardness = 2.0F;
        float resistance = 10.0F;
        float lightLevel = 0.0F;
        String harvestTool = "pickaxe";
        int harvestLevel = 0;
        CreativeTabs creativeTab = GregTechAPI.TAB_GREGTECH;
        final String[] textures = new String[6];
        int color = 0xFFFFFF;
        boolean transparent;
        boolean translucent;
        boolean beaconBase;
        boolean witherProof;
        ItemStack drop;
        final List<String> tooltips = new ArrayList<>();
        final List<String> oreDicts = new ArrayList<>();
        RightClickHandler onRightClick;
        LeftClickHandler onLeftClick;
        WalkHandler onWalk;
    }

    private final Properties p = new Properties();

    private BlockBuilder(String id) {
        p.id = id;
        p.unlocalizedName = "gt.block." + id;
        p.localName = id;
        Arrays.fill(p.textures, "blocks_custom/" + id);
    }

    /**
     * @param id registry name, lower case without spaces. Never change it after release - worlds store it.
     */
    public static BlockBuilder create(String id) {
        if (!id.equals(id.toLowerCase(Locale.ENGLISH)) || id.contains(" ")) {
            throw new IllegalArgumentException("Block id must be lower case without spaces: " + id);
        }
        return new BlockBuilder(id);
    }

    public static List<SimpleBlock> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    /** English display name. */
    public BlockBuilder name(String localName) {
        p.localName = localName;
        return this;
    }

    /** Russian display name. */
    public BlockBuilder nameRu(String localName) {
        Lang.add("tile." + p.unlocalizedName + ".name", Lang.RU, localName);
        return this;
    }

    /**
     * Same texture on all sides. Path relative to {@code assets/gregtech/textures/blocks/}, or {@code "mod:path"}.
     * Default: {@code blocks_custom/<id>}.
     */
    public BlockBuilder texture(String texture) {
        Arrays.fill(p.textures, texture);
        return this;
    }

    /** Different texture for top, bottom and sides (like a log or a furnace). */
    public BlockBuilder texture(String top, String bottom, String side) {
        p.textures[0] = bottom;
        p.textures[1] = top;
        for (int i = 2; i < 6; i++) {
            p.textures[i] = side;
        }
        return this;
    }

    /** Texture per side in forge order: down, up, north, south, west, east. */
    public BlockBuilder textures(String down, String up, String north, String south, String west, String east) {
        p.textures[0] = down;
        p.textures[1] = up;
        p.textures[2] = north;
        p.textures[3] = south;
        p.textures[4] = west;
        p.textures[5] = east;
        return this;
    }

    /** Color multiplier as 0xRRGGBB, handy to make many recolored blocks from one grayscale texture. */
    public BlockBuilder color(int rgb) {
        p.color = rgb;
        return this;
    }

    public BlockBuilder material(Material material) {
        p.material = material;
        return this;
    }

    public BlockBuilder sound(Block.SoundType sound) {
        p.sound = sound;
        return this;
    }

    public BlockBuilder hardness(float hardness) {
        p.hardness = hardness;
        return this;
    }

    public BlockBuilder resistance(float resistance) {
        p.resistance = resistance;
        return this;
    }

    public BlockBuilder unbreakable() {
        p.hardness = -1.0F;
        p.resistance = 6_000_000.0F;
        return this;
    }

    /** @param level 0.0 - 1.0 like vanilla (1.0 = glowstone) */
    public BlockBuilder light(float level) {
        p.lightLevel = level;
        return this;
    }

    public BlockBuilder tool(String tool, int level) {
        p.harvestTool = tool;
        p.harvestLevel = level;
        return this;
    }

    public BlockBuilder pickaxe(int level) {
        return tool("pickaxe", level);
    }

    public BlockBuilder axe(int level) {
        return tool("axe", level);
    }

    public BlockBuilder shovel(int level) {
        return tool("shovel", level);
    }

    public BlockBuilder creativeTab(CreativeTabs tab) {
        p.creativeTab = tab;
        return this;
    }

    /** Cutout block like glass: neighbours of the same block do not render shared faces. */
    public BlockBuilder transparent() {
        p.transparent = true;
        return this;
    }

    /** Semi transparent block like stained glass or ice (alpha blending, render pass 1). */
    public BlockBuilder translucent() {
        p.transparent = true;
        p.translucent = true;
        return this;
    }

    public BlockBuilder beaconBase() {
        p.beaconBase = true;
        return this;
    }

    public BlockBuilder witherProof() {
        p.witherProof = true;
        return this;
    }

    /** Drops this stack instead of the block (ores, gravel like blocks). */
    public BlockBuilder drops(ItemStack drop) {
        p.drop = drop;
        return this;
    }

    public BlockBuilder tooltip(String... lines) {
        Collections.addAll(p.tooltips, lines);
        return this;
    }

    public BlockBuilder oreDict(String... names) {
        Collections.addAll(p.oreDicts, names);
        return this;
    }

    public BlockBuilder onRightClick(RightClickHandler handler) {
        p.onRightClick = handler;
        return this;
    }

    public BlockBuilder onLeftClick(LeftClickHandler handler) {
        p.onLeftClick = handler;
        return this;
    }

    public BlockBuilder onWalk(WalkHandler handler) {
        p.onWalk = handler;
        return this;
    }

    public SimpleBlock build() {
        ModpackAPI.checkPhase(ModpackAPI.Phase.CONTENT, "Block " + p.id);
        SimpleBlock block = new SimpleBlock(p);
        GameRegistry.registerBlock(block, SimpleItemBlock.class, p.id);
        Lang.add("tile." + p.unlocalizedName + ".name", p.localName);
        for (String oreDict : p.oreDicts) {
            OreDictionary.registerOre(oreDict, block.getStack());
        }
        BLOCKS.add(block);
        return block;
    }
}
