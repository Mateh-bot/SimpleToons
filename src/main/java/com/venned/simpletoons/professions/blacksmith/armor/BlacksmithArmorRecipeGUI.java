package com.venned.simpletoons.professions.blacksmith.armor;

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

public class BlacksmithArmorRecipeGUI {

    public static Inventory getArmorRecipeInventory(String mineral) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Armor Recipes - " + mineral);
        inv.setItem(10, createArmorRecipeItem("Helmet", mineral, getArmorRecipe("Helmet", mineral)));
        inv.setItem(12, createArmorRecipeItem("Chestplate", mineral, getArmorRecipe("Chestplate", mineral)));
        inv.setItem(14, createArmorRecipeItem("Leggings", mineral, getArmorRecipe("Leggings", mineral)));
        inv.setItem(16, createArmorRecipeItem("Boots", mineral, getArmorRecipe("Boots", mineral)));
        return inv;
    }

    private static ItemStack createArmorRecipeItem(String armorType, String mineral, String recipeLore) {
        Material icon = getArmorIcon(armorType, mineral);
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + armorType + " Recipe (" + mineral + ")");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + recipeLore);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static String getArmorRecipe(String armorType, String mineral) {
        String m = mineral.toLowerCase().trim();
        armorType = armorType.toLowerCase();
        switch (m) {
            case "iron":
                switch (armorType) {
                    case "helmet": return "10 iron, Helmet Armor Base";
                    case "chestplate": return "20 iron, Chestplate Armor Base";
                    case "leggings": return "15 iron, 8 Leggings Armor Base";
                    case "boots": return "10 iron, Boots Armor Base";
                }
                break;
            case "bronze":
                switch (armorType) {
                    case "helmet": return "10 bronze, Helmet Armor Base";
                    case "chestplate": return "20 bronze, Chestplate Armor Base";
                    case "leggings": return "15 bronze, Leggings Armor Base";
                    case "boots": return "10 bronze, Boots Armor Base";
                }
                break;
            case "steel":
                switch (armorType) {
                    case "helmet": return "10 steel, Helmet Armor Base";
                    case "chestplate": return "20 steel, Chestplate Armor Base";
                    case "leggings": return "15 steel, Leggings Armor Base";
                    case "boots": return "10 steel, Boots Armor Base";
                }
                break;
            case "adamantium":
                switch (armorType) {
                    case "helmet": return "10 adamantium, Helmet Armor Base";
                    case "chestplate": return "20 adamantium, Chestplate Armor Base";
                    case "leggings": return "15 adamantium, Leggings Armor Base";
                    case "boots": return "10 adamantium, Boots Armor Base";
                }
                break;
            case "fortium":
                switch (armorType) {
                    case "helmet": return "10 fortium, Helmet Armor Base";
                    case "chestplate": return "20 fortium, Chestplate Armor Base";
                    case "leggings": return "15 steel, Leggings Armor Base";
                    case "boots": return "10 fortium, Boots Armor Base";
                }
                break;
            case "dragonscale":
                switch (armorType) {
                    case "helmet": return "5 dragonscale, 10 steel, Helmet Armor Base";
                    case "chestplate": return "10 dragonscale, 15 steel, Chestplate Armor Base";
                    case "leggings": return "8 dragonscale, 12 steel, Leggings Armor Base";
                    case "boots": return "5 dragonscale, 10 steel, Boots Armor Base";
                }
                break;
        }
        return "Recipe not defined";
    }

    private static Material getArmorIcon(String armorType, String mineral) {
        String m = mineral.toLowerCase().trim();
        armorType = armorType.toLowerCase();
        switch (m) {
            case "bronze":
                switch (armorType) {
                    case "helmet": return Material.LEATHER_HELMET;
                    case "chestplate": return Material.LEATHER_CHESTPLATE;
                    case "leggings": return Material.LEATHER_LEGGINGS;
                    case "boots": return Material.LEATHER_BOOTS;
                }
                break;
            case "iron":
                switch (armorType) {
                    case "helmet": return Material.IRON_HELMET;
                    case "chestplate": return Material.IRON_CHESTPLATE;
                    case "leggings": return Material.IRON_LEGGINGS;
                    case "boots": return Material.IRON_BOOTS;
                }
                break;
            case "steel":
                switch (armorType) {
                    case "helmet": return Material.NETHERITE_HELMET;
                    case "chestplate": return Material.NETHERITE_CHESTPLATE;
                    case "leggings": return Material.NETHERITE_LEGGINGS;
                    case "boots": return Material.NETHERITE_BOOTS;
                }
                break;
            case "adamantium":
                switch (armorType) {
                    case "helmet": return Material.GOLDEN_HELMET;
                    case "chestplate": return Material.GOLDEN_CHESTPLATE;
                    case "leggings": return Material.GOLDEN_LEGGINGS;
                    case "boots": return Material.GOLDEN_BOOTS;
                }
                break;
            case "fortium":
                switch (armorType) {
                    case "helmet": return Material.CHAINMAIL_HELMET;
                    case "chestplate": return Material.CHAINMAIL_CHESTPLATE;
                    case "leggings": return Material.CHAINMAIL_LEGGINGS;
                    case "boots": return Material.CHAINMAIL_BOOTS;
                }
                break;
            case "dragonscale":
                switch (armorType) {
                    case "helmet": return Material.DIAMOND_HELMET;
                    case "chestplate": return Material.DIAMOND_CHESTPLATE;
                    case "leggings": return Material.DIAMOND_LEGGINGS;
                    case "boots": return Material.DIAMOND_BOOTS;
                }
                break;
            default:
                return Material.PAPER;
        }
        return Material.PAPER;
    }

    public static void openArmorRecipeGUI(Player player, String mineral) {
        player.openInventory(getArmorRecipeInventory(mineral));
    }
}
