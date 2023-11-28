package com.example.craft;

import com.example.craft.listeners.EntitySpawnHandler;
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
        getServer().getPluginManager().registerEvents(new EntitySpawnHandler(), this);
    }

    @Override
    /**
     * Called when the plugin is disabled.
     */
    public void onDisable() {
        getLogger().info("Demo plugin has stopped :(");
    }
}
