package com.venned.simpletoons.professions;

import org.bukkit.entity.Player;

public abstract class Profession {
    private String name;
    private int level;
    private int experience;
    private static final int BASE_XP = 100;

    public Profession(String name) {
        this.name = name;
        this.level = 1;
        this.experience = 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getRequiredExperienceForNextLevel() {
        return BASE_XP + (level * 50);
    }

    public void grantExperience(Player player, int amount) {
        experience += amount;
        player.sendMessage("§e+" + amount + " XP in " + name + ".");
        while (experience >= getRequiredExperienceForNextLevel()) {
            levelUp(player);
        }
    }

    private void levelUp(Player player) {
        level++;
        experience = 0;
        player.sendMessage("§aYou have reached level " + level + " in " + name + "!");
        onLevelUp(player);
    }

    protected abstract void onLevelUp(Player player);

    public abstract void onTaskCompleted(Player player);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Profession)) return false;
        Profession other = (Profession) obj;
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name + " (Lv. " + level + ", " + experience + "/" + getRequiredExperienceForNextLevel() + " XP)";
    }
}
