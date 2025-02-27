package com.venned.simpletoons.professions.leatherworker;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Leatherworker extends Profession {

    public Leatherworker() {
        super("Leatherworker");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 5);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your leatherworking skills have advanced! You are now level " + getLevel() + " in Leatherworker.");
    }
}
