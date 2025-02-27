package com.venned.simpletoons.manager;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.Race;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class RaceManager {
    private final Map<String, Race> races = new HashMap<>();

    public RaceManager(Main plugin) {
        loadRaces(plugin.getConfig());
    }

    private void loadRaces(FileConfiguration config) {
        if (config.isConfigurationSection("races")) {
            for (String key : config.getConfigurationSection("races").getKeys(false)) {
                int maxAge = config.getInt("races." + key + ".max-age");
                double height = config.getDouble("races." + key + ".height");
                double reach = config.getDouble("races." + key + ".reach");
                double attack = config.getDouble("races." + key + ".attack");
                Race race = new Race(key, maxAge, height, reach, attack);
                races.put(key.toLowerCase(), race);
            }
        }
    }

    public Race getRace(String name) {
        return races.get(name.toLowerCase());
    }

    public Map<String, Race> getAllRaces() {
        return races;
    }
}
