package com.example.craft;

import com.example.craft.listeners.EntitySpawnHandler;
import com.example.craft.listeners.PlayerEventHandler;
import com.example.craft.commands.CatRaveCommand;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Test.
 */
public class App extends JavaPlugin {
    @Override
    /**
     * Called when the plugin is enabled.
     */
    public void onEnable() {
        getLogger().info("Demo plugin has started!");
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new EntitySpawnHandler(), this);
        pluginManager.registerEvents(new PlayerEventHandler(), this);

        // Register commands
        this.getCommand("catrave").setExecutor(new CatRaveCommand(this));
    }

    @Override
    /**
     * Called when the plugin is disabled.
     */
    public void onDisable() {
        getLogger().info("Demo plugin has stopped :(");
    }
}
