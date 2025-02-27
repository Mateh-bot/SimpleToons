package com.venned.simpletoons.commands;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Optional;

public class MenuCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        openMenu(player);
        return true;
    }

    private void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Toon Menu");

        Optional<PlayerSelect> playerSelect = Main.getInstance().getPlayerSelectSet().stream()
                .filter(p -> p.getPlayer().equals(player))
                .findFirst();

        if (!playerSelect.isPresent()) {
            player.sendMessage(ChatColor.RED + "You don't have an active Toon.");
            return;
        }

        PlayerTon toon = playerSelect.get().getToon();

        menu.setItem(0, createItem(Material.NETHER_STAR, ChatColor.GOLD + "Website", "Click to visit website."));
        menu.setItem(1, createItem(Material.GOLD_INGOT, ChatColor.YELLOW + "Server Shop", "Click to visit shop."));
        menu.setItem(4, createItem(Material.BLAZE_ROD, ChatColor.YELLOW + "Toon Information",
                "Name: " + toon.getName(),
                "Age: " + toon.getAge(),
                "Culture: " + toon.getCulture(),
                "Gender: " + toon.getGender(),
                "Race: " + toon.getRace()));
        menu.setItem(5, createItem(Material.DRAGON_BREATH, ChatColor.LIGHT_PURPLE + "Magic Skills", "Check your magic skills."));
        menu.setItem(8, createItem(Material.MAP, ChatColor.RED + "Reports and Requests", "Access player reports.", "Ask administrators for help."));
        menu.setItem(9, createItem(Material.PLAYER_HEAD, ChatColor.GREEN + toon.getName(), "Your active Toon."));
        menu.setItem(10, createItem(Material.PLAYER_HEAD, ChatColor.GRAY + "Toon Locked", "You must unlock another Toon."));
        menu.setItem(13, createItem(Material.BREEZE_ROD, ChatColor.AQUA + "Toon Statuses", "Current statuses: " + toon.getDescription()));
        menu.setItem(14, createItem(Material.NETHERITE_SWORD, ChatColor.DARK_RED + "PvP Skills", "Check your combat skills."));
        menu.setItem(17, createItem(Material.BOOK, ChatColor.DARK_BLUE + "Command List",
                "/toon status",
                "/toon setname",
                "/toon setdesc",
                "/toon pk...",
                "More information on the website."));
        menu.setItem(18, createItem(Material.SKELETON_SKULL, ChatColor.GRAY + "Alternate Toon 1", "Slot reserved for another Toon."));
        menu.setItem(19, createItem(Material.SKELETON_SKULL, ChatColor.GRAY + "Alternate Toon 2", "Slot reserved for another Toon."));
        menu.setItem(22, createItem(Material.CRAFTING_TABLE, ChatColor.GREEN + "Profession Skills", "Check your profession skills."));
        menu.setItem(23, createItem(Material.TRIPWIRE_HOOK, ChatColor.GRAY + "Stealth Skills", "Check your thief skills."));

        player.openInventory(menu);
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Toon Menu")) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) return;

        switch (clickedItem.getType()) {
            case NETHER_STAR:
                player.sendMessage(ChatColor.YELLOW + "Opening the website...");
                player.closeInventory();
                break;
            case GOLD_INGOT:
                player.sendMessage(ChatColor.YELLOW + "Opening the store...");
                player.closeInventory();
                break;
            case MAP:
                player.sendMessage(ChatColor.RED + "Opening the reports menu...");
                player.closeInventory();
                break;
            case NETHERITE_SWORD:
                player.sendMessage(ChatColor.DARK_RED + "Showing PvP skills...");
                break;
            case DRAGON_BREATH:
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Showing magic skills...");
                break;
            case TRIPWIRE_HOOK:
                player.sendMessage(ChatColor.GRAY + "Showing stealth skills...");
                break;
            case CRAFTING_TABLE:
                player.sendMessage(ChatColor.GREEN + "Showing profession skills...");
                break;
            default:
                break;
        }
    }
}
