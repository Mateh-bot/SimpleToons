package com.venned.simpletoons.professions.leatherworker.workstation;

import com.venned.simpletoons.professions.leatherworker.armorBase.LeatherworkerArmorBaseGUI;
import com.venned.simpletoons.professions.leatherworker.banners.LeatherworkerBannerGUI;
import com.venned.simpletoons.professions.leatherworker.brigandineArmor.LeatherworkerBrigandineGUI;
import com.venned.simpletoons.professions.leatherworker.horseArmor.LeatherworkerHorseArmorGUI;
import com.venned.simpletoons.professions.leatherworker.lead.LeatherworkerLeadGUI;
import com.venned.simpletoons.professions.leatherworker.saddle.LeatherworkerSaddleGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LeatherworkerWorkStationListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.DARK_GRAY + "Leatherworker WorkStation")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
        String option = event.getCurrentItem().getItemMeta().getDisplayName();
        option = ChatColor.stripColor(option);
        Player player = (Player) event.getWhoClicked();

        switch (option) {
            case "Saddles":
                LeatherworkerSaddleGUI.openSaddleGUI(player);
                break;
            case "Leads":
                LeatherworkerLeadGUI.openLeadGUI(player);
                break;
            case "Horse Armor":
                LeatherworkerHorseArmorGUI.openHorseArmorGUI(player);
                break;
            case "Armor Bases":
                LeatherworkerArmorBaseGUI.openArmorBaseGUI(player);
                break;
            case "Brigandine Armor":
                LeatherworkerBrigandineGUI.openBrigandineGUI(player);
                break;
            case "Banners":
                LeatherworkerBannerGUI.openBannerGUI(player);
                break;
        }
    }
}
