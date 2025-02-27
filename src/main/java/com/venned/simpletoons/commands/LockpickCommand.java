package com.venned.simpletoons.commands;

import com.venned.simpletoons.thievery.ContainerExtractionGUI;
import com.venned.simpletoons.thievery.ThieveryManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

public class LockpickCommand implements CommandExecutor {

    private final ThieveryManager thieveryManager;
    private final Random random = new Random();

    public LockpickCommand(ThieveryManager manager) {
        this.thieveryManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        Block targetBlock = player.getTargetBlockExact(5);
        if (targetBlock == null) {
            player.sendMessage(ChatColor.RED + "No valid target block found within range.");
            return true;
        }

        Material blockType = targetBlock.getType();
        boolean isContainer = targetBlock.getState() instanceof Container;
        boolean isDoor = blockType.name().contains("DOOR") || blockType.name().contains("TRAPDOOR");

        if (!isContainer && !isDoor) {
            player.sendMessage(ChatColor.RED + "The targeted block is not lockpickable.");
            return true;
        }

        if (thieveryManager.isJammed(targetBlock.getLocation())) {
            player.sendMessage(ChatColor.RED + "This lock is jammed and cannot be picked right now.");
            return true;
        }

        UUID thiefUUID = player.getUniqueId();

        if (isContainer) {
            if (!thieveryManager.canAttemptContainer(thiefUUID, targetBlock.getLocation())) {
                player.sendMessage(ChatColor.RED + "You have already lockpicked this container or reached your daily limit.");
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("lockpick")) {
            if (!player.getInventory().contains(Material.TRIPWIRE_HOOK)) {
                player.sendMessage(ChatColor.RED + "You do not have any lockpicks in your inventory.");
                return true;
            }
            ItemStack lockpick = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            player.getInventory().removeItem(lockpick);
        }

        //boolean success = random.nextDouble() < 0.25;
        boolean success = true;
        if (!success) {
            thieveryManager.jamBlock(targetBlock.getLocation());
            player.sendMessage(ChatColor.RED + "Lockpick attempt failed! The lock is now jammed for 24 hours.");
        } else {
            if (isDoor) {
                player.sendMessage(ChatColor.GREEN + "You have successfully picked the lock on the door!");
                // Opcional: aquí se podría implementar lógica para abrir la puerta
            } else if (isContainer) {
                thieveryManager.recordContainerAttempt(thiefUUID, targetBlock.getLocation());
                player.sendMessage(ChatColor.GREEN + "You have successfully picked the lock on the container! You may now extract one item from it.");
                if (targetBlock.getState() instanceof Container) {
                    ContainerExtractionGUI extractionGUI = new ContainerExtractionGUI();
                    extractionGUI.open(player, (Container) targetBlock.getState());
                }
            }
        }
        return true;
    }
}
