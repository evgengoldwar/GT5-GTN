package gregtech.api.enums;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.blocks.GTBlockIconContainer;

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

    private static IIconContainer createBlockIcon(String path, String name) {
        return GTBlockIconContainer.create(path, name);
    }
}
