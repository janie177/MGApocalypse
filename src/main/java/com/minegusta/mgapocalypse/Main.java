package com.minegusta.mgapocalypse;

import com.minegusta.mgapocalypse.Tasks.*;
import com.minegusta.mgapocalypse.commands.BreakCommand;
import com.minegusta.mgapocalypse.commands.MGACommand;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import com.minegusta.mgapocalypse.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    public static Plugin PLUGIN;
    public static int SAVETASK, BLEEDTASK, DISEASETASK, DRINKTASK, BROADCASTTASK;
    public static boolean WG_ENABLED = false;
    public static boolean TAGAPI_ENABLED = false;

    @Override
    public void onEnable()
    {
        //Set Plugin
        PLUGIN = this;


        //Config files
        SavedLocationsManager.createOrLoadLocationsFile(PLUGIN);
        DefaultConfig.loadConfig();


        //Listeners components
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileListener(), this);

        //Commands
        getCommand("mga").setExecutor(new MGACommand());
        getCommand("break").setExecutor(new BreakCommand());


        //Tasks
        SAVETASK = SaveTask.start();
        BLEEDTASK = DOTTask.bleedingTask();
        DISEASETASK = DOTTask.diseaseTask();
        DRINKTASK = DrinkTask.start();
        BROADCASTTASK = BroadCastTask.start();
        SpawnTask.start();

        //Worldguard enabled
        if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard"))WG_ENABLED = true;
        if(Bukkit.getPluginManager().isPluginEnabled("TagAPI"))
        {
            TAGAPI_ENABLED = true;
            Bukkit.getPluginManager().registerEvents(new TagListener(), this);
        }
    }


    @Override
    public void onDisable()
    {
        //Cancel Tasks
        Bukkit.getScheduler().cancelTask(SAVETASK);
        Bukkit.getScheduler().cancelTask(BLEEDTASK);
        Bukkit.getScheduler().cancelTask(DISEASETASK);
        Bukkit.getScheduler().cancelTask(DRINKTASK);
        Bukkit.getScheduler().cancelTask(BROADCASTTASK);
        SpawnTask.stop();
    }
}
