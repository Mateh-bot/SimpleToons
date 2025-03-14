package com.venned.simpletoons;

import com.venned.simpletoons.build.PlayerSelect;
import com.venned.simpletoons.chat.RoleplayChatListener;
import com.venned.simpletoons.commands.*;
import com.venned.simpletoons.data.SQLService;
import com.venned.simpletoons.data.TonManager;
import com.venned.simpletoons.gui.CreationCharacterGUI;
import com.venned.simpletoons.listeners.ToonNameListener;
import com.venned.simpletoons.listeners.ToonNametagListener;
import com.venned.simpletoons.listeners.data.CharacterLoadListener;
import com.venned.simpletoons.listeners.data.PlayerJoinDataListener;
import com.venned.simpletoons.manager.RaceManager;
import com.venned.simpletoons.maps.MapUtils;
import com.venned.simpletoons.professions.ProfessionCommand;
import com.venned.simpletoons.professions.ProfessionWorkStationListener;
import com.venned.simpletoons.professions.blacksmith.armor.BlacksmithArmorMineralListener;
import com.venned.simpletoons.professions.blacksmith.armor.BlacksmithArmorRecipeListener;
import com.venned.simpletoons.professions.blacksmith.ingots.BlacksmithIngotSmeltingListener;
import com.venned.simpletoons.professions.blacksmith.lockpicks.BlacksmithLockpicksListener;
import com.venned.simpletoons.professions.blacksmith.tools.BlacksmithToolsMineralListener;
import com.venned.simpletoons.professions.blacksmith.tools.BlacksmithToolsRecipeListener;
import com.venned.simpletoons.professions.blacksmith.weapons.BlacksmithWeaponsMineralListener;
import com.venned.simpletoons.professions.blacksmith.weapons.BlacksmithWeaponsRecipeListener;
import com.venned.simpletoons.professions.blacksmith.workstation.BlacksmithWorkStationListener;
import com.venned.simpletoons.professions.chef.workstation.ChefWorkStationListener;
import com.venned.simpletoons.professions.farmer.FarmerListener;
import com.venned.simpletoons.professions.fisherman.FishermanFishListener;
import com.venned.simpletoons.professions.husbander.HusbanderBreedListener;
import com.venned.simpletoons.professions.husbander.HusbanderShearChickenListener;
import com.venned.simpletoons.professions.husbander.HusbanderShearSheepListener;
import com.venned.simpletoons.professions.leatherworker.armorBase.LeatherworkerArmorBaseListener;
import com.venned.simpletoons.professions.leatherworker.banners.LeatherworkerBannerListener;
import com.venned.simpletoons.professions.leatherworker.brigandineArmor.LeatherworkerBrigandineListener;
import com.venned.simpletoons.professions.leatherworker.horseArmor.LeatherworkerHorseArmorListener;
import com.venned.simpletoons.professions.leatherworker.lead.LeatherworkerLeadListener;
import com.venned.simpletoons.professions.leatherworker.saddle.LeatherworkerSaddleListener;
import com.venned.simpletoons.professions.leatherworker.workstation.LeatherworkerWorkStationListener;
import com.venned.simpletoons.professions.mason.MasonWorkStationListener;
import com.venned.simpletoons.professions.woodcutter.workstation.WoodcutterWorkStationListener;
import com.venned.simpletoons.task.AgeUpdater;
import com.venned.simpletoons.task.ToonsActive;
import com.venned.simpletoons.thievery.ThieveryGUIListener;
import com.venned.simpletoons.thievery.ThieveryManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public final class Main extends JavaPlugin {
    SQLService service;
    TonManager tonManager;
    Set<PlayerSelect> playerSelectSet;
    static Main instance;
    CreationCharacterGUI creationCharacterGUI;
    MapUtils mapUtils;
    RaceManager raceManager;
    ToonCommand toonCommand;
    ThieveryManager thieveryManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        createPluginFolder();

        raceManager = new RaceManager(this);
        mapUtils = new MapUtils();
        service = new SQLService(this);
        tonManager = new TonManager(service);
        playerSelectSet = new HashSet<>();
        toonCommand = new ToonCommand();
        thieveryManager = new ThieveryManager();

        createGUI();

        loadListeners();
        loadCommands();
        loadCompleters();

        new ToonsActive().runTaskTimer(this, 20, 20);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendUnicodeActionBar(player, 3); // 5 filas de "᭢"
            }
        }, 0L, 40L); // Se actualiza cada 2 segundos (40 ticks)

        scheduleAgeUpdater();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ArmorStand hologram = ToonNametagListener.getHologram(player.getUniqueId());
                if (hologram != null && !hologram.isDead()) {
                    player.getWorld().getPlayers().forEach(p -> {
                    });
                    hologram.teleport(player.getLocation().add(0, 2.0, 0));
                }
            }
        }, 1L, 1L);
    }

    private void sendUnicodeActionBar(Player player, int filas) {
        String actionBar = "";
        int maxLength = 40;  // Máximo de caracteres por fila (dependiendo de tu configuración de ancho de ActionBar)

        for (int i = 0; i < filas; i++) {
            // Agregar espacios para centrar cada fila
            String text = "᭢᭣᭤᭥";  // Tu texto (esto lo puedes cambiar por el símbolo de pregunta que mencionas)
            int spacesToAdd = (maxLength - text.length()) / 2; // Calcula los espacios necesarios para centrar

            String centeredText = " ".repeat(spacesToAdd) + text; // Genera el texto centrado

            // Añadir cada fila al actionBar
            actionBar += centeredText + "\n";
        }
        // Enviar el mensaje del ActionBar
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBar.trim()));
    }

    @Override
    public void onDisable() {
        try {
            service.saveAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        service.closeConnection();

        ToonNametagListener.removeAllHolograms();
    }

    void loadCommands() {
        getCommand("race").setExecutor(new RaceCommand());
        getCommand("toon").setExecutor(toonCommand);
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("sit").setExecutor(new SitCommand());
        getCommand("carry").setExecutor(new CarryCommand());
        getCommand("profession").setExecutor(new ProfessionCommand());
        getCommand("lockpick").setExecutor(new LockpickCommand(thieveryManager));
        getCommand("pickpocket").setExecutor(new PickpocketCommand(thieveryManager));
    }

    void loadListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinDataListener(tonManager, creationCharacterGUI), this);
        getServer().getPluginManager().registerEvents(creationCharacterGUI, this);
        getServer().getPluginManager().registerEvents(new CharacterLoadListener(), this);
        getServer().getPluginManager().registerEvents(new MenuCommand(), this);
        getServer().getPluginManager().registerEvents(new SitCommand(), this);
        getServer().getPluginManager().registerEvents(new RoleplayChatListener(tonManager), this);
        getServer().getPluginManager().registerEvents(new ToonNameListener(), this);
        getServer().getPluginManager().registerEvents(new ToonNametagListener(), this);
        getServer().getPluginManager().registerEvents(new ProfessionWorkStationListener(), this);

        getServer().getPluginManager().registerEvents(new BlacksmithWorkStationListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithWeaponsMineralListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithWeaponsRecipeListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithArmorMineralListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithArmorRecipeListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithToolsMineralListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithToolsRecipeListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithLockpicksListener(), this);
        getServer().getPluginManager().registerEvents(new BlacksmithIngotSmeltingListener(), this);

        getServer().getPluginManager().registerEvents(new MasonWorkStationListener(), this);

        getServer().getPluginManager().registerEvents(new LeatherworkerWorkStationListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerSaddleListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerLeadListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerHorseArmorListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerArmorBaseListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerBrigandineListener(), this);
        getServer().getPluginManager().registerEvents(new LeatherworkerBannerListener(), this);

        getServer().getPluginManager().registerEvents(new HusbanderBreedListener(), this);
        getServer().getPluginManager().registerEvents(new HusbanderShearChickenListener(), this);
        getServer().getPluginManager().registerEvents(new HusbanderShearSheepListener(), this);

        getServer().getPluginManager().registerEvents(new WoodcutterWorkStationListener(), this);

        getServer().getPluginManager().registerEvents(new FarmerListener(), this);

        getServer().getPluginManager().registerEvents(new FishermanFishListener(), this);

        getServer().getPluginManager().registerEvents(new ChefWorkStationListener(), this);

        getServer().getPluginManager().registerEvents(new ThieveryGUIListener(), this);
    }

    void loadCompleters() {
        getCommand("toon").setTabCompleter(toonCommand);
    }

    void createGUI() {
        creationCharacterGUI = new CreationCharacterGUI();
    }

    public static Main getInstance() {
        return instance;
    }

    public SQLService getService() {
        return service;
    }

    public TonManager getTonManager() {
        return tonManager;
    }

    public CreationCharacterGUI getCreationCharacterGUI() {
        return creationCharacterGUI;
    }

    public MapUtils getMapUtils() {
        return mapUtils;
    }

    public RaceManager getRaceManager() {
        return raceManager;
    }

    public Set<PlayerSelect> getPlayerSelectSet() {
        return playerSelectSet;
    }

    public ThieveryManager getThieveryManager() {
        return thieveryManager;
    }

    private void createPluginFolder() {
        File pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }
    }

    private void scheduleAgeUpdater() {
        ZoneId zoneId = ZoneId.of("America/New_York");
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        ZonedDateTime nextSundayNoon = now.with(DayOfWeek.SUNDAY)
                .withHour(12).withMinute(0).withSecond(0).withNano(0);

        if (now.compareTo(nextSundayNoon) >= 0) {
            nextSundayNoon = nextSundayNoon.plusWeeks(1);
        }

        long delaySeconds = Duration.between(now, nextSundayNoon).getSeconds();
        long delayTicks = delaySeconds * 20; // 20 ticks per second
        long periodTicks = 7L * 24 * 60 * 60 * 20; // One week in ticks

        new AgeUpdater().runTaskTimer(this, delayTicks, periodTicks);

        getLogger().info("AgeUpdater scheduled for: " + nextSundayNoon);
    }
}
