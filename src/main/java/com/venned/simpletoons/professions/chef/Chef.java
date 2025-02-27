package com.venned.simpletoons.professions.chef;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Chef extends Profession {

    public Chef() {
        super("Chef");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 20);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your cooking skills have improved! You are now level " + getLevel() + " in Chef.");
    }
}
