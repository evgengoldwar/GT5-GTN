package gregtech.api.enums;

import static gregtech.api.util.GTUtility.getMissingNo;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.GTBlockIconContainer;
import gregtech.client.iconContainers.GTIconContainer;
import gregtech.client.iconContainers.GTItemIconContainer;

public enum TexturesIcons implements IIconContainer {

    // Default path for Items: gregtech:textures/items/
    // Default path for Blocks: gregtech:textures/blocks/
    ERROR(createIcon(getMissingNo())),

    ARBOREAL_CASING(createBlockIcon("2"));

    private final IIconContainer container;

    TexturesIcons(@NotNull IIconContainer container) {
        this.container = container;
    }

    @Override
    public IIcon getIcon() {
        return container.getIcon();
    }

    @Override
    public IIcon getOverlayIcon() {
        return container.getOverlayIcon();
    }

    @Override
    public ResourceLocation getTextureFile() {
        return container.getTextureFile();
    }

    private static IIconContainer createBlockIcon(String path, String name) {
        return GTBlockIconContainer.create(path, name);
    }

    private static IIconContainer createBlockIcon(String name) {
        return createBlockIcon("", name);
    }

    private static IIconContainer createItemIcon(String path, String name) {
        return GTItemIconContainer.create(path, name);
    }

    private static IIconContainer createItemIcon(String name) {
        return createItemIcon("", name);
    }

    private static IIconContainer createIcon(Item item, int meta) {
        IIcon icon = item.getIconFromDamage(meta);
        return new GTIconContainer(icon);
    }

    private static IIconContainer createIcon(Item item) {
        return createIcon(item, 0);
    }

    private static IIconContainer createIcon(Block block, int meta, int side) {
        IIcon icon = block.getIcon(side, meta);
        return new GTIconContainer(icon);
    }

    private static IIconContainer createIcon(Block block, int meta) {
        return createIcon(block, meta, 0);
    }

    private static IIconContainer createIcon(Block block) {
        return createIcon(block, 0, 0);
    }

    private static IIconContainer createIcon(ItemStack itemStack) {
        IIcon icon = itemStack.getIconIndex();
        return new GTIconContainer(icon);
    }

    private static IIconContainer createIcon(IIcon icon, IIcon overlay) {
        return new GTIconContainer(icon, overlay);
    }

    private static IIconContainer createIcon(IIcon icon) {
        return createIcon(icon, null);
    }
}
