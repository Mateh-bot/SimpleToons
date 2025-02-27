package com.venned.simpletoons.professions.chef;

import org.bukkit.Material;

public class ChefRecipe {

    private String name;
    private String ingredients;
    private int cookTime;
    private String decayTime;
    private double baseSaturation;
    private Material icon;

    public ChefRecipe(String name, String ingredients, int cookTime, String decayTime, double baseSaturation, Material icon) {
        this.name = name;
        this.ingredients = ingredients;
        this.cookTime = cookTime;
        this.decayTime = decayTime;
        this.baseSaturation = baseSaturation;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getCookTime() {
        return cookTime;
    }

    public String getDecayTime() {
        return decayTime;
    }

    public double getBaseSaturation() {
        return baseSaturation;
    }

    public Material getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return name;
    }
}
