package com.venned.simpletoons.professions.leatherworker.workstation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherworkerWorkStationGUI {

    public static Inventory getLeatherworkerInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Leatherworker WorkStation");

        inv.setItem(10, createMenuItem("Saddles", Material.SADDLE, "Craft Saddles"));
        inv.setItem(11, createMenuItem("Leads", Material.LEAD, "Craft Leads"));
        inv.setItem(12, createMenuItem("Horse Armor", Material.LEATHER_HORSE_ARMOR, "Craft Leather Horse Armor"));
        inv.setItem(14, createMenuItem("Armor Bases", Material.LEATHER_CHESTPLATE, "Craft armor bases"));
        inv.setItem(15, createMenuItem("Brigandine Armor", Material.IRON_CHESTPLATE, "Craft Brigandine Armor"));
        inv.setItem(16, createMenuItem("Banners", Material.WHITE_BANNER, "Craft Custom Banners"));
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

    public static void openLeatherworkerGUI(Player player) {
        player.openInventory(getLeatherworkerInventory());
    }
}
