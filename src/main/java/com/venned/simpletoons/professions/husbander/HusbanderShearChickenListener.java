package com.venned.simpletoons.professions.husbander;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HusbanderShearChickenListener implements Listener {
    private static final Map<UUID, Long> shearCooldowns = new HashMap<>();
    private static final long SHEAR_COOLDOWN_TIME = 10 * 1000;

    private static final Map<UUID, Long> shearMessageCooldown = new HashMap<>();
    private static final long MESSAGE_COOLDOWN_TIME = 3 * 1000;

    private static final Map<UUID, Long> nonHusbanderMessageCooldown = new HashMap<>();
    private static final long NON_HUSBANDER_MSG_COOLDOWN = 10 * 1000;

    private static final Random random = new Random();

    @EventHandler
    public void onChickenInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Chicken))
            return;

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null ||
                player.getInventory().getItemInMainHand().getType() != Material.SHEARS)
            return;

        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();

        boolean isHusbander = toon.getProfession("Husbander") != null;
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (!isHusbander) {
            if (!nonHusbanderMessageCooldown.containsKey(uuid) ||
                    currentTime - nonHusbanderMessageCooldown.get(uuid) >= NON_HUSBANDER_MSG_COOLDOWN) {
                player.sendMessage("Only Husbanders can shear chickens for feathers.");
                nonHusbanderMessageCooldown.put(uuid, currentTime);
            }
            event.setCancelled(true);
            return;
        }

        if (shearCooldowns.containsKey(uuid)) {
            long lastTime = shearCooldowns.get(uuid);
            if (currentTime - lastTime < SHEAR_COOLDOWN_TIME) {
                if (!shearMessageCooldown.containsKey(uuid) ||
                        currentTime - shearMessageCooldown.get(uuid) >= MESSAGE_COOLDOWN_TIME) {
                    long secondsLeft = (SHEAR_COOLDOWN_TIME - (currentTime - lastTime)) / 1000;
                    player.sendMessage("You are on cooldown. Please wait " + secondsLeft + " second(s).");
                    shearMessageCooldown.put(uuid, currentTime);
                }
                event.setCancelled(true);
                return;
            }
        }

        event.setCancelled(true);

        int feathersAmount = random.nextInt(4) + 1;
        ItemStack feathers = new ItemStack(Material.FEATHER, feathersAmount);
        player.getInventory().addItem(feathers);
        player.sendMessage("You have sheared the chicken and obtained " + feathersAmount + " feather(s).");

        shearCooldowns.put(uuid, currentTime);
    }
}
