package gregtech.api.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.GTBlockIconContainer;
import gregtech.client.iconContainers.GTItemIconContainer;
import gregtech.client.iconContainers.IconContainer;

public enum Textures implements IIconContainer {

    // spotless:off
    // Default path for Items: gregtech:textures/items/
    // Default path for Blocks: gregtech:textures/blocks/
    MISSING(IconContainer.MISSING_CONTAINER),
    INVISIBLE(IconContainer.INVISIBLE_CONTAINER),
    MISING_INVISIBLE(IconContainer.MISSING_INVISIBLE_CONTAINER),
    ARBOREAL_CASING(createBlockIcon("2"))
    ;
    // spotless:on

    public static final List<Textures> ALL_ICONS = new ArrayList<>();
    private final IIconContainer container;

    Textures(@NotNull IIconContainer container) {
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

    public static void init() {
        ALL_ICONS.addAll(Arrays.asList(Textures.values()));
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
