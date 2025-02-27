package com.venned.simpletoons.thievery;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ThieveryManager {
    private final Map<String, Long> jammedBlocks = new HashMap<>();
    private final Map<UUID, Integer> containerLockpickCount = new HashMap<>();
    private final Map<UUID, Map<String, Long>> thiefContainerAttempts = new HashMap<>();
    private final Map<UUID, Long> pickpocketCooldown = new HashMap<>();

    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    public static final long PICKPOCKET_COOLDOWN = 30 * 60 * 1000L;

    private String getLocationKey(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    public boolean isJammed(Location loc) {
        String key = getLocationKey(loc);
        Long expires = jammedBlocks.get(key);
        if (expires == null) return false;
        if (System.currentTimeMillis() > expires) {
            jammedBlocks.remove(key);
            return false;
        }
        return true;
    }

    public void jamBlock(Location loc) {
        jammedBlocks.put(getLocationKey(loc), System.currentTimeMillis() + ONE_DAY);
    }

    public boolean canAttemptContainer(UUID thief, Location loc) {
        String locKey = getLocationKey(loc);
        Map<String, Long> attempts = thiefContainerAttempts.getOrDefault(thief, new HashMap<>());
        Long lastAttempt = attempts.get(locKey);
        if (lastAttempt != null && System.currentTimeMillis() < lastAttempt + ONE_DAY) {
            return false;
        }
        int count = containerLockpickCount.getOrDefault(thief, 0);
        return count < 5;
    }

    public void recordContainerAttempt(UUID thief, Location loc) {
        String locKey = getLocationKey(loc);
        Map<String, Long> attempts = thiefContainerAttempts.computeIfAbsent(thief, k -> new HashMap<>());
        attempts.put(locKey, System.currentTimeMillis());
        int count = containerLockpickCount.getOrDefault(thief, 0);
        containerLockpickCount.put(thief, count + 1);
    }

    public boolean canPickpocket(UUID thief) {
        Long cd = pickpocketCooldown.get(thief);
        return cd == null || System.currentTimeMillis() > cd;
    }

    public void setPickpocketCooldown(UUID thief) {
        pickpocketCooldown.put(thief, System.currentTimeMillis() + PICKPOCKET_COOLDOWN);
    }

    public void resetDailyLimits() {
        containerLockpickCount.clear();
        thiefContainerAttempts.clear();
    }
}
