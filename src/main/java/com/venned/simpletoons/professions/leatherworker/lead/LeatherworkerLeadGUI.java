package com.venned.simpletoons.professions.leatherworker.lead;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherworkerLeadGUI {

    public static Inventory getLeadInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Lead Recipe");
        inv.setItem(13, createLeadRecipeItem());
        return inv;
    }

    private static ItemStack createLeadRecipeItem() {
        ItemStack item = new ItemStack(Material.LEAD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Lead Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Requires: 2 string, 1 leather"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openLeadGUI(Player player) {
        player.openInventory(getLeadInventory());
    }
}
