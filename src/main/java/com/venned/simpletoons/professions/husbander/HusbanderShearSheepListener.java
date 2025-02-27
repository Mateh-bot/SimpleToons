package com.venned.simpletoons.professions.husbander;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.Optional;

public class HusbanderShearSheepListener implements Listener {

    @EventHandler
    public void onShearEntity(PlayerShearEntityEvent event) {
        if (!(event.getEntity() instanceof Sheep))
            return;
        Player player = event.getPlayer();
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();
        boolean isHusbander = toon.getProfession("Husbander") != null;
        if (!isHusbander) {
            player.sendMessage("Only Husbanders can shear sheep.");
            event.setCancelled(true);
        }
    }
}
