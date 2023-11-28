package com.example.craft;

import com.example.craft.listeners.EntitySpawnHandler;
import com.example.craft.listeners.PlayerEventHandler;

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
    }

    @Override
    /**
     * Called when the plugin is disabled.
     */
    public void onDisable() {
        getLogger().info("Demo plugin has stopped :(");
    }
}
