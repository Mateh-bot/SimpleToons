package com.venned.simpletoons.thievery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ThieveryGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getView().getTopInventory();
        InventoryHolder holder = inv.getHolder();
        if (!(holder instanceof PickpocketInventoryHolder)) {
            return;
        }
        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;

        Player thief = (Player) event.getWhoClicked();
        PickpocketInventoryHolder pickHolder = (PickpocketInventoryHolder) holder;
        Player target = Bukkit.getPlayer(pickHolder.getTargetUUID());
        if (target == null) {
            thief.sendMessage(ChatColor.RED + "Target player is no longer online.");
            thief.closeInventory();
            return;
        }

        boolean removed = false;
        for (int i = 0; i < target.getInventory().getSize(); i++) {
            ItemStack item = target.getInventory().getItem(i);
            if (item != null && item.isSimilar(clickedItem)) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    target.getInventory().setItem(i, null);
                }
                removed = true;
                break;
            }
        }
        if (removed) {
            thief.getInventory().addItem(clickedItem);
            thief.sendMessage(ChatColor.GREEN + "You stole: " + clickedItem.getType().name());
        } else {
            thief.sendMessage(ChatColor.RED + "Error: Could not remove the item from the target's inventory.");
        }
        thief.closeInventory();
    }
}
