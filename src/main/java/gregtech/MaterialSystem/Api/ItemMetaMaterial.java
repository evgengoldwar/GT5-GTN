package gregtech.MaterialSystem.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetaMaterial extends Item {

    private static final int MAX_META = 32_767;

    private final Map<Integer, Material> materialMap = new HashMap<>();
    private final MaterialType materialType;

    private IIcon icon;
    private final String texturePath;

    public ItemMetaMaterial(MaterialType materialType) {
        this.materialType = materialType;

        this.texturePath = "gregtech:MaterialSystems/Items/" + materialType.getTextureName() + ".png";
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(texturePath);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        Material material = materialMap.get(stack.getItemDamage());

        if (material != null) {
            return material.getColor();
        }

        return 0xFFFFFF;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        Material material = materialMap.get(stack.getItemDamage());

        if (material != null) {
            return "item." + material.getName()
                .toLowerCase()
                + "."
                + materialType.name()
                    .toLowerCase();
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {

        for (Map.Entry<Integer, Material> entry : materialMap.entrySet()) {

            Material material = entry.getValue();

            if (material.hasType(materialType)) {
                list.add(new ItemStack(this, 1, entry.getKey()));
            }
        }
    }

    public void registerMaterial(Material material) {
        if (material == null) {
            return;
        }

        int meta = material.getMeta();

        if (meta < 0 || meta > MAX_META) {
            throw new IllegalArgumentException("Material meta out of range: " + meta);
        }

        materialMap.put(meta, material);
    }

    public Material getMaterial(int meta) {
        return materialMap.get(meta);
    }

    public boolean hasMaterial(int meta) {
        return materialMap.containsKey(meta);
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}
