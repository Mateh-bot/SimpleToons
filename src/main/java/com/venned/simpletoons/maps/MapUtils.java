package com.venned.simpletoons.maps;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class MapUtils {

    private final Set<Player> inDescription;

    public MapUtils() {
        this.inDescription = new HashSet<>();
    }

    public Set<Player> getInDescription() {
        return inDescription;
    }
}
