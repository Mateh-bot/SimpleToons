package com.venned.simpletoons.professions.farmer;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Farmer extends Profession {

    public Farmer() {
        super("Farmer");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 10);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your farming skills have improved! You are now level " + getLevel() + " in Farmer.");
    }
}
