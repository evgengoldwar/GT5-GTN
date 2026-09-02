package gregtech.common.items;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.Mods.GregTech;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.oredict.OreDictionary;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.factory.GuiFactories;
import com.cleanroommc.modularui.factory.PlayerInventoryGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.GTLoggers;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.INetworkUpdatableItem;
import gregtech.api.items.GTGenericItem;
import gregtech.api.modularui2.GTGuiThemes;
import gregtech.api.modularui2.GTModularScreen;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GTConfig;
import gregtech.common.gui.modularui.item.IntegratedCircuitGui;
import gregtech.crossmod.backhand.Backhand;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.ItemToolbox;

public class ItemIntegratedCircuit extends GTGenericItem
    implements INetworkUpdatableItem, IGuiHolder<PlayerInventoryGuiData> {

    public static final int MAX_CIRCUIT_NUMBER = 24;
    public static final List<ItemStack> NON_ZERO_VARIANTS = new ArrayList<>(MAX_CIRCUIT_NUMBER);

    private static final String aTextEmptyRow = "   ";
    public static final List<ItemStack> ALL_VARIANTS = new ArrayList<>(MAX_CIRCUIT_NUMBER + 1);
    protected final IIcon[] mIconDamage = new IIcon[25];

    public ItemIntegratedCircuit() {
        super("integrated_circuit", "Programmed Circuit", "");
        setHasSubtypes(true);
        setMaxDamage(0);

        ItemList.Circuit_Integrated.set(this);

        ALL_VARIANTS.add(new ItemStack(this, 0, 0));
        for (int i = 1; i <= MAX_CIRCUIT_NUMBER; i++) {
            ItemStack aStack = new ItemStack(this, 0, i);
            NON_ZERO_VARIANTS.add(aStack);
            ALL_VARIANTS.add(aStack);
        }
    }

    private static String getModeString(int aMetaData) {
        return switch ((byte) (aMetaData >>> 8)) {
            case 0 -> "==";
            case 1 -> "<=";
            case 2 -> ">=";
            case 3 -> "<";
            case 4 -> ">";
            default -> "";
        };
    }

    private static String getConfigurationString(int aMetaData) {
        return getModeString(aMetaData) + " " + (byte) (aMetaData & 0xFF);
    }

    @Override
    public void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        aList.add(
            StatCollector.translateToLocalFormatted(
                "GT5U.item.programmed_circuit.tooltip.0",
                getConfigurationString(getDamage(aStack))));
        aList.add(StatCollector.translateToLocal("GT5U.item.programmed_circuit.tooltip.1"));
        aList.add(StatCollector.translateToLocal("GT5U.item.programmed_circuit.tooltip.2"));
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item aItem, CreativeTabs aCreativeTab, List<ItemStack> aList) {
        aList.add(new ItemStack(this, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aIconRegister) {
        super.registerIcons(aIconRegister);
        for (int i = 0; i < mIconDamage.length; i++) {
            mIconDamage[i] = aIconRegister
                .registerIcon(GregTech.getResourcePath(GTConfig.troll ? "troll" : getUnlocalizedName() + "/" + i));
        }
        if (GregTechAPI.sPostloadFinished) {
            GT_FML_LOGGER.debug("GTMod: Starting Item Icon Load Phase");
            GT_FML_LOGGER.info("GTMod: Starting Item Icon Load Phase");
            GregTechAPI.sItemIcons = aIconRegister;
            for (Runnable tRunnable : GregTechAPI.sGTItemIconload) {
                try {
                    tRunnable.run();
                } catch (Exception e) {
                    GTLoggers.GT_FML_LOGGER.error("Error registering icons", e);
                }
            }
            GT_FML_LOGGER.debug("GTMod: Finished Item Icon Load Phase");
            GT_FML_LOGGER.info("GTMod: Finished Item Icon Load Phase");
        }
    }

    @Override
    public IIcon getIconFromDamage(int aMetaData) {
        byte circuitMode = ((byte) (aMetaData & 0xFF)); // Mask out the MSB Comparison Mode Bits. See: getModeString
        return mIconDamage[circuitMode < mIconDamage.length ? circuitMode : 0];
    }

    @Override
    public boolean receive(ItemStack stack, EntityPlayerMP player, NBTTagCompound tag) {
        int meta = tag.hasKey("meta", Constants.NBT.TAG_BYTE) ? tag.getByte("meta") : -1;
        if (meta < 0 || meta > 24) return true;

        if (!player.capabilities.isCreativeMode) {
            try {
                findConfiguratorInInv(player, true); // damage the tool
            } catch (IllegalStateException e) {
                player.addChatComponentMessage(
                    new ChatComponentText("Error while trying to configure circuit: " + e.getMessage()));
                return true;
            }
        }
        stack.setItemDamage(meta);

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!(player instanceof FakePlayer) && !world.isRemote) {
            // check if any screwdriver
            if (!player.capabilities.isCreativeMode && findConfiguratorInInv(player, false) == null) {
                int count;
                try {
                    count = Integer
                        .parseInt(StatCollector.translateToLocal("GT5U.item.programmed_circuit.no_screwdriver.count"));
                } catch (NumberFormatException e) {
                    player.addChatComponentMessage(
                        new ChatComponentText(
                            "Error in translation GT5U.item.programmed_circuit.no_screwdriver.count: "
                                + e.getMessage()));
                    count = 1;
                }
                player.addChatComponentMessage(
                    new ChatComponentTranslation(
                        "GT5U.item.programmed_circuit.no_screwdriver." + XSTR.XSTR_INSTANCE.nextInt(count)));
                return super.onItemRightClick(stack, world, player);
            }

            // open gui
            if (stack == Backhand.getOffhandItem(player)) GuiFactories.playerInventory()
                .openFromPlayerInventory(player, Backhand.getOffhandSlot(player));
            else GuiFactories.playerInventory()
                .openFromMainHand(player);
        }

        return super.onItemRightClick(stack, world, player);
    }

    private static final int screwdriverOreId = OreDictionary.getOreID("craftingToolScrewdriver");

    /**
     * Test a player's inventory for a circuit configuring item.
     *
     * @param player   The player whose inventory will be tested.
     * @param doDamage Whether a found item should be damaged.
     *
     * @return A display stack representing the item that was found. Do not modify this item!
     */
    public static ItemStack findConfiguratorInInv(EntityPlayer player, boolean doDamage) {
        ItemStack[] mainInventory = player.inventory.mainInventory;
        for (int i = 0; i < mainInventory.length; i++) {
            ItemStack potentialStack = mainInventory[i];
            if (potentialStack == null || potentialStack.getItem() == null || potentialStack.stackSize <= 0) continue;

            // Circuit Configurator

            // Toolbox with Screwdriver inside
            if (potentialStack.getItem() instanceof ItemToolbox toolbox) {
                IHasGui toolboxInventory = toolbox.getInventory(player, potentialStack);
                if (!IC2.platform.isSimulating()) {
                    populateToolboxInventory(toolboxInventory, potentialStack);
                }
                for (int j = 0; j < toolboxInventory.getSizeInventory(); j++) {
                    ItemStack toolboxStack = toolboxInventory.getStackInSlot(j);
                    if (toolboxStack == null || toolboxStack.getItem() == null || toolboxStack.stackSize <= 0) continue;

                    for (int id : OreDictionary.getOreIDs(toolboxStack)) {
                        if (id == screwdriverOreId) {
                            if (doDamage) {
                                toolboxStack = toolboxStack.getItem()
                                    .getContainerItem(toolboxStack);
                                if (toolboxStack != null && toolboxStack.stackSize <= 0) {
                                    toolboxInventory.setInventorySlotContents(j, null);
                                } else {
                                    toolboxInventory.setInventorySlotContents(j, toolboxStack);
                                }
                            }
                            return potentialStack; // return the toolbox for display
                        }
                    }
                }
            }

            // Screwdriver
            for (int id : OreDictionary.getOreIDs(potentialStack)) {
                if (id == screwdriverOreId) {
                    if (doDamage) {
                        potentialStack = potentialStack.getItem()
                            .getContainerItem(potentialStack);
                        if (potentialStack != null && potentialStack.stackSize <= 0) {
                            mainInventory[i] = null;
                        } else {
                            mainInventory[i] = potentialStack;
                        }
                    }
                    return potentialStack;
                }
            }
        }
        return null;
    }

    // Because for some reason, the toolbox inventory refuses to read the itemstack nbt
    // on the client (according to IC2 this is in fact intentional).
    private static void populateToolboxInventory(IHasGui toolboxInventory, ItemStack toolbox) {
        NBTTagCompound nbt = toolbox.getTagCompound();
        if (nbt == null) return;
        NBTTagList stacks = nbt.getTagList("Items", 10);
        for (int i = 0; i < stacks.tagCount(); i++) {
            NBTTagCompound slotNbt = stacks.getCompoundTagAt(i);
            int slot = slotNbt.getByte("Slot");
            if (slot >= 0 && slot < toolboxInventory.getSizeInventory()) {
                toolboxInventory.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(slotNbt));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModularScreen createScreen(PlayerInventoryGuiData data, ModularPanel mainPanel) {
        return new GTModularScreen(mainPanel, GTGuiThemes.STANDARD);
    }

    @Override
    public ModularPanel buildUI(PlayerInventoryGuiData data, PanelSyncManager syncManager, UISettings settings) {
        EntityPlayer player = data.getPlayer();

        return new IntegratedCircuitGui(
            data,
            player.capabilities.isCreativeMode ? null : findConfiguratorInInv(data.getPlayer(), false)).build();
    }
}
