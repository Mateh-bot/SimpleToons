package com.venned.simpletoons.professions.leatherworker.banners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherworkerBannerGUI {

    public static Inventory getBannerInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Banner Recipe");
        inv.setItem(13, createBannerRecipeItem());
        return inv;
    }

    private static ItemStack createBannerRecipeItem() {
        ItemStack item = new ItemStack(Material.WHITE_BANNER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Banner Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Requires: 6 wool, 1 stick, 1 dye"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openBannerGUI(Player player) {
        player.openInventory(getBannerInventory());
    }
}
