package com.venned.simpletoons.professions.leatherworker.horseArmor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LeatherworkerHorseArmorGUI {

    public static Inventory getHorseArmorInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Horse Armor Recipe");
        inv.setItem(13, createHorseArmorRecipeItem());
        return inv;
    }

    private static ItemStack createHorseArmorRecipeItem() {
        ItemStack item = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Leather Horse Armor Recipe");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Requires: 5 steel, 60 leather"));
        item.setItemMeta(meta);
        return item;
    }

    public static void openHorseArmorGUI(Player player) {
        player.openInventory(getHorseArmorInventory());
    }
}
