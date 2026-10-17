package gregtech.common.MaterialSystem.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTechAPI;
import gregtech.api.util.client.ResourceUtils;

public class ItemMetaMaterial extends Item {

    private final Map<Integer, Material> materialMap = new TreeMap<>();
    private final MaterialType materialType;

    /** icon set name -> icon */
    private final Map<String, IIcon> icons = new HashMap<>();
    private IIcon defaultIcon;

    public ItemMetaMaterial(MaterialType materialType) {
        this.materialType = materialType;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setCreativeTab(GregTechAPI.TAB_GREGTECH);
        this.setUnlocalizedName("gt.meta_" + materialType.getTextureName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons.clear();
        defaultIcon = register.registerIcon(MaterialUtils.getTexturePath(materialType));

        for (Material material : materialMap.values()) {
            String iconSet = material.getIconSet();
            if (icons.containsKey(iconSet)) {
                continue;
            }
            String path = MaterialUtils.getTexturePath(materialType, iconSet);
            if (ResourceUtils.resourceExists(
                ResourceUtils.getCompleteItemTextureResourceLocation(path))) {
                icons.put(iconSet, register.registerIcon(path));
            } else {
                icons.put(iconSet, defaultIcon);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        Material material = materialMap.get(meta);
        if (material == null) {
            return defaultIcon;
        }
        return icons.getOrDefault(material.getIconSet(), defaultIcon);
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
            return "item." + material.getUnlocalizedName() + "_" + materialType.getTextureName();
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String key = getUnlocalizedName(stack) + ".name";
        if (StatCollector.canTranslate(key)) {
            return StatCollector.translateToLocal(key);
        }
        Material material = materialMap.get(stack.getItemDamage());
        return material != null ? materialType.formatLocalName(material) : super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        Material material = materialMap.get(stack.getItemDamage());
        if (material != null && material.getTooltip() != null) {
            tooltip.add(EnumChatFormatting.YELLOW + material.getTooltip());
        }
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

        if (meta < 0 || meta > MaterialUtils.MAX_MATERIAL_META) {
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
