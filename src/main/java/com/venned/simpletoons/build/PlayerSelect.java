package com.venned.simpletoons.build;

import org.bukkit.entity.Player;

public class PlayerSelect {
    Player player;
    PlayerTon toon;

    public PlayerSelect(Player player, PlayerTon toon) {
        this.player = player;
        this.toon = toon;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerTon getToon() {
        return toon;
    }
}
