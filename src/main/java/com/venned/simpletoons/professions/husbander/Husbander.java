package com.venned.simpletoons.professions.husbander;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Husbander extends Profession {

    public Husbander() {
        super("Husbander");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 12);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your animal handling skills have advanced! You are now level " + getLevel() + " in Husbander.");
    }
}
