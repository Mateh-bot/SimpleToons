package com.venned.simpletoons.listeners.data;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.build.Race;
import com.venned.simpletoons.events.CharacterLoadEvent;
import com.venned.simpletoons.manager.AgeManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CharacterLoadListener implements Listener {

    @EventHandler
    public void onLoadCharacter(CharacterLoadEvent event) {
        Race race = Main.getInstance().getRaceManager().getRace(event.getToons().getRace());
        event.getPlayer().sendMessage("Race " + race.getName());
        if (race != null) {
            if (race.getName().equalsIgnoreCase("human")) {
                applyRaceHuman(event.getPlayer(), event.getToons());
                return;
            }
            applyRaceAttributes(event.getPlayer(), race);
        }
    }

    private void applyRaceHuman(Player player, PlayerTon playerTon) {
        int age = playerTon.getAge();
        AgeManager.applyAgeStage(player, age);
    }

    private void applyRaceAttributes(Player player, Race race) {
        AttributeInstance height = player.getAttribute(Attribute.SCALE);
        if (height != null) {
            height.setBaseValue(1.0 + race.getHeight());
        }

        AttributeInstance reach = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE);
        if (reach != null) {
            reach.setBaseValue(3.0 + race.getReach());
        }

        AttributeInstance attack = player.getAttribute(Attribute.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(1.0 + race.getAttack());
        }
    }
}
