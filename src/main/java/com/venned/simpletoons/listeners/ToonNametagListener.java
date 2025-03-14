package com.venned.simpletoons.listeners;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToonNametagListener implements Listener {

    private static Map<UUID, ArmorStand> holograms = new HashMap<>();

    public static ArmorStand getHologram(UUID uuid) {
        return holograms.get(uuid);
    }

    public static void removeAllHolograms() {
        for (ArmorStand hologram : holograms.values()) {
            if (hologram != null && !hologram.isDead()) {
                hologram.remove();
            }
        }
        holograms.clear();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerTon toon = Main.getInstance().getTonManager().getPlayerTonByUUID(player.getUniqueId());
        if (toon == null) {
            return;
        }

        String toonName = toon.getName();

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.getTeam("toon_" + player.getUniqueId());
        if (team == null) {
            team = board.registerNewTeam("toon_" + player.getUniqueId());
        }
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.addEntry(player.getName());
        player.setScoreboard(board);

        Location loc = player.getLocation().add(0, 2.0, 0);
        ArmorStand hologram = player.getWorld().spawn(loc, ArmorStand.class, stand -> {
            stand.setCustomName(toonName);
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setMarker(true);
        });

        holograms.put(player.getUniqueId(), hologram);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArmorStand hologram = holograms.remove(player.getUniqueId());
        if (hologram != null && !hologram.isDead()) {
            hologram.remove();
        }
    }
}
