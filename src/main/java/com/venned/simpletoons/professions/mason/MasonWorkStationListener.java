package com.venned.simpletoons.professions.mason;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class MasonWorkStationListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("RIGHT_CLICK"))
            return;
        Block block = event.getClickedBlock();
        if (block == null)
            return;
        if (block.getType() != Material.STONECUTTER)
            return;
        Player player = event.getPlayer();
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();
        boolean isMason = toon.getProfession("Mason") != null;
        if (!isMason) {
            player.sendMessage("Only Mason can use the Stonecutter.");
            event.setCancelled(true);
        }
    }
}
