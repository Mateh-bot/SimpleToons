package com.venned.simpletoons.professions.blacksmith.workstation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BlacksmithWorkStationGUI {

    public static Inventory getBlacksmithInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Blacksmith WorkStation");

        inv.setItem(9, createMenuItem("Metal Weapons", Material.IRON_SWORD, "View weapon recipes"));
        inv.setItem(11, createMenuItem("Metal Armor", Material.IRON_CHESTPLATE, "View armor recipes"));
        inv.setItem(13, createMenuItem("Metal Tools", Material.IRON_PICKAXE, "View tool recipes"));
        inv.setItem(15, createMenuItem("Lockpicks", Material.IRON_NUGGET, "Craft lockpicks"));
        inv.setItem(17, createMenuItem("Metal Ingots", Material.FURNACE, "View ingot smelting recipes"));
        return inv;
    }

    private static ItemStack createMenuItem(String name, Material material, String description) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        meta.setLore(Arrays.asList(ChatColor.GRAY + description));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static void openBlacksmithGUI(Player player) {
        player.openInventory(getBlacksmithInventory());
    }
}
