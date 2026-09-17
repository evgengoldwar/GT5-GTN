package gregtech.api.enums;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.GTBlockIconContainer;
import gregtech.client.iconContainers.GTItemIconContainer;

public enum TexturesIcons implements IIconContainer {

    // spotless:off
    // Default path for Items: gregtech:textures/items/
    // Default path for Blocks: gregtech:textures/blocks/

    ARBOREAL_CASING(createBlockIcon("2"))
    ;
    // spotless:on

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
}
