package com.venned.simpletoons.task;

import com.venned.simpletoons.Main;
import com.venned.simpletoons.build.PlayerTon;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AgeUpdater extends BukkitRunnable {

    @Override
    public void run() {
        for (PlayerTon toon : Main.getInstance().getTonManager().getPlayerTons()) {
            int currentAge = toon.getAge();
            toon.setAge(currentAge + 1);

            if (Bukkit.getPlayer(toon.getOwner_uuid()) != null) {
                Bukkit.getPlayer(toon.getOwner_uuid())
                        .sendMessage("Your Toon has aged by 1 year automatically.");
            }
        }
        Bukkit.getLogger().info("All Toons have been aged by 1 year.");
    }
}
