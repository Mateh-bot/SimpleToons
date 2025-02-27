package com.venned.simpletoons.professions.blacksmith.weapons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BlacksmithWeaponsRecipeGUI {

    public static Inventory getWeaponsRecipeInventory(String mineral) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Weapons Recipes - " + mineral);
        int slot = 10;
        inv.setItem(slot++, createWeaponsRecipeItem("Long Sword", mineral, 12));
        inv.setItem(slot++, createWeaponsRecipeItem("Short Sword", mineral, 8));
        inv.setItem(slot++, createWeaponsRecipeItem("Spear", mineral, 12));
        inv.setItem(slot++, createWeaponsRecipeItem("Pike", mineral, 24));
        inv.setItem(slot++, createWeaponsRecipeItem("Lance", mineral, 24));
        inv.setItem(slot++, createWeaponsRecipeItem("Mace", mineral, 12));
        inv.setItem(slot++, createWeaponsRecipeItem("Javelin", mineral, 12));
        return inv;
    }

    private static ItemStack createWeaponsRecipeItem(String weaponName, String mineral, int materialAmount) {
        Material icon = getWeaponIcon(weaponName, mineral);
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + weaponName + " Recipe (" + mineral + ")");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        String requiredMaterial;
        if (mineral.equalsIgnoreCase("bronze")) {
            requiredMaterial = "Bronze";
        } else if (mineral.equalsIgnoreCase("steel")) {
            requiredMaterial = "Steel";
        } else {
            requiredMaterial = "Iron";
        }
        lore.add(ChatColor.GRAY + "Requires " + materialAmount + " " + requiredMaterial);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static Material getWeaponIcon(String weaponName, String mineral) {
        String lowMineral = mineral.toLowerCase();
        weaponName = weaponName.toLowerCase();
        if (weaponName.contains("sword")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_SWORD;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_SWORD;
            } else {
                return Material.IRON_SWORD;
            }
        } else if (weaponName.contains("spear")) {
            return Material.TRIDENT;
        } else if (weaponName.contains("pike") || weaponName.contains("lance")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_AXE;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_AXE;
            } else {
                return Material.IRON_AXE;
            }
        } else if (weaponName.contains("mace")) {
            return Material.MACE;
        } else if (weaponName.contains("javelin")) {
            return Material.STICK;
        }
        return Material.PAPER;
    }

    public static void openRecipeGUI(Player player, String mineral) {
        player.openInventory(getWeaponsRecipeInventory(mineral));
    }
}
