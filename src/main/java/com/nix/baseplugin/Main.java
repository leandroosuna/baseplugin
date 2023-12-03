package com.nix.baseplugin;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    static Main instance;

    public static final Logger LOGGER=Logger.getLogger("baseplugin");
    CommandManager commandManager;
    HUD hud;
    Particles particles;
    public void onEnable()
    {
        instance = this;

        LOGGER.info("baseplugin enabled");
        commandManager = new CommandManager();
        getCommand("bp").setExecutor(commandManager);
        getServer().getPluginManager().registerEvents(new Events(), this);
        hud = new HUD();
        Locations.Init();
        particles = new Particles();
    }

    public void onDisable()
    {
        LOGGER.info("stopping tasks");
        Bukkit.getScheduler().cancelTasks(this);
        LOGGER.info("baseplugin disabled");
    }
    public static Main getInstance(){return instance;};
}
