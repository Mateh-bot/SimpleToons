package com.venned.simpletoons.professions.woodcutter.workstation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class WoodcutterWorkStationGUI {

    public static Inventory getWoodcutterInventory() {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GRAY + "Woodcutter WorkStation");

        inv.setItem(10, createMenuItem("Long Hilt", Material.STICK, "Requires: 5 Sticks, 32 Leather"));
        inv.setItem(12, createMenuItem("Short Hilt", Material.STICK, "Requires: 2 Sticks, 16 Leather"));
        inv.setItem(14, createMenuItem("Arrow", Material.ARROW, "Requires: 2 Iron, 1 Stick, 1 Feather (yields 4 Arrows)"));
        inv.setItem(16, createMenuItem("Longbow", Material.BOW, "Requires: 5 Sticks, 12 Steel, 3 String"));
        inv.setItem(28, createMenuItem("Shortbow", Material.BOW, "Requires: 3 Sticks, 4 Steel, 3 String"));
        inv.setItem(30, createMenuItem("Crossbow", Material.CROSSBOW, "Requires: 10 Sticks, 16 Steel, 3 String"));
        inv.setItem(32, createMenuItem("Boomstick", Material.BOW, "Requires: 2 Planks, 5 Sticks, 24 Steel, 1 Lead"));
        inv.setItem(34, createMenuItem("Shield", Material.SHIELD, "Requires: 10 Planks, 4 Steel, 16 Leather"));
        return inv;
    }

    private static ItemStack createMenuItem(String name, Material icon, String description) {
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name + " Recipe");
        List<String> lore = Arrays.asList(ChatColor.GRAY + description);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void openWoodcutterGUI(Player player) {
        player.openInventory(getWoodcutterInventory());
    }
}
