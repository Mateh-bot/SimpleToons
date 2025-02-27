package com.venned.simpletoons.task;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class ToonsActive extends BukkitRunnable {

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            List<PlayerTon> toons = Main.getInstance().getTonManager()
                    .getPlayerTons().stream().filter(p -> p.getOwner_uuid().equals(players.getUniqueId()))
                    .collect(Collectors.toList());
            for (PlayerTon toon : toons) {
                if (toon.isActive()) {
                    PlayerSelect playerSelect = Main.getInstance().getPlayerSelectSet()
                            .stream().filter(p -> p.getPlayer().equals(players))
                            .findFirst().orElse(null);
                    if (playerSelect == null) {
                        PlayerSelect playerSelectNew = new PlayerSelect(players, toon);
                        Main.getInstance().getPlayerSelectSet().add(playerSelectNew);
                    }
                    break;
                } else {
                    PlayerSelect playerSelect = Main.getInstance().getPlayerSelectSet()
                            .stream().filter(p -> p.getPlayer().equals(players))
                            .findFirst().orElse(null);
                    if (playerSelect == null) {
                        PlayerSelect playerSelectNew = new PlayerSelect(players, toon);
                        Main.getInstance().getPlayerSelectSet().add(playerSelectNew);
                    }
                    break;
                }
            }
        }
    }
}
