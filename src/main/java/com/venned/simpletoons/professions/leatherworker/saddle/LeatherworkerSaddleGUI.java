package com.venned.simpletoons.professions.leatherworker.saddle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherworkerSaddleGUI {

    public static Inventory getSaddleInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Saddle Recipe");
        inv.setItem(13, createRecipeItem());
        return inv;
    }

    private static ItemStack createRecipeItem() {
        ItemStack item = new ItemStack(Material.SADDLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Saddle Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Requires: 5 steel, 20 leather"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openSaddleGUI(Player player) {
        player.openInventory(getSaddleInventory());
    }
}
