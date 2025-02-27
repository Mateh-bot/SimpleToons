package com.venned.simpletoons.professions.blacksmith.workstation;

import com.venned.simpletoons.professions.blacksmith.armor.BlacksmithArmorMineralGUI;
import com.venned.simpletoons.professions.blacksmith.ingots.BlacksmithIngotSmeltingGUI;
import com.venned.simpletoons.professions.blacksmith.lockpicks.BlacksmithLockpicksGUI;
import com.venned.simpletoons.professions.blacksmith.tools.BlacksmithToolsMineralGUI;
import com.venned.simpletoons.professions.blacksmith.weapons.BlacksmithWeaponsMineralGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BlacksmithWorkStationListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.DARK_GRAY + "Blacksmith WorkStation")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
        String option = event.getCurrentItem().getItemMeta().getDisplayName();
        option = ChatColor.stripColor(option);
        Player player = (Player) event.getWhoClicked();

        switch (option) {
            case "Metal Weapons":
                BlacksmithWeaponsMineralGUI.openMineralGUI(player);
                break;
            case "Metal Armor":
                BlacksmithArmorMineralGUI.openMineralGUI(player);
                break;
            case "Metal Tools":
                BlacksmithToolsMineralGUI.openMineralGUI(player);
                break;
            case "Lockpicks":
                BlacksmithLockpicksGUI.openLockpicksGUI(player);
                break;
            case "Metal Ingots":
                BlacksmithIngotSmeltingGUI.openIngotGUI(player);
                break;
            default:
                break;
        }
    }
}
