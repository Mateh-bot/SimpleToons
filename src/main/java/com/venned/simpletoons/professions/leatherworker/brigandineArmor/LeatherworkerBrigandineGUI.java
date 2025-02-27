package com.venned.simpletoons.professions.leatherworker.brigandineArmor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class LeatherworkerBrigandineGUI {

    public static Inventory getBrigandineInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Brigandine Armor Recipes");

        inv.setItem(10, createBrigandineItem("Brigandine Helmet", Material.IRON_HELMET, "Requires: 5 wool, 20 leather, 2 steel"));
        inv.setItem(12, createBrigandineItem("Brigandine Chestplate", Material.IRON_CHESTPLATE, "Requires: 8 wool, 32 leather, 4 steel"));
        inv.setItem(14, createBrigandineItem("Brigandine Leggings", Material.IRON_LEGGINGS, "Requires: 7 wool, 24 leather, 3 steel"));
        inv.setItem(16, createBrigandineItem("Brigandine Boots", Material.IRON_BOOTS, "Requires: 4 wool, 29 leather, 2 steel"));
        return inv;
    }

    private static ItemStack createBrigandineItem(String name, Material icon, String loreText) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        List<String> lore = Arrays.asList(ChatColor.GRAY + loreText);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void openBrigandineGUI(Player player) {
        player.openInventory(getBrigandineInventory());
    }
}
