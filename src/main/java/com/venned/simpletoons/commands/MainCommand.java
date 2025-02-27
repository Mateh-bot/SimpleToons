package com.venned.simpletoons.commands;

import com.venned.simpletoons.Main;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.Collections;

public class MainCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openMenu(player);
        }
        return false;
    }

    void openMenu(Player player) {
        new AnvilGUI.Builder()
                .onClose(stateSnapshot -> {
                    stateSnapshot.getPlayer().sendMessage("You closed the inventory.");
                })
                .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if (stateSnapshot.getText().equalsIgnoreCase("you")) {
                        stateSnapshot.getPlayer().sendMessage("You have magical powers!");
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("Try again"));
                    }
                })
                .preventClose()
                .text("What is the meaning of life?")
                .title("Enter your answer.")
                .plugin(Main.getProvidingPlugin(Main.class))
                .open(player);
    }
}
