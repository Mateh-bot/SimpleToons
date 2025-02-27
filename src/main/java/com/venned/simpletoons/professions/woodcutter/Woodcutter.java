package com.venned.simpletoons.professions.woodcutter;

import com.venned.simpletoons.professions.Profession;
import org.bukkit.entity.Player;

public class Woodcutter extends Profession {

    public Woodcutter() {
        super("Woodcutter");
    }

    @Override
    public void onTaskCompleted(Player player) {
        grantExperience(player, 7);
    }

    @Override
    protected void onLevelUp(Player player) {
        player.sendMessage("Your woodcutting skills have improved! You are now level " + getLevel() + " in Woodcutter.");
    }
}
