package com.venned.simpletoons.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SitCommand implements CommandExecutor, Listener {

    private static final Map<UUID, ArmorStand> sittingArmorStands = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (sittingArmorStands.containsKey(uuid)) {
            ArmorStand stand = sittingArmorStands.get(uuid);
            if (stand != null && !stand.isDead()) {
                if (stand.getPassengers().contains(player)) {
                    stand.removePassenger(player);
                }
                stand.remove();
            }
            sittingArmorStands.remove(uuid);
            player.setSwimming(false);
            player.sendMessage(ChatColor.GREEN + "You've gotten up..");
        } else {
            Location loc = player.getLocation();
            ArmorStand stand = player.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setMarker(true);
            });
            stand.addPassenger(player);
            player.setSwimming(true);
            sittingArmorStands.put(uuid, stand);
            player.sendMessage(ChatColor.GREEN + "You sat down.");
        }
        return true;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (SitCommand.sittingArmorStands.containsKey(player.getUniqueId())) {
            ArmorStand stand = SitCommand.sittingArmorStands.get(player.getUniqueId());
            if (stand != null && !stand.isDead()) {
                if (stand.getPassengers().contains(player)) {
                    stand.removePassenger(player);
                }
                stand.remove();
            }
            SitCommand.sittingArmorStands.remove(player.getUniqueId());
        }
    }
}
