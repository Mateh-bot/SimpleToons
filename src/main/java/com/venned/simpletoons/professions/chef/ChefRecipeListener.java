package com.venned.simpletoons.professions.chef;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.professions.Profession;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Optional;
import java.util.Random;

public class ChefRecipeListener implements Listener {

    private static final Random random = new Random();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.DARK_GRAY + "Chef Cooking (Smoker)"))
            return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta())
            return;
        String recipeName = event.getCurrentItem().getItemMeta().getDisplayName();
        recipeName = ChatColor.stripColor(recipeName);
        Player player = (Player) event.getWhoClicked();

        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();
        int chefLevel = 1;
        for (Profession prof : toon.getAllProfessions().values()) {
            if (prof.getName().equalsIgnoreCase("Chef") && prof instanceof Chef) {
                chefLevel = prof.getLevel();
                break;
            }
        }

        double chanceFor5Star = (chefLevel - 1) / 99.0;
        int stars;
        if (random.nextDouble() < chanceFor5Star) {
            stars = 5;
        } else {
            stars = random.nextInt(4) + 1;
        }
        double baseSaturation = 0;
        String lower = recipeName.toLowerCase();
        if (lower.contains("bread"))
            baseSaturation = 4;
        else if (lower.contains("steak"))
            baseSaturation = 8;
        else
            baseSaturation = 5;
        double finalSaturation = baseSaturation + (stars - 1) * 0.5;
        player.sendMessage(ChatColor.GREEN + "You cooked " + recipeName + " with a " + stars +
                "-star rating. Final saturation: " + finalSaturation);
    }
}
