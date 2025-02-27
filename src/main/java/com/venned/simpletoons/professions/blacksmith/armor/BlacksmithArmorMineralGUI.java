package com.venned.simpletoons.professions.blacksmith.armor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BlacksmithArmorMineralGUI {
    private static final String[] minerals = {"Bronze", "Iron", "Steel", "Adamantium", "Fortium", "Dragonscale"};

    public static Inventory getMineralInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Armor - Choose Mineral");
        for (int i = 0; i < minerals.length && i < inv.getSize(); i++) {
            inv.setItem(i, createMineralItem(minerals[i]));
        }
        return inv;
    }

    private static ItemStack createMineralItem(String mineral) {
        Material icon;
        switch (mineral.toLowerCase()) {
            case "bronze":
                icon = Material.BRICK;
                break;
            case "iron":
                icon = Material.IRON_INGOT;
                break;
            case "steel":
                icon = Material.NETHERITE_INGOT;
                break;
            case "adamantium":
                icon = Material.EMERALD; // placeholder
                break;
            case "fortium":
                icon = Material.GOLD_INGOT; // placeholder
                break;
            case "dragonscale":
                icon = Material.DRAGON_BREATH; // placeholder
                break;
            default:
                icon = Material.PAPER;
                break;
        }
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + mineral);
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to view armor recipes"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openMineralGUI(Player player) {
        player.openInventory(getMineralInventory());
    }
}
