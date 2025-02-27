package com.venned.simpletoons.professions.blacksmith.ingots;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BlacksmithIngotSmeltingGUI {

    public static Inventory getIngotInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Ingot Smelting Recipes");
        inv.setItem(9, createIngotItem("Iron Ingot", Material.IRON_INGOT, "2 iron ore → 1 ingot"));
        inv.setItem(10, createIngotItem("Copper Ingot", Material.COPPER_INGOT, "5 copper ore → 1 ingot"));
        inv.setItem(11, createIngotItem("Tin Ingot", Material.BRICK, "2 tin ore → 1 ingot")); // placeholder para Tin
        inv.setItem(12, createIngotItem("Lead Ingot", Material.PAPER, "3 lead ore → 1 ingot")); // placeholder para Lead

        inv.setItem(4, createIngotItem("Low Grade Gold Ingot", Material.GOLD_ORE, "10 low grade gold ore → 1 ingot"));
        inv.setItem(13, createIngotItem("Medium Grade Gold Ingot", Material.GOLD_ORE, "5 medium grade gold ore → 1 ingot"));
        inv.setItem(22, createIngotItem("High Grade Gold Ingot", Material.GOLD_ORE, "1 high grade gold ore → 1 ingot"));

        inv.setItem(14, createIngotItem("Adamantium Ingot", Material.EMERALD, "5 adamantium ore → 1 ingot"));
        inv.setItem(15, createIngotItem("Steel Ingot", Material.NETHERITE_INGOT, "3 iron ore + 2 coal → 1 ingot"));
        inv.setItem(16, createIngotItem("Bronze Ingot", Material.BRICK, "3 copper ore + 1 tin ore → 1 ingot"));
        inv.setItem(17, createIngotItem("Fortium Ingot", Material.GOLD_INGOT, "5 steel ingots + 2 adamantium ore + 1 bronze ingot → 1 ingot"));
        return inv;
    }

    private static ItemStack createIngotItem(String name, Material icon, String recipe) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name + " Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + recipe));
        item.setItemMeta(meta);
        return item;
    }

    public static void openIngotGUI(Player player) {
        player.openInventory(getIngotInventory());
    }
}
