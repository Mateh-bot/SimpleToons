package com.venned.simpletoons.professions.farmer;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.build.PlayerTon;
import com.venned.simpletoons.professions.Profession;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

public class FarmerListener implements Listener {
    private static final Random random = new Random();
    private static final Material[] CROP_MATERIALS = {
            Material.WHEAT,
            Material.CARROTS,
            Material.POTATOES,
            Material.BEETROOTS,
            Material.PUMPKIN_STEM,
            Material.MELON_STEM
    };

    @EventHandler
    public void onHoeUse(PlayerInteractEvent event) {
        if (!event.getAction().toString().contains("RIGHT_CLICK"))
            return;

        Block block = event.getClickedBlock();
        if (block == null || !isCrop(block))
            return;

        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem == null || !isHoe(heldItem.getType()))
            return;

        Optional<Profession> farmerOpt = getFarmerProfession(player);
        if (!farmerOpt.isPresent())
            return;

        Optional<Ageable> cropOpt = getAgeableCrop(block);
        if (!cropOpt.isPresent())
            return;

        Ageable crop = cropOpt.get();
        if (crop.getAge() >= crop.getMaximumAge())
            return;

        event.setCancelled(true);
        crop.setAge(crop.getMaximumAge());
        block.setBlockData(crop);
        player.sendMessage("Your crop has grown instantly!");
        player.playSound(player.getLocation(), Sound.ITEM_BONE_MEAL_USE, 1.0f, 1.0f);

        farmerOpt.get().grantExperience(player, 10);
        reduceDurability(heldItem, player);
    }

    @EventHandler
    public void onBoneMealUse(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.BONE_MEAL) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Bone meal is disabled. Only Farmer can grow crops using a hoe.");
        }
    }

    @EventHandler
    public void onSeedPlant(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (!isCrop(block))
            return;

        Player player = event.getPlayer();
        Optional<Profession> farmerOpt = getFarmerProfession(player);
        farmerOpt.ifPresent(farmer -> farmer.grantExperience(player, 1));
    }

    @EventHandler
    public void onCropBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Optional<Ageable> cropOpt = getAgeableCrop(block);
        if (!cropOpt.isPresent())
            return;

        Ageable crop = cropOpt.get();
        if (crop.getAge() < crop.getMaximumAge())
            return;

        Player player = event.getPlayer();
        Optional<Profession> farmerOpt = getFarmerProfession(player);
        if (!farmerOpt.isPresent())
            return;

        event.setDropItems(false);
        Profession farmer = farmerOpt.get();
        farmer.grantExperience(player, 15);

        double chance = Math.min(1.0, farmer.getLevel() / 100.0);
        boolean doubleYield = random.nextDouble() < chance;
        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

        for (ItemStack drop : drops) {
            block.getWorld().dropItemNaturally(block.getLocation(), drop);
            if (doubleYield) {
                ItemStack doubleDrop = drop.clone();
                doubleDrop.setAmount(drop.getAmount() * 2);
                block.getWorld().dropItemNaturally(block.getLocation(), doubleDrop);
            }
        }
    }

    private void reduceDurability(ItemStack tool, Player player) {
        if (tool.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) tool.getItemMeta();
            int newDamage = damageable.getDamage() + 3;
            int maxDurability = tool.getType().getMaxDurability();
            if (newDamage >= maxDurability) {
                player.getInventory().setItemInMainHand(null);
                player.sendMessage("Your hoe has broken.");
                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.0f, 1.0f);
            } else {
                damageable.setDamage(newDamage);
                tool.setItemMeta(damageable);
            }
        }
    }

    private boolean isHoe(Material mat) {
        switch (mat) {
            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case DIAMOND_HOE:
            case NETHERITE_HOE:
                return true;
            default:
                return false;
        }
    }

    private boolean isCrop(Block block) {
        return Arrays.asList(CROP_MATERIALS).contains(block.getType());
    }

    private Optional<Ageable> getAgeableCrop(Block block) {
        if (block.getBlockData() instanceof Ageable) {
            return Optional.of((Ageable) block.getBlockData());
        }
        return Optional.empty();
    }

    private Optional<PlayerTon> getPlayerToon(Player player) {
        return Main.getInstance().getPlayerSelectSet().stream()
                .filter(ps -> ps.getPlayer().equals(player))
                .map(PlayerSelect::getToon)
                .findFirst();
    }

    private Optional<Profession> getFarmerProfession(Player player) {
        return getPlayerToon(player).map(toon -> toon.getProfession("Farmer"));
    }
}
