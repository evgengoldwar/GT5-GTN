package gregtech.GTMaterialSystem.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaGTMaterial extends Block {

    private final Map<Integer, GTMaterial> materialMap = new HashMap<>();

    private final GTMaterialType materialType;

    private IIcon icon;

    private final String texturePath;

    public BlockMetaGTMaterial(GTMaterialType materialType) {
        super(Material.iron);
        this.materialType = materialType;
        this.texturePath = GTMaterialUtils.getTextureBlockPath(materialType);
        setHardness(5.0F);
        setResistance(10.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icon = register.registerIcon(texturePath);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        GTMaterial material = materialMap.get(meta);

        if (material != null) {
            return material.getColor();
        }

        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        GTMaterial material = materialMap.get(meta);

        if (material != null) {
            return material.getColor();
        }

        return 0xFFFFFF;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    public void registerMaterial(GTMaterial material) {
        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (meta < 0 || meta > GTMaterialUtils.MAX_MATERIAL_META) {
            throw new IllegalArgumentException("Material meta out of range: " + meta);
        }

        materialMap.put(meta, material);
    }

    public GTMaterial getMaterial(int meta) {
        return materialMap.get(meta);
    }

    public void addSubItems(Item item, List<ItemStack> list) {
        for (Map.Entry<Integer, GTMaterial> entry : materialMap.entrySet()) {

            GTMaterial material = entry.getValue();

            if (material.hasType(materialType)) {
                list.add(new ItemStack(item, 1, entry.getKey()));
            }
        }
    }

    public GTMaterialType getMaterialType() {
        return materialType;
    }
}
