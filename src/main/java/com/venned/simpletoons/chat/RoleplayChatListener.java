package com.venned.simpletoons.chat;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.data.TonManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.util.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RoleplayChatListener implements Listener {

    private final HashMap<String, Integer> chatDistances = new HashMap<>();
    private final TonManager tonManager;

    public RoleplayChatListener(TonManager tonManager) {
        this.tonManager = tonManager;
        loadConfigValues();
    }

    private void loadConfigValues() {
        chatDistances.put("#Rp", Main.getInstance().getConfig().getInt("chat.rp_range", 16));
        chatDistances.put("#Q", Main.getInstance().getConfig().getInt("chat.q_range", 8));
        chatDistances.put("#S", Main.getInstance().getConfig().getInt("chat.s_range", 32));
        chatDistances.put("#W", 1);
        chatDistances.put("#OOC", -1); // Global
        chatDistances.put("#WOOC", 1);
        chatDistances.put("#QOOC", 8);
        chatDistances.put("#LOOC", 16);
        chatDistances.put("#SOOC", 32);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String message = event.getMessage();

        for (String prefix : chatDistances.keySet()) {
            if (message.startsWith(prefix)) {
                event.setCancelled(true);
                String cleanMessage = ChatColor.translateAlternateColorCodes('&', message.replaceFirst(prefix, "").trim());
                broadcastRoleplayMessage(sender, cleanMessage, prefix);
                return;
            }
        }
    }

    private void broadcastRoleplayMessage(Player sender, String message, String chatType) {
        int distance = chatDistances.get(chatType);
        Set<Player> recipients = new HashSet<>();

        if (distance == -1) {
            recipients.addAll(Bukkit.getOnlinePlayers()); // Mensaje global
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().equals(sender.getWorld()) && player.getLocation().distance(sender.getLocation()) <= distance) {
                    if (chatType.equals("#Q") || chatType.equals("#QOOC")) {
                        if (!hasLineOfSight(sender, player)) continue; // Bloqueo por paredes
                    }
                    recipients.add(player);
                }
            }
        }

        String formattedMessage = formatMessage(sender, message, chatType);
        for (Player recipient : recipients) {
            recipient.sendMessage(formattedMessage);
        }

        logChat(sender, chatType, message);
        playChatSound(sender, chatType);
    }

    private boolean hasLineOfSight(Player sender, Player receiver) {
        Vector direction = receiver.getLocation().toVector().subtract(sender.getLocation().toVector()).normalize();
        Vector position = sender.getLocation().toVector();
        double distance = sender.getLocation().distance(receiver.getLocation());

        for (double i = 0; i < distance; i += 0.5) {
            position.add(direction.multiply(0.5));
            Block block = sender.getWorld().getBlockAt(position.toLocation(sender.getWorld()));
            if (block.getType() != Material.AIR) {
                return false; // Hay una pared en medio
            }
        }
        return true;
    }

    private String formatMessage(Player sender, String message, String chatType) {
        PlayerTon toon = tonManager.getPlayerTons().stream()
                .filter(p -> p.getOwner_uuid().equals(sender.getUniqueId()))
                .findFirst().orElse(null);

        String toonName = (toon != null) ? toon.getName() : sender.getName();
        String color = ChatColor.WHITE.toString();

        if (chatType.contains("OOC")) {
            color = ChatColor.GRAY.toString();
        }

        return color + "[" + chatType.replace("#", "") + "] " + toonName + ": " + message;
    }

    private void logChat(Player sender, String chatType, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_log.txt", true))) {
            writer.write("[" + LocalDateTime.now() + "] [" + sender.getName() + "] [" + chatType + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playChatSound(Player sender, String chatType) {
        Sound sound = Sound.ENTITY_VILLAGER_TRADE;

        switch (chatType) {
            case "#Rp":
                sound = Sound.ENTITY_VILLAGER_AMBIENT;
                break;
            case "#S":
                sound = Sound.ENTITY_ENDER_DRAGON_GROWL;
                break;
            case "#Q":
            case "#QOOC":
                sound = Sound.ENTITY_BAT_TAKEOFF;
                break;
        }

        sender.playSound(sender.getLocation(), sound, 1.0f, 1.0f);
    }
}
