package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

/**
 * Represents a furnace.
 */
public interface Furnace extends BlockState, ContainerBlock {

    /**
     * Get burn time.
     *
     * @return Burn time
     */
    public short getBurnTime();

    /**
     * Set burn time.
     *
     * @param burnTime Burn time
     */
    public void setBurnTime(short burnTime);

    /**
     * Get cook time.
     *
     * @return Cook time
     */
    public short getCookTime();

    /**
     * Set cook time.
     *
     * @param cookTime Cook time
     */
    public void setCookTime(short cookTime);

    /**
     * Get cook speed multiplier.
     *
     * @return cook speed multiplier
     */
    public double getCookSpeedMultiplier();

    /**
     *  Set cook speed multiplier.
     *
     * @param cookSpeedMultiplier cook speed multiplier
     */
    public void setCookSpeedMultiplier(double cookSpeedMultiplier);

    public FurnaceInventory getInventory();
}
