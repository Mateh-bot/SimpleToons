package com.venned.simpletoons.professions.husbander;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HusbanderBreedListener implements Listener {
    private static final Map<UUID, Long> breedMessageCooldown = new HashMap<>();
    private static final long BREED_MESSAGE_COOLDOWN = 30 * 1000;

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        LivingEntity breederEntity = event.getBreeder();
        if (!(breederEntity instanceof Player)) {
            return;
        }
        Player breeder = (Player) breederEntity;
        if (breeder == null)
            return;

        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(breeder))
                .findFirst();
        if (!psOpt.isPresent())
            return;

        PlayerTon toon = psOpt.get().getToon();
        boolean isHusbander = toon.getProfession("Husbander") != null;
        if (!isHusbander) {
            UUID uuid = breeder.getUniqueId();
            long currentTime = System.currentTimeMillis();
            if (!breedMessageCooldown.containsKey(uuid) ||
                    currentTime - breedMessageCooldown.get(uuid) >= BREED_MESSAGE_COOLDOWN) {
                breeder.sendMessage("Only Husbanders can breed animals.");
                breedMessageCooldown.put(uuid, currentTime);
            }
            event.setCancelled(true);
        }
    }
}
