package com.happiergore.wolves_armors;

import com.happiergore.menusapi.Events;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Events.OnClickTamedWolf;
import com.happiergore.wolves_armors.Events.OnWolfDamaged;
import com.happiergore.wolves_armors.Events.OnWolfDeath;
import com.happiergore.wolves_armors.Events.OnDragItem;
import com.happiergore.wolves_armors.Events.OnItemInteract;
import com.happiergore.wolves_armors.Items.Config;
import com.happiergore.wolves_armors.Utils.ConsoleUtils;
import com.happiergore.wolves_armors.Utils.Metrics;
import com.happiergore.wolves_armors.Utils.UpdateChecker;
import com.happiergore.wolves_armors.Utils.YAML.YamlJBDC;
import com.happiergore.wolves_armors.cmds.Commands;
import com.happiergore.wolves_armors.cmds.argsComplete;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author HappierGore
 */
public class main extends JavaPlugin implements Listener {

    public static boolean debugMode;
    public static ConsoleUtils console;
    private String sversion;
    public Metrics metrics;
    public UpdateChecker updateChecker;
    public static YamlJBDC wolvesYAML;
    public static YamlJBDC chestData;
    public static Map<String, WolfData> wolvesData = new HashMap<>();

    public static FileConfiguration configYML;

    @Override
    public void onEnable() {

        configYML = getConfig();
        console = new ConsoleUtils();
        debugMode = getConfig().getBoolean("debug_mode");
        //updateChecker = new UpdateChecker(100948);

        setupManager();

        wolvesYAML = new YamlJBDC(this.getDataFolder().getAbsolutePath(), "Wolves_data", false);
        chestData = new YamlJBDC(this.getDataFolder().getAbsolutePath(), "ChestData", false);
        Config.reloadConfig(false);
        //Metrics
        //int pluginId = 15538; // <-- Replace with the id of your plugin!
        //metrics = new Metrics(this, pluginId);
        registerCommands();

        //Crear config.yml en caso de que no exista
        saveDefaultConfig();
        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
        Config.saveData();
    }

    //***********************
    //        EVENTOS
    //***********************
    //***********************
    //        Helper
    //***********************
    private void registerCommands() {
        this.getCommand("wolvesarmor").setExecutor(new Commands());
        this.getCommand("wolvesarmor").setTabCompleter(new argsComplete());
    }

    private void successMessage(String version) {
        List<String> msg = new ArrayList<>();
        msg.add("&b" + this.getName() + " &a has been loaded successfully");
        msg.add("");
        msg.add("&9Creator: &aHappierGore");
        msg.add("&9Discord: &aHappierGore#1197");
        msg.add("&9Support: &ahttps://discord.gg/ZKy5eVPxW5");
        msg.add("&9Resources: &ahttps://www.spigotmc.org/resources/authors/happiergore.1046101/");
        msg.add("&9Server version: &a1." + version);
        msg.add("");
        msg.add("&9Debug mode: &a" + debugMode);

        msg.add("");

//        switch (updateChecker.getUpdateCheckResult()) {
//            case OUT_DATED:
//                msg.add("&6There's a new update available:");
//                msg.add("&9New Version: &a" + updateChecker.latestVersion);
//                msg.add("&9Your version is: &c" + updateChecker.currentVersion);
//                msg.add("&eDownload it here: " + "&r\n&ahttps://www.spigotmc.org/resources/stop-vine-growing.100948/");
//                break;
//            case UNRELEASED:
//                msg.add("&6Your'e using beta / unreleased version: " + updateChecker.currentVersion);
//                msg.add("&eThe latest version released is: &a" + updateChecker.latestVersion);
//                break;
//            case UP_TO_DATE:
//                msg.add("&eYour'e using the latest version: &a" + updateChecker.currentVersion);
//                break;
//            case NO_RESULT:
//                msg.add("&cThere was an error when trying to get the versions. Skipping...");
//        }
        console.loggerMsg(msg);
    }

    @EventHandler
    public void onClickEntity(PlayerInteractAtEntityEvent e) {
        OnClickTamedWolf.listen(e);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageEvent e) {
        OnWolfDamaged.listen(e);
    }

    @EventHandler
    public void onDeathEntity(EntityDeathEvent e) {
        OnWolfDeath.listen(e);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        OnDragItem.listen(e);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        OnItemInteract.listen(e);
    }

    private boolean setupManager() {
        sversion = "N/A";
        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (Exception e) {
            console.warnMsg(this.getName() + " wasn't able to get your client version.\nWill start with default version...");
            sversion = "v1_19";
        }
        successMessage(sversion);

        return true;
    }
}
