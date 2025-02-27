package com.venned.simpletoons.commands;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.Race;
import com.venned.simpletoons.events.CharacterLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RaceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Use: /race <name>");
            return true;
        }

        Race race = Main.getInstance().getRaceManager().getRace(args[0]);

        if (race != null) {
            player.sendMessage("§aRace: " + race.getName());
            player.sendMessage("§7Age Max: " + race.getMaxAge());
            player.sendMessage("§7Height: " + race.getHeight());
            player.sendMessage("§7Reach Attack: " + race.getReach());
            player.sendMessage("§7Attack : " + race.getAttack());
            PlayerSelect playerSelect = Main.getInstance().getPlayerSelectSet()
                    .stream().filter(p -> p.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
            if (playerSelect != null) {
                playerSelect.getToon().setRace(race.getName());
                CharacterLoadEvent characterLoadEvent = new CharacterLoadEvent(playerSelect.getToon(), player);
                Bukkit.getPluginManager().callEvent(characterLoadEvent);
            }
            applyRaceAttributes(player, race);
        } else {
            player.sendMessage("§cThe race '" + args[0] + "' was not found.");
        }
        return true;
    }

    private void applyRaceAttributes(Player player, Race race) {
        AttributeInstance height = player.getAttribute(org.bukkit.attribute.Attribute.SCALE);
        if (height != null) {
            height.setBaseValue(1.0 + race.getHeight()); // Valor base de alcance + bonificación de la raza
        }

        // 2️⃣ Modificar Alcance (GENERIC_REACH_DISTANCE)
        AttributeInstance reach = player.getAttribute(org.bukkit.attribute.Attribute.ENTITY_INTERACTION_RANGE);
        if (reach != null) {
            reach.setBaseValue(3.0 + race.getReach()); // Valor base de alcance + bonificación de la raza
        }

        // 3️⃣ Modificar Daño de Ataque (GENERIC_ATTACK_DAMAGE)
        AttributeInstance attack = player.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(1.0 + race.getAttack()); // Daño base + bonificación de la raza
        }
    }
}
