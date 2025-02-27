package com.venned.simpletoons.professions;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.professions.blacksmith.workstation.BlacksmithWorkStationGUI;
import com.venned.simpletoons.professions.chef.workstation.ChefWorkStationGUI;
import com.venned.simpletoons.professions.leatherworker.workstation.LeatherworkerWorkStationGUI;
import com.venned.simpletoons.professions.woodcutter.workstation.WoodcutterWorkStationGUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ProfessionWorkStationListener implements Listener {

    private static class WorkStationMapping {
        String requiredProfession;
        Consumer<Player> openGuiFunction;

        public WorkStationMapping(String requiredProfession, Consumer<Player> openGuiFunction) {
            this.requiredProfession = requiredProfession;
            this.openGuiFunction = openGuiFunction;
        }
    }

    private static final Map<Material, WorkStationMapping> mappings = new HashMap<>();

    static {
        mappings.put(Material.SMITHING_TABLE, new WorkStationMapping("Blacksmith", BlacksmithWorkStationGUI::openBlacksmithGUI));
        mappings.put(Material.LOOM, new WorkStationMapping("Leatherworker", LeatherworkerWorkStationGUI::openLeatherworkerGUI));
        mappings.put(Material.FLETCHING_TABLE, new WorkStationMapping("Woodcutter", WoodcutterWorkStationGUI::openWoodcutterGUI));
        mappings.put(Material.SMOKER, new WorkStationMapping("Chef", ChefWorkStationGUI::openChefGUI));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("RIGHT_CLICK"))
            return;

        Block block = event.getClickedBlock();
        if (block == null)
            return;

        Material blockType = block.getType();
        if (!mappings.containsKey(blockType))
            return;

        WorkStationMapping mapping = mappings.get(blockType);

        Player player = event.getPlayer();
        Optional<PlayerSelect> psOpt = Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .findFirst();
        if (!psOpt.isPresent())
            return;
        PlayerTon toon = psOpt.get().getToon();

        boolean hasProfession = toon.getProfession(mapping.requiredProfession) != null;
        if (!hasProfession)
            return;

        event.setCancelled(true);
        mapping.openGuiFunction.accept(player);
    }
}
