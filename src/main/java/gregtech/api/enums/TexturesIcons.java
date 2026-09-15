package gregtech.api.enums;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.blocks.GTCustomBlockIconContainer;
import gregtech.client.iconContainers.items.GTCustomItemIconContainer;

public enum TexturesIcons implements IIconContainer {

    ;

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

    public static @NotNull IIconContainer createItemIcon(@NotNull String iconPath) {
        return GTCustomItemIconContainer.create(iconPath);
    }

    public static @NotNull IIconContainer createBlockIcon(@NotNull String iconPath) {
        return GTCustomBlockIconContainer.create(iconPath);
    }
}
