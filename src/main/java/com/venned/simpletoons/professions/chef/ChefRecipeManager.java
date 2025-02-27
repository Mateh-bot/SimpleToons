package com.venned.simpletoons.professions.chef;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ChefRecipeManager {

    private List<ChefRecipe> recipes;

    public ChefRecipeManager() {
        recipes = new ArrayList<>();
        registerRecipes();
    }

    private void registerRecipes() {
        recipes.add(new ChefRecipe("Flour", "3 wheat", 5, "7 days", 0, Material.PAPER));
        recipes.add(new ChefRecipe("Dough", "5 wheat, 1 water bottle, 1 egg", 5, "1 hour", 0.5, Material.SNOWBALL));
        recipes.add(new ChefRecipe("Butter", "1 bucket of milk", 180, "48 hours", 0.5, Material.YELLOW_DYE));
        recipes.add(new ChefRecipe("Bread", "1 dough", 30, "2 hours", 4, Material.BREAD));
        recipes.add(new ChefRecipe("Steak", "1 raw beef", 10, "1 hour", 8, Material.COOKED_BEEF));
        recipes.add(new ChefRecipe("Porkchop", "1 raw pork", 10, "1 hour", 8, Material.COOKED_PORKCHOP));
        recipes.add(new ChefRecipe("Cooked Chicken", "1 raw chicken", 10, "1 hour", 7, Material.COOKED_CHICKEN));
        recipes.add(new ChefRecipe("Cooked Mutton", "1 raw mutton", 10, "1 hour", 8, Material.COOKED_MUTTON));
        recipes.add(new ChefRecipe("Cooked Rabbit", "1 raw rabbit", 10, "1 hour", 7, Material.COOKED_RABBIT));
        recipes.add(new ChefRecipe("Roast Beef", "1 raw beef", 60, "1 hour", 12, Material.COOKED_BEEF));
        recipes.add(new ChefRecipe("Cooked Carrot", "1 carrot", 30, "1 hour", 5, Material.GOLDEN_CARROT));
        recipes.add(new ChefRecipe("Porridge", "2 flour, 12 wheat", 30, "2 hours", 8, Material.RABBIT_STEW));
        recipes.add(new ChefRecipe("Gruel", "2 flour, 12 wheat", 30, "4 hours", 6, Material.MILK_BUCKET));
        recipes.add(new ChefRecipe("Frosting", "1 milk bucket, 2 sugar", 60, "24 hours", 0.5, Material.POWDER_SNOW_BUCKET));
        recipes.add(new ChefRecipe("Cake", "3 egg, 6 flour, 2 buckets of milk, 5 sugar", 180, "24 hours", 0.4, Material.CAKE));
        recipes.add(new ChefRecipe("Apple Pie", "8 apples, 2 flour, 2 butter", 180, "12 hours", 7, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Pumpkin Pie", "2 pumpkin, 2 flour, 2 butter", 180, "12 hours", 7, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Berry Pie", "32 sweetberry, 2 flour, 2 butter", 180, "12 hours", 7, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Fish Pie", "4 fish, 2 flour, 2 butter", 180, "1 hour", 14, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Chicken Pot Pie", "1 cooked chicken, 4 cooked carrots, 2 flour, 2 butter", 180, "1 hour", 16, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Beef Pot Pie", "1 Steak, 4 cooked carrots, 2 flour, 2 butter", 180, "1 hour", 16, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Beef Stew", "1 roast beef, 4 carrots, 2 flour, 2 bottles of water", 60, "1 hour", 16, Material.RABBIT_STEW));
        recipes.add(new ChefRecipe("Pork Stew", "1 porkchop, 4 carrots, 2 flour, 2 bottles of water", 60, "1 hour", 12, Material.RABBIT_STEW));
        recipes.add(new ChefRecipe("Chicken Stew", "1 cooked chicken, 4 carrots, 2 flour, 2 bottles of water", 60, "1 hour", 12, Material.RABBIT_STEW));
        recipes.add(new ChefRecipe("Lamb Stew", "1 cooked mutton, 4 carrots, 2 flour, 2 bottles of water", 60, "1 hour", 12, Material.RABBIT_STEW));
        recipes.add(new ChefRecipe("Vegetable Stew", "4 cooked carrots, 4 beets, 2 bamboo, 1 pumpkin", 60, "3 hours", 10, Material.SUSPICIOUS_STEW));
        recipes.add(new ChefRecipe("Beef Jerky", "1 steak", 360, "48 hours", 8, Material.DRIED_KELP));
        recipes.add(new ChefRecipe("Apple Turnover", "2 apples, 2 sugar, 1 dough", 30, "24 hours", 5, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Beef Turnover", "1 roastbeef, 1 dough", 30, "4 hours", 14, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Chicken Turnover", "1 cooked chocken , 1 dough", 30, "4 hours", 9, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Lamb Turnover", "1 cooked mutton , 1 dough", 30, "4 hours", 9, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Fish Turnover", "1 fish, 1 dough", 30, "4 hours", 7, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Berry Turnover", "16 sweetberry, 1 dough", 30, "24 hours", 7, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Apple Fritter", "1 dough, 2 apple, 1 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Berry Fritter", "1 dough, 16 sweetberry, 1 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Pumpkin Fritter", "1 dough, 1 pumpkin, 1 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Honey Fritter", "1 dough, 2 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Doughnut", "1 dough, 2 sugar, 1 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Apple Doughnut", "2 apple, 1 dough, 2 sugar, 1 honey", 30, "24 hours", 5, Material.COOKIE));
        recipes.add(new ChefRecipe("Cheese", "1 bucket of milk", 2160, "72 hours", 5, Material.RAW_GOLD));
        recipes.add(new ChefRecipe("Pizza Pie", "1 dough, 1 cheese, 1 porkchop, 2 mushroom", 10, "4 hours", 12, Material.SUNFLOWER));
        recipes.add(new ChefRecipe("Quiche", "1 dough, 1 cheese, 2 cooked carrot, 2 mushroom", 60, "4 hours", 12, Material.PUMPKIN_PIE));
        recipes.add(new ChefRecipe("Wafer", "1 dough, 1 milk, 1 egg", 30, "24 hours", 4, Material.HONEYCOMB));
        recipes.add(new ChefRecipe("Biscuit", "2 dough, 2 milk, 3 eggs, 12 sugar, 1 honey", 120, "24 hours", 6, Material.COOKIE));
        recipes.add(new ChefRecipe("Baklava", "2 dough, 4 butter, 2 honey", 240, "24 hours", 6, Material.BREAD));
        recipes.add(new ChefRecipe("Ice Cream", "2 milk bucket, 12 sugar", 720, "12 hours", 4, Material.POWDER_SNOW_BUCKET));
        recipes.add(new ChefRecipe("Pickled Carrots", "2 carrots, 1 honey", 720, "96 hours", 6, Material.CARROT));
        recipes.add(new ChefRecipe("Borscht", "4 beets, 2 cooked carrots, 1 milk", 30, "24 hours", 8, Material.BEETROOT_SOUP));
        recipes.add(new ChefRecipe("Fried Egg", "1 egg", 2, "2 hours", 4, Material.EGG));
        recipes.add(new ChefRecipe("Pasta", "1 dough, 2 eggs", 120, "24 hours", 6, Material.BLAZE_ROD));
    }

    public List<ChefRecipe> getRecipes() {
        return recipes;
    }
}
