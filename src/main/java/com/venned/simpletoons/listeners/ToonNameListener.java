package com.venned.simpletoons.listeners;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ToonNameListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerTon toon = Main.getInstance().getTonManager().getPlayerTonByUUID(player.getUniqueId());

        if (toon != null) {
            String toonName = toon.getName();

            player.setDisplayName(toonName);
            player.setPlayerListName(toonName);

            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Team team = board.registerNewTeam("toonTeam_" + player.getUniqueId());
            team.setPrefix("");
            team.setSuffix("");
            team.addEntry(player.getName());
            player.setScoreboard(board);
        }
    }
}
