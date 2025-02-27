package com.venned.simpletoons.professions.blacksmith.tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BlacksmithToolsMineralListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.DARK_GRAY + "Tools - Choose Mineral")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
        String mineral = event.getCurrentItem().getItemMeta().getDisplayName();
        mineral = ChatColor.stripColor(mineral);
        Player player = (Player) event.getWhoClicked();
        BlacksmithToolsRecipeGUI.openRecipeGUI(player, mineral);
    }
}
