package com.venned.simpletoons.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CarryCommand implements CommandExecutor {

    private static final Map<UUID, UUID> carryRequests = new HashMap<>();
    private static final Map<UUID, UUID> carryingPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Use: /carry <player> | /carry accept | /carry deny | /carry stop");
            return true;
        }

        String subcommand = args[0];

        if (subcommand.equalsIgnoreCase("stop")) {
            if (carryingPlayers.containsKey(player.getUniqueId())) {
                UUID carriedUUID = carryingPlayers.get(player.getUniqueId());
                Player carried = Bukkit.getPlayer(carriedUUID);
                if (carried != null) {
                    carried.leaveVehicle();
                    carried.sendMessage(ChatColor.YELLOW + player.getName() + " has stopped carrying you.");
                }
                carryingPlayers.remove(player.getUniqueId());
                player.removePotionEffect(PotionEffectType.SLOWNESS);
                player.sendMessage(ChatColor.GREEN + "You have stopped carrying someone.");
            } else {
                player.sendMessage(ChatColor.RED + "You're not carrying someone.");
            }
            return true;
        } else if (subcommand.equalsIgnoreCase("accept")) {
            UUID carrierUUID = carryRequests.get(player.getUniqueId());
            if (carrierUUID == null) {
                player.sendMessage(ChatColor.RED + "You have no requests to carry.");
                return true;
            }
            Player carrier = Bukkit.getPlayer(carrierUUID);
            if (carrier == null) {
                player.sendMessage(ChatColor.RED + "The player who tried to carry you is no longer available.");
                carryRequests.remove(player.getUniqueId());
                return true;
            }
            carrier.addPassenger(player);
            carrier.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 0, false, false));
            carryingPlayers.put(carrier.getUniqueId(), player.getUniqueId());
            carryRequests.remove(player.getUniqueId());
            carrier.sendMessage(ChatColor.GREEN + "Now you are carrying " + player.getName() + ".");
            player.sendMessage(ChatColor.GREEN + "Now you are being carried by " + carrier.getName() + ".");
            return true;
        } else if (subcommand.equalsIgnoreCase("deny")) {
            UUID carrierUUID = carryRequests.get(player.getUniqueId());
            if (carrierUUID == null) {
                player.sendMessage(ChatColor.RED + "You have no requests to carry.");
                return true;
            }
            Player carrier = Bukkit.getPlayer(carrierUUID);
            if (carrier != null) {
                carrier.sendMessage(ChatColor.RED + player.getName() + " has rejected your request to carry.");
            }
            carryRequests.remove(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "You have rejected the request to carry.");
            return true;
        }

        Player target = Bukkit.getPlayer(subcommand);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }
        if (target.equals(player)) {
            player.sendMessage(ChatColor.RED + "You can't carry yourself.");
            return true;
        }

        carryRequests.put(target.getUniqueId(), player.getUniqueId());

        TextComponent acceptMessage = new TextComponent(ChatColor.GREEN + "[ACCEPT]");
        acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/carry accept"));
        TextComponent denyMessage = new TextComponent(ChatColor.RED + " [DENY]");
        denyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/carry deny"));

        target.sendMessage(ChatColor.AQUA + player.getName() + " wants to carry you. Click on an option:");
        target.spigot().sendMessage(acceptMessage, denyMessage);
        player.sendMessage(ChatColor.YELLOW + "You have sent a request to carry " + target.getName() + ".");

        return true;
    }
}
