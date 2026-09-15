package gregtech.api.enums;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ICasingTextureProvider;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;

public class Textures {

    public static final String TextureMaterialIconDirectory = "materialicons/";
    public static final String TextureSetFallback = "NONE";
    public static final String OverlaySuffix = "_OVERLAY";

    // spotless:off
    public enum InvisibleIcon implements IIcon {
        INVISIBLE_ICON;

        @Override public int    getIconWidth()  { return 1; }
        @Override public int    getIconHeight() { return 1; }
        @Override public float  getMinU()       { return 0.999f; }
        @Override public float  getMaxU()       { return 1.0f;   }
        @Override public float  getMinV()       { return 0.999f; }
        @Override public float  getMaxV()       { return 1.0f;   }
        @Override public float  getInterpolatedU(double u)  { return 1.0f; }
        @Override public float  getInterpolatedV(double v)  { return 1.0f; }

        @Override public String getIconName()   { return "invisible"; }
    }
    // spotless:on

    public enum GlobalIcons implements IIconContainer {

        RENDERING_ERROR(null, null),
        VOID(InvisibleIcon.INVISIBLE_ICON, InvisibleIcon.INVISIBLE_ICON);

        // Atlas sprites are replaced during stitching
        // RenderInit clears this after the block atlas rebuilds
        private static IIcon missingIcon;

        private final IIcon mIcon, mOverlay;

        GlobalIcons(IIcon icon, IIcon overlay) {
            mIcon = icon;
            mOverlay = overlay;
        }

        @Override
        public IIcon getIcon() {
            if (mIcon == null) return getMissingNo();
            return mIcon;
        }

        @Override
        public IIcon getOverlayIcon() {
            if (mOverlay == null) return getMissingNo();
            return mOverlay;
        }

        public static void invalidateMissingIconCache() {
            missingIcon = null;
        }

        @Override
        public ResourceLocation getTextureFile() {
            return TextureMap.locationItemsTexture;
        }

        private static IIcon getMissingNo() {
            if (missingIcon == null) {
                missingIcon = ((TextureMap) Minecraft.getMinecraft()
                    .getTextureManager()
                    .getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
            }
            return missingIcon;
        }
    }

    public static final class BlockIcons {

        // spotless:off
        public static final IIconContainer
            VOID = GlobalIcons.VOID, // The Empty Texture
            RENDERING_ERROR = GlobalIcons.RENDERING_ERROR;
        // semicolon after the comment to reduce merge conflicts
        ;

        public static final ITexture[] HIDDEN_TEXTURE = { TextureFactory.builder()
            .addIcon(RENDERING_ERROR)
            .stdOrient()
            .build() };
        public static final ITexture[] ERROR_RENDERING = { TextureFactory.of(Textures.GlobalIcons.RENDERING_ERROR) };

        // spotless:off
        /**
         * by Default pages are null
         * page 0: 0-63 GT casing 1-4, 64-127 GT++
         * page 1: 0-15 GT casing 5, 22-26 GS dyson swarm, 48-57 GT casing 8, 63 EMT, 80-95 GT reinforced blocks, 96 casing 2 meta 6, 97 error casing
         * page 2: 0-15 solenoid coils
         * page 8: 0-111 TecTech, 112-127 GT casing 6
         * page 12: 0-127 GlodBlock
         * page 16: 0-15 GT glass 1, 16-31 GT casing 9, 32-47 GT glass 2, 48-63 GT casing 10, 64-79 GT casing 11, 80-95 GT casing 12
         * page 20: 0-15 CropsNH Casings
         * page 42: 0-126 glee8e, 127 KekzTech LSC base
         */
        // spotless:on
        public static ITexture[][] casingTexturePages = new ITexture[128][]; // page holder so we don't make a short
                                                                             // long array

        public static final int ERROR_TEXTURE_INDEX = (1 << 7) + 97;
        private static final Map<ITexture, Integer> reverseMap = new HashMap<>();

        static {
            casingTexturePages[0] = new ITexture[128];
            // adds some known pages, modders also can do it...
            GTUtility.addTexturePage((byte) 1);
            GTUtility.addTexturePage((byte) 2);
            GTUtility.addTexturePage((byte) 8);
            GTUtility.addTexturePage((byte) 16);
            GTUtility.addTexturePage((byte) 17);
            setCasingTextureForId(ERROR_TEXTURE_INDEX, ERROR_RENDERING[0]);
        }

        private BlockIcons() {
            throw new AssertionError("No instances allowed");
        }

        public static ITexture getCasingTextureForId(int id) {
            final ITexture[] page = casingTexturePages[(id >> 7) & 0x7f];
            if (page == null) return null;
            return page[id & 0x7f];
        }

        public static void setCasingTextureForId(int id, ITexture iTexture) {
            casingTexturePages[(byte) ((id >> 7) & 0x7f)][(byte) (id & 0x7f)] = iTexture;
            reverseMap.put(iTexture, id);
        }

        public static void setCasingTexture(byte page, byte index, ITexture iTexture) {
            casingTexturePages[page][index] = iTexture;
            reverseMap.put(iTexture, (page << 7) + index);
        }

        public static int getTextureIndex(ITexture texture) {
            Integer id = reverseMap.get(texture);
            return id == null ? ERROR_TEXTURE_INDEX : id;
        }

        public static ITexture[] createTextureWithCasing(ICasingTextureProvider mte, ForgeDirection side,
            ForgeDirection aFacing, boolean aActive, IIconContainer tex, IIconContainer texGlow,
            IIconContainer texActive, IIconContainer texActiveGlow) {
            if (side == aFacing) {
                if (aActive) return new ITexture[] { mte.getCasingTexture(), TextureFactory.builder()
                    .addIcon(texActive)
                    .extFacing()
                    .build(),
                    TextureFactory.builder()
                        .addIcon(texActiveGlow)
                        .extFacing()
                        .glow()
                        .build() };
                return new ITexture[] { mte.getCasingTexture(), TextureFactory.builder()
                    .addIcon(tex)
                    .extFacing()
                    .build(),
                    TextureFactory.builder()
                        .addIcon(texGlow)
                        .extFacing()
                        .glow()
                        .build() };
            }
            return new ITexture[] { mte.getCasingTexture() };
        }

        public static void cleanup() {}
    }

    public static final class ItemIcons {

        // spotless:off
        public static final IIconContainer
            VOID = GlobalIcons.VOID, // The Empty Texture
            RENDERING_ERROR = GlobalIcons.RENDERING_ERROR;
        // spotless:on

        public static final ITexture[] ERROR_RENDERING = { TextureFactory.of(Textures.GlobalIcons.RENDERING_ERROR) };

        private ItemIcons() {
            throw new AssertionError("No instances allowed");
        }

    }
}
