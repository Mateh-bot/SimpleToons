package com.venned.simpletoons.thievery;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class PickpocketInventoryHolder implements InventoryHolder {
    private final UUID targetUUID;

    public PickpocketInventoryHolder(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}