package com.minegusta.mgapocalypse;

import com.minegusta.mgapocalypse.Tasks.DOTTask;
import com.minegusta.mgapocalypse.Tasks.SaveTask;
import com.minegusta.mgapocalypse.commands.BreakCommand;
import com.minegusta.mgapocalypse.commands.MGACommand;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import com.minegusta.mgapocalypse.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    public static Plugin PLUGIN;
    public static int SAVETASK, BLEEDTASK, DISEASETASK;

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

        //Commands
        getCommand("mga").setExecutor(new MGACommand());
        getCommand("break").setExecutor(new BreakCommand());


        //Tasks
        SAVETASK = SaveTask.start();
        BLEEDTASK = DOTTask.bleedingTask();
        DISEASETASK = DOTTask.diseaseTask();


    }


    @Override
    public void onDisable()
    {
        //Cancel Tasks
        Bukkit.getScheduler().cancelTask(SAVETASK);
        Bukkit.getScheduler().cancelTask(BLEEDTASK);
        Bukkit.getScheduler().cancelTask(DISEASETASK);
    }
}
