package com.venned.simpletoons.listeners.data;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.data.TonManager;
import com.venned.simpletoons.events.CharacterLoadEvent;
import com.venned.simpletoons.gui.CreationCharacterGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinDataListener implements Listener {
    TonManager tonManager;
    CreationCharacterGUI creationCharacterGUI;

    public PlayerJoinDataListener(TonManager tonManager, CreationCharacterGUI creationCharacterGUI) {
        this.tonManager = tonManager;
        this.creationCharacterGUI = creationCharacterGUI;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (tonManager.getPlayerTons().stream().noneMatch(p -> p.getOwner_uuid().equals(player.getUniqueId()))) {
            creationCharacterGUI.init(player);
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            PlayerSelect playerSelect = Main.getInstance().getPlayerSelectSet()
                    .stream()
                    .filter(p -> p.getPlayer().equals(player))
                    .findFirst().orElse(null);

            if (playerSelect != null) {
                CharacterLoadEvent characterLoadEvent = new CharacterLoadEvent(playerSelect.getToon(), playerSelect.getPlayer());
                Bukkit.getPluginManager().callEvent(characterLoadEvent);
            }
        }, 60);
    }
}
