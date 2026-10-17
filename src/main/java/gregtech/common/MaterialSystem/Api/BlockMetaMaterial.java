package gregtech.common.MaterialSystem.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.util.client.ResourceUtils;

public class BlockMetaMaterial extends Block {

    private final Map<Integer, Material> materialMap = new TreeMap<>();

    private final MaterialType materialType;

    /** icon set name -> icon */
    private final Map<String, IIcon> icons = new HashMap<>();
    private IIcon defaultIcon;

    public BlockMetaMaterial(MaterialType materialType) {
        super(net.minecraft.block.material.Material.iron);
        this.materialType = materialType;
        setHardness(materialType == MaterialType.FRAME ? 3.0F : 5.0F);
        setResistance(10.0F);
        setStepSound(soundTypeMetal);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setBlockName("gt.meta_" + materialType.getTextureName());
        if (isFrame()) {
            setLightOpacity(0);
        }
    }

    public boolean isFrame() {
        return materialType == MaterialType.FRAME;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons.clear();
        defaultIcon = register.registerIcon(MaterialUtils.getTexturePath(materialType));

        for (Material material : materialMap.values()) {
            String iconSet = material.getIconSet();
            if (icons.containsKey(iconSet)) {
                continue;
            }
            String path = MaterialUtils.getTexturePath(materialType, iconSet);
            if (ResourceUtils.resourceExists(ResourceUtils.getCompleteBlockTextureResourceLocation(path))) {
                icons.put(iconSet, register.registerIcon(path));
            } else {
                icons.put(iconSet, defaultIcon);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        Material material = materialMap.get(meta);
        if (material == null) {
            return defaultIcon;
        }
        return icons.getOrDefault(material.getIconSet(), defaultIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        Material material = materialMap.get(meta);

        if (material != null) {
            return material.getColor();
        }

        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        Material material = materialMap.get(meta);

        if (material != null) {
            return material.getColor();
        }

        return 0xFFFFFF;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    // region Frame box behaviour

    @Override
    public boolean isOpaqueCube() {
        return !isFrame();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !isFrame();
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return !isFrame();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return isFrame() || super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return isFrame();
    }

    // endregion

    public void registerMaterial(Material material) {
        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (meta < 0 || meta > MaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalArgumentException("Material meta out of range: " + meta);
        }

        materialMap.put(meta, material);
    }

    public Material getMaterial(int meta) {
        return materialMap.get(meta);
    }

    public void addSubItems(Item item, List<ItemStack> list) {
        for (Map.Entry<Integer, Material> entry : materialMap.entrySet()) {

            Material material = entry.getValue();

            if (material.hasType(materialType)) {
                list.add(new ItemStack(item, 1, entry.getKey()));
            }
        }
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}
