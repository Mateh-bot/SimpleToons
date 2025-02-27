package com.venned.simpletoons.thievery;

import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ContainerExtractionInventoryHolder implements InventoryHolder {
    private final Container container;

    public ContainerExtractionInventoryHolder(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}