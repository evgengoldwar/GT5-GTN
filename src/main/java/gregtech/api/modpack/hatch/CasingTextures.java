package gregtech.api.modpack.hatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;

import gregtech.api.interfaces.ITexture;
import gregtech.api.modpack.util.ResourceIcon;
import gregtech.api.render.TextureFactory;

/**
 * Numbered casing textures. A multiblock tells its hatches the id of its casing so they look like the casing; the id
 * is sent to the client, so ids must be registered in the same order on both sides - always during pre-init
 * (multiblock builders do it for you).
 */
public final class CasingTextures {

    private static final List<ITexture> TEXTURES = new ArrayList<>();
    private static final Map<String, Integer> IDS = new HashMap<>();

    static {
        TEXTURES.add(null); // id 0 = no casing, hatch uses its own hull
    }

    private CasingTextures() {}

    /**
     * Registers (or returns the existing id of) a texture under a unique key.
     */
    public static int register(String key, ITexture texture) {
        Integer id = IDS.get(key);
        if (id != null) {
            return id;
        }
        TEXTURES.add(texture);
        id = TEXTURES.size() - 1;
        IDS.put(key, id);
        return id;
    }

    /** Casing texture taken from a block (icon of its side 2 for that meta). */
    public static int register(Block block, int meta) {
        return register(Block.blockRegistry.getNameForObject(block) + "@" + meta, TextureFactory.of(block, meta));
    }

    /** Casing texture from a block texture path, e.g. {@code "casings/steel_solid"}. */
    public static int register(String texturePath) {
        return register("path:" + texturePath, TextureFactory.of(ResourceIcon.block(texturePath)));
    }

    /** @return the texture or null for 0 / unknown ids */
    public static ITexture get(int id) {
        return id > 0 && id < TEXTURES.size() ? TEXTURES.get(id) : null;
    }
}
