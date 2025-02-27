package com.venned.simpletoons.events;

import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CharacterLoadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final PlayerTon toons;
    private final Player player; // Puede ser null para eventos como LOAD o UNLOAD.

    public CharacterLoadEvent(PlayerTon playerTon, Player player) {
        this.toons = playerTon;
        this.player = player;
    }

    public PlayerTon getToons() {
        return toons;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
