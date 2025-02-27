package com.venned.simpletoons.professions;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionCommand implements CommandExecutor, TabCompleter {

    private final ProfessionManager professionManager;

    public ProfessionCommand() {
        this.professionManager = new ProfessionManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent()) {
            player.sendMessage(ChatColor.RED + "You do not have an active Toon.");
            return true;
        }
        PlayerTon toon = psOpt.get().getToon();

        if (args.length == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.GREEN).append("Your Toon has assigned:\n");
            if (toon.getAllProfessions().isEmpty()) {
                sb.append(ChatColor.YELLOW).append("None");
            } else {
                for (Profession prof : toon.getAllProfessions().values()) {
                    sb.append(ChatColor.YELLOW)
                            .append("- ").append(prof.getName())
                            .append(" (Level ").append(prof.getLevel())
                            .append(" [").append(prof.getExperience())
                            .append("/").append(prof.getRequiredExperienceForNextLevel()).append(" XP])\n");
                }
            }
            player.sendMessage(sb.toString());
            return true;
        } else if (args.length >= 1) {
            String sub = args[0].toLowerCase();
            if (sub.equals("list")) {
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.GREEN).append("Available professions:\n");
                for (Profession prof : professionManager.getAllProfessions()) {
                    sb.append(ChatColor.YELLOW)
                            .append("- ").append(prof.getName())
                            .append("\n");
                }
                sb.append(ChatColor.GREEN).append("You can have a maximum of 2 professions per Toon.");
                player.sendMessage(sb.toString());
                return true;
            } else if (args.length >= 2) {
                String action = sub;
                String profName = args[1];
                Profession newInstance = professionManager.createProfession(profName);
                if (newInstance == null) {
                    player.sendMessage(ChatColor.RED + "The profession '" + profName + "' does not exist.");
                    return true;
                }
                if (action.equals("add")) {
                    if (toon.getAllProfessions().size() >= 2) {
                        player.sendMessage(ChatColor.RED + "Your Toon can have a maximum of 2 professions.");
                        return true;
                    }
                    if (!toon.addProfession(newInstance)) {
                        player.sendMessage(ChatColor.RED + "Your Toon already has the profession " + newInstance.getName() + ".");
                    } else {
                        player.sendMessage(ChatColor.GREEN + "The profession " + newInstance.getName() + " has been added to your Toon.");
                    }
                    return true;
                } else if (action.equals("remove")) {
                    if (toon.getProfession(newInstance.getName()) == null) {
                        player.sendMessage(ChatColor.RED + "Your Toon does not have the profession " + newInstance.getName() + ".");
                    } else {
                        toon.removeProfession(newInstance);
                        player.sendMessage(ChatColor.GREEN + "The profession " + newInstance.getName() + " has been removed from your Toon.");
                    }
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /profession [add|remove|list] <profession name>");
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /profession [add|remove|list] <profession name>");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completions;
        }
        if (args.length == 1) {
            String[] subs = {"add", "remove", "list"};
            for (String s : subs) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(s);
                }
            }
        } else if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (sub.equals("add") || sub.equals("remove")) {
                String[] available = {"Chef", "Blacksmith", "Farmer", "Husbander", "Mason", "Woodcutter", "Fisherman"};
                for (String prof : available) {
                    if (prof.toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(prof);
                    }
                }
            }
        }
        return completions;
    }
}
