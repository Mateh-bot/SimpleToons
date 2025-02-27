package com.venned.simpletoons.professions.fisherman;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Optional;
import java.util.Random;

public class FishermanFishListener implements Listener {

    private static final Random random = new Random();

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH)
            return;

        Player player = event.getPlayer();
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();
        boolean isFisherman = toon.getProfession("Fisherman") != null;
        if (!isFisherman)
            return;

        int level = 1;

        double chanceFor5Star = level / 200.0;
        int stars;
        if (random.nextDouble() < chanceFor5Star) {
            stars = 5;
        } else {
            stars = random.nextInt(4) + 1;
        }

        double saturationBonus = stars * 0.5;
        player.sendMessage(ChatColor.GREEN + "You caught a fish with " + stars + " star(s)! (+" + saturationBonus + " saturation)");
    }
}
