package gregtech.api.interfaces;

import gregtech.api.interfaces.metatileentity.IMTE;
import gregtech.common.blocks.ItemMachines;

/**
 * Implement this interface on your {@link IMTE} so that energy
 * info doesn't get automatically added in the tooltip by {@link ItemMachines#addInformation}
 */
public interface IHideTooltipEnergyInfo {
}
