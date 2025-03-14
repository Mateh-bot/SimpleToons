package com.venned.simpletoons.thievery;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PickpocketGUI {

    public static Inventory createPickpocketInventory(Player target) {
        Inventory inv = Bukkit.createInventory(new PickpocketInventoryHolder(target.getUniqueId()), 36,
                ChatColor.DARK_RED + "Pickpocket " + target.getName());
        ItemStack[] targetContents = target.getInventory().getStorageContents();
        inv.setContents(targetContents);
        return inv;
    }

    public static void openPickpocketGUI(Player thief, Player target) {
        Inventory gui = createPickpocketInventory(target);
        thief.openInventory(gui);
    }
}
