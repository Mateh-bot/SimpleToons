package com.venned.simpletoons.commands;

import com.venned.simpletoons.thievery.PickpocketGUI;
import com.venned.simpletoons.thievery.ThieveryManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class PickpocketCommand implements CommandExecutor {
    private final ThieveryManager thieveryManager;
    private final Random random = new Random();

    public PickpocketCommand(ThieveryManager manager) {
        this.thieveryManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player thief = (Player) sender;
        if (args.length != 1) {
            thief.sendMessage(ChatColor.YELLOW + "Usage: /pickpocket <username>");
            return true;
        }
        UUID thiefUUID = thief.getUniqueId();
        if (!thieveryManager.canPickpocket(thiefUUID)) {
            thief.sendMessage(ChatColor.RED + "You are on cooldown. Try again later.");
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            thief.sendMessage(ChatColor.RED + "Target player not found.");
            return true;
        }
        if (thief.getLocation().distance(target.getLocation()) > 2.0) {
            thief.sendMessage(ChatColor.RED + "You must be within 2 blocks of the target to pickpocket.");
            return true;
        }
        //boolean success = random.nextDouble() < 0.10;
        boolean success = true;
        thieveryManager.setPickpocketCooldown(thiefUUID);
        if (!success) {
            thief.sendMessage(ChatColor.RED + "Pickpocket attempt failed!");
            target.sendMessage(ChatColor.RED + "Someone attempted to pick your pocket!");
        } else {
            thief.sendMessage(ChatColor.GREEN + "Pickpocket successful! Select one item from " + target.getName() + "'s inventory.");
            PickpocketGUI.openPickpocketGUI(thief, target);
        }
        return true;
    }
}
