package com.venned.simpletoons.professions.blacksmith.tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BlacksmithToolsMineralGUI {

    private static final String[] minerals = {"Bronze", "Iron", "Steel"};

    public static Inventory getMineralInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Tools - Choose Mineral");
        int[] slots = {2, 4, 6};
        for (int i = 0; i < minerals.length; i++) {
            inv.setItem(slots[i], createMineralItem(minerals[i]));
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
            default:
                icon = Material.PAPER;
                break;
        }
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + mineral);
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to view tool recipes"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openMineralGUI(Player player) {
        player.openInventory(getMineralInventory());
    }
}
