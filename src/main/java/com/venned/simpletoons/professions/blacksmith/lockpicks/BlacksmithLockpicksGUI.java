package com.venned.simpletoons.professions.blacksmith.lockpicks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BlacksmithLockpicksGUI {

    public static Inventory getLockpicksInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Lockpicks Recipe");
        inv.setItem(4, createLockpickItem());
        return inv;
    }

    private static ItemStack createLockpickItem() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Lockpick Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Requires: 5 Steel, 5 Leather"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openLockpicksGUI(Player player) {
        player.openInventory(getLockpicksInventory());
    }
}
