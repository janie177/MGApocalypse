package com.minegusta.mgapocalypse;

import com.minegusta.mgapocalypse.Tasks.*;
import com.minegusta.mgapocalypse.commands.BreakCommand;
import com.minegusta.mgapocalypse.commands.InfoCommand;
import com.minegusta.mgapocalypse.commands.MGACommand;
import com.minegusta.mgapocalypse.commands.PerkCommand;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.LogoutManager;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import com.minegusta.mgapocalypse.listeners.*;
import com.minegusta.mgapocalypse.pvplog.PvpLogListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin PLUGIN;
    public static int SAVETASK, BLEEDTASK, DISEASETASK, DRINKTASK, BROADCASTTASK;
    public static boolean WG_ENABLED = false;

    @Override
    public void onEnable() {
        //Set Plugin
        PLUGIN = this;


        //Config files
        SavedLocationsManager.createOrLoadLocationsFile(PLUGIN);
        DefaultConfig.loadConfig();
        LogoutManager.createFile(PLUGIN);


        //Listeners components
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileListener(), this);
        Bukkit.getPluginManager().registerEvents(new PvpLogListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        //Commands
        getCommand("mga").setExecutor(new MGACommand());
        getCommand("break").setExecutor(new BreakCommand());
        getCommand("stats").setExecutor(new InfoCommand());
        getCommand("perk").setExecutor(new PerkCommand());


        //Tasks
        SAVETASK = SaveTask.start();
        BLEEDTASK = DOTTask.bleedingTask();
        DISEASETASK = DOTTask.diseaseTask();
        DRINKTASK = DrinkTask.start();
        BROADCASTTASK = BroadCastTask.start();
        PlayTimeTask.start();
        SpawnTask.start();

        //Worldguard enabled
        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) WG_ENABLED = true;
    }

    @Override
    public void onDisable() {
        //Cancel Tasks
        Bukkit.getScheduler().cancelTask(SAVETASK);
        Bukkit.getScheduler().cancelTask(BLEEDTASK);
        Bukkit.getScheduler().cancelTask(DISEASETASK);
        Bukkit.getScheduler().cancelTask(DRINKTASK);
        Bukkit.getScheduler().cancelTask(BROADCASTTASK);
        PlayTimeTask.stop();
        SpawnTask.stop();
    }
}
