package com.venned.simpletoons.gui;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.thread.GUIExecutor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CreationCharacterGUI implements Listener {
    private final Map<Player, PlayerTon> playerCreation;
    public List<String> culturesList = Arrays.asList("Cristiano", "bicho", "tilin");
    public List<String> genderList = Arrays.asList("Male", "Female", "Bi");

    public CreationCharacterGUI() {
        this.playerCreation = new HashMap<>();
    }

    public void init(Player player) {
        PlayerTon playerTon = new PlayerTon(player.getUniqueId());
        playerTon.setAge(0);
        playerCreation.put(player, playerTon);

        new AnvilGUI.Builder()
                .onClose(stateSnapshot -> {
                    Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                        PlayerTon ton = playerCreation.get(player);
                        if (ton != null) {
                            if (ton.getName() == null) {
                                init(player);
                            }
                        }
                    });
                })
                .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }
                    return Collections.singletonList(AnvilGUI.ResponseAction.run(
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    PlayerTon ton = playerCreation.get(player);
                                    ton.setName(stateSnapshot.getText());
                                    age(player);
                                }
                            }
                    ));
                })
                .text("Your name?")
                .title("Enter your name")
                .plugin(Main.getProvidingPlugin(Main.class))
                .open(player);
    }

    void age(Player player) {
        PlayerTon playerTon = playerCreation.get(player);
        if (playerTon != null) {
            new AnvilGUI.Builder()
                    .onClose(stateSnapshot -> {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                PlayerTon ton = playerCreation.get(player);
                                if (ton != null) {
                                    if (ton.getAge() == 0) {
                                        age(player);
                                    }
                                }
                            }
                        }.runTask(Main.getInstance());
                    })
                    .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                        if (slot != AnvilGUI.Slot.OUTPUT) {
                            return Collections.emptyList();
                        }

                        try {
                            int age = Integer.parseInt(stateSnapshot.getText());
                            PlayerTon ton = playerCreation.get(player);
                            ton.setAge(age);
                            cultures(player);
                            return Collections.singletonList(AnvilGUI.ResponseAction.close());
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Please enter a valid number for the age..");
                            ;
                            return Collections.singletonList(AnvilGUI.ResponseAction.close());
                        }
                    })
                    .text("Your age?")
                    .title("Enter your age")
                    .plugin(Main.getProvidingPlugin(Main.class))
                    .mainThreadExecutor(new GUIExecutor())
                    .open(player);
        }
    }

    void cultures(Player player) {
        PlayerTon playerTon = playerCreation.get(player);
        if (playerTon != null) {
            player.sendMessage(ChatColor.YELLOW + "Please select a culture by clicking on the chat:");

            for (String culture : culturesList) {
                TextComponent message = new TextComponent(ChatColor.AQUA + "- " + culture);
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/selectculture " + culture));
                player.spigot().sendMessage(message);
            }
            Bukkit.getPluginCommand("selectculture").setExecutor((sender, command, label, args) -> {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    PlayerTon ton = playerCreation.get(player);
                    if (ton == null) return true;
                    if (ton.getCulture() != null) {
                        return true;
                    }

                    if (args.length > 0) {
                        String selectedCulture = args[0];
                        if (culturesList.contains(selectedCulture)) {
                            playerTon.setCulture(selectedCulture);
                            p.sendMessage(ChatColor.GREEN + "You have selected the culture: " + selectedCulture);
                            genders(player);
                        } else {
                            p.sendMessage(ChatColor.RED + "Cultura no válida. Intenta nuevamente.");
                        }
                    }
                }
                return true;
            });
        }
    }

    void genders(Player player) {
        PlayerTon playerTon = playerCreation.get(player);
        if (playerTon != null) {
            player.sendMessage(ChatColor.YELLOW + "Please select a Gender by clicking on the chat:");

            for (String culture : genderList) {
                TextComponent message = new TextComponent(ChatColor.AQUA + "- " + culture);
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/selectgender " + culture));
                player.spigot().sendMessage(message);
            }

            Bukkit.getPluginCommand("selectgender").setExecutor((sender, command, label, args) -> {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    PlayerTon ton = playerCreation.get(player);
                    if (ton == null) return true;
                    if (ton.getGender() != null) {
                        return true;
                    }
                    if (args.length > 0) {
                        String selectedGender = args[0];
                        if (genderList.contains(selectedGender)) {
                            playerTon.setGender(selectedGender);
                            p.sendMessage(ChatColor.GREEN + "You have selected the Gender: " + selectedGender);
                            Main.getInstance().getMapUtils().getInDescription().add(player);
                            player.sendMessage(ChatColor.YELLOW + "Please enter a description of your character:");
                        } else {
                            p.sendMessage(ChatColor.RED + "Cultura no válida. Intenta nuevamente.");
                        }
                    }
                }
                return true;
            });
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (Main.getInstance().getMapUtils().getInDescription().contains(event.getPlayer())) {
            PlayerTon ton = playerCreation.get(event.getPlayer());
            if (ton == null) return;
            ton.setDescription(event.getMessage());
            ton.setRace("human");
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your description was established");
            Main.getInstance().getMapUtils().getInDescription().remove(event.getPlayer());
            event.setCancelled(true);

            Main.getInstance().getTonManager().getPlayerTons().add(ton);
        }
    }
}
