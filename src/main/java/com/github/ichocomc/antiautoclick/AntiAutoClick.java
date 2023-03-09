package com.github.ichocomc.antiautoclick;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.ichocomc.antiautoclick.checks.DelayCheck;
import com.github.ichocomc.antiautoclick.interfaces.PlayerInfoStorage;
import com.github.ichocomc.antiautoclick.listeners.PlayerInteractListener;
import com.github.ichocomc.antiautoclick.listeners.PlayerJoinAndQuitListener;
import com.github.ichocomc.antiautoclick.storage.MapStorage;

public class AntiAutoClick extends JavaPlugin {
    
    @Override
    public void onEnable() {

        this.saveDefaultConfig(); // Create plugin folder and config.yml

        PlayerInfoStorage storage = new MapStorage();

        // Start listeners
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerInteractListener(getConfig(), storage), this);
        pluginManager.registerEvents(new PlayerJoinAndQuitListener(storage), this);

        // Tasks for reset player info
        BukkitScheduler scheduler = getServer().getScheduler();
        int seconds = getConfig().getInt("reports.reset-seconds");

        scheduler.runTaskTimer(this, () -> storage.resetReports(), 0, seconds);
        scheduler.runTaskTimer(this, () -> {
            storage.reset();
            DelayCheck.resetTime();
        }, 0, 20);
    }
}