package com.venned.simpletoons.manager;

import com.venned.simpletoons.Main;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class AgeManager {

    public static void applyAgeStage(Player player, int age) {
        resetAttributes(player); // Resetea antes de aplicar nuevos cambios
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (age < 14) {
                applyChildStage(player);
            } else if (age >= 14 && age < 18) {
                applyTeenagerStage(player);
            } else if (age >= 80) {
                applyOldAgeStage(player);
            }
        }, 20);
    }

    private static void resetAttributes(Player player) {
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20); // Resetea la salud
        player.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(1); // Daño base normal
        player.getAttribute(Attribute.SCALE).setBaseValue(1);
    }

    private static void applyChildStage(Player player) {
        AttributeInstance health = player.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(10);
        }

        AttributeInstance reach = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE);
        if (reach != null) {
            reach.setBaseValue(2);
        }

        AttributeInstance height = player.getAttribute(Attribute.SCALE);
        if (height != null) {
            height.setBaseValue(0.5);
        }
        player.sendMessage("§a been applied: Child");
    }

    private static void applyTeenagerStage(Player player) {
        AttributeInstance health = player.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(20);
        }

        AttributeInstance reach = player.getAttribute(org.bukkit.attribute.Attribute.ENTITY_INTERACTION_RANGE);
        if (reach != null) {
            reach.setBaseValue(3);
        }

        AttributeInstance height = player.getAttribute(Attribute.SCALE);
        if (height != null) {
            height.setBaseValue(1);
        }
        player.sendMessage("§a been applied: Teenager");
    }

    private static void applyOldAgeStage(Player player) {
        AttributeInstance health = player.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(20);
        }

        AttributeInstance reach = player.getAttribute(org.bukkit.attribute.Attribute.ENTITY_INTERACTION_RANGE);
        if (reach != null) {
            reach.setBaseValue(3);
        }

        AttributeInstance height = player.getAttribute(Attribute.SCALE);
        if (height != null) {
            height.setBaseValue(1);
        }
        player.sendMessage("§a been applied: OldAge");
    }
}
