package com.venned.simpletoons.professions.leatherworker.armorBase;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class LeatherworkerArmorBaseGUI {

    public static Inventory getArmorBaseInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Armor Bases Recipes");

        inv.setItem(10, createArmorBaseItem("Helmet Armor Base", Material.LEATHER_HELMET, "Requires: 14 leather"));
        inv.setItem(12, createArmorBaseItem("Chestplate Armor Base", Material.LEATHER_CHESTPLATE, "Requires: 24 leather"));
        inv.setItem(14, createArmorBaseItem("Leggings Armor Base", Material.LEATHER_LEGGINGS, "Requires: 16 leather"));
        inv.setItem(16, createArmorBaseItem("Boots Armor Base", Material.LEATHER_BOOTS, "Requires: 10 leather"));
        return inv;
    }

    private static ItemStack createArmorBaseItem(String name, Material icon, String loreText) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        List<String> lore = Arrays.asList(ChatColor.GRAY + loreText);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void openArmorBaseGUI(Player player) {
        player.openInventory(getArmorBaseInventory());
    }
}
