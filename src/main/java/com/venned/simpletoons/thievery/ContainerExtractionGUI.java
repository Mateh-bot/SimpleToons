package com.venned.simpletoons.thievery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContainerExtractionGUI {

    public void open(Player thief, Container container) {
        int size = container.getInventory().getSize();
        Inventory gui = Bukkit.createInventory(new ContainerExtractionInventoryHolder(container), size, ChatColor.DARK_BLUE + "Container Extraction");
        gui.setContents(container.getInventory().getContents());
        thief.openInventory(gui);
    }
}
