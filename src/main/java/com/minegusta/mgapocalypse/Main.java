package com.minegusta.mgapocalypse;

import com.minegusta.mgapocalypse.Tasks.SaveTask;
import com.minegusta.mgapocalypse.commands.BreakCommand;
import com.minegusta.mgapocalypse.commands.MGACommand;
import com.minegusta.mgapocalypse.config.DefaultConfig;
import com.minegusta.mgapocalypse.config.SavedLocationsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    public static Plugin PLUGIN;
    public static int SAVETASK;

    @Override
    public void onEnable()
    {
        //Set Plugin
        PLUGIN = this;


        //Config files
        SavedLocationsManager.createOrLoadLocationsFile(PLUGIN);
        DefaultConfig.loadConfig();


        //Listeners components


        //Commands
        getCommand("mga").setExecutor(new MGACommand());
        getCommand("break").setExecutor(new BreakCommand());


        //Tasks
        SAVETASK = SaveTask.start();


    }


    @Override
    public void onDisable()
    {
        //Cancel Tasks
        Bukkit.getScheduler().cancelTask(SAVETASK);

    }
}
