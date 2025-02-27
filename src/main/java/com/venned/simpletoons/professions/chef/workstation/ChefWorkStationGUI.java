package com.venned.simpletoons.professions.chef.workstation;

import com.venned.simpletoons.professions.chef.ChefRecipe;
import com.venned.simpletoons.professions.chef.ChefRecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChefWorkStationGUI {

    private static ChefRecipeManager recipeManager = new ChefRecipeManager();

    public static Inventory getChefInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Chef WorkStation");
        int slot = 0;
        for (ChefRecipe recipe : recipeManager.getRecipes()) {
            if (slot >= inv.getSize()) break;
            inv.setItem(slot, createRecipeItem(recipe));
            slot++;
        }
        return inv;
    }

    private static ItemStack createRecipeItem(ChefRecipe recipe) {
        Material icon = recipe.getIcon();
        if (icon == null) icon = Material.REDSTONE;
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + recipe.getName() + " Recipe");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Ingredients: " + recipe.getIngredients());
        lore.add(ChatColor.GRAY + "Cooking Time: " + recipe.getCookTime() + " min");
        lore.add(ChatColor.GRAY + "Decay Time: " + recipe.getDecayTime());
        lore.add(ChatColor.GRAY + "Base Saturation: " + recipe.getBaseSaturation());
        lore.add(ChatColor.GRAY + "Extra: Each star above 1 adds 0.5 saturation.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static void openChefGUI(Player player) {
        player.openInventory(getChefInventory());
    }
}
