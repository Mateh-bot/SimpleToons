package com.venned.simpletoons.professions.blacksmith.tools;

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

public class BlacksmithToolsRecipeGUI {

    public static Inventory getRecipeInventory(String mineral) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Tools Recipes - " + mineral);

        inv.setItem(10, createRecipeItem("Pickaxe", mineral, 3, 2));
        inv.setItem(12, createRecipeItem("Axe", mineral, 3, 2));
        inv.setItem(14, createRecipeItem("Shovel", mineral, 1, 2));
        inv.setItem(16, createRecipeItem("Hoe", mineral, 2, 2));
        if (mineral.equalsIgnoreCase("iron")) {
            inv.setItem(22, createRecipeItem("Shears", mineral, 2, 0));
        }
        return inv;
    }

    private static ItemStack createRecipeItem(String toolType, String mineral, int materialAmount, int sticks) {
        Material icon = getToolIcon(toolType, mineral);
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + toolType + " Recipe (" + mineral + ")");
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
        String req = "Requires " + materialAmount + " " + requiredMaterial;
        if (sticks > 0) {
            req += " and " + sticks + " Sticks";
        }
        lore.add(ChatColor.GRAY + req);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static Material getToolIcon(String toolType, String mineral) {
        String lowMineral = mineral.toLowerCase();
        toolType = toolType.toLowerCase();
        if (toolType.contains("pickaxe")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_PICKAXE;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_PICKAXE;
            } else {
                return Material.IRON_PICKAXE;
            }
        } else if (toolType.contains("axe")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_AXE;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_AXE;
            } else {
                return Material.IRON_AXE;
            }
        } else if (toolType.contains("shovel")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_SHOVEL;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_SHOVEL;
            } else {
                return Material.IRON_SHOVEL;
            }
        } else if (toolType.contains("hoe")) {
            if (lowMineral.equals("bronze")) {
                return Material.WOODEN_HOE;
            } else if (lowMineral.equals("steel")) {
                return Material.NETHERITE_HOE;
            } else {
                return Material.IRON_HOE;
            }
        } else if (toolType.contains("shears")) {
            return Material.SHEARS;
        }
        return Material.PAPER;
    }

    public static void openRecipeGUI(Player player, String mineral) {
        player.openInventory(getRecipeInventory(mineral));
    }
}
