package com.venned.simpletoons.professions.mason;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Mason extends Profession {

    public Mason() {
        super("Mason");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 8);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your masonry skills have advanced! You are now level " + getLevel() + " in Mason.");
    }
}
