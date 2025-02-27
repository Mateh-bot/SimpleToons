package com.venned.simpletoons.professions.blacksmith.lockpicks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BlacksmithLockpicksListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.DARK_GRAY + "Lockpicks Recipe")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
        String recipeName = event.getCurrentItem().getItemMeta().getDisplayName();
        recipeName = ChatColor.stripColor(recipeName);
        Player player = (Player) event.getWhoClicked();
        player.sendMessage(ChatColor.GREEN + "Selected recipe: " + recipeName);
    }
}
