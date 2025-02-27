package com.venned.simpletoons.professions.blacksmith;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Blacksmith extends Profession {

    public Blacksmith() {
        super("Blacksmith");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 15);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your forging skills have advanced! You are now level " + getLevel() + " in Blacksmith.");
    }
}
