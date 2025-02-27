package com.venned.simpletoons.professions.fisherman;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Fisherman extends Profession {

    public Fisherman() {
        super("Fisherman");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 10);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your fishing skills have advanced! You are now level " + getLevel() + " in Fisherman.");
    }
}
