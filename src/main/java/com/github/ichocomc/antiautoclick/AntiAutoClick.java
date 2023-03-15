package com.github.ichocomc.antiautoclick;

import com.github.ichocomc.antiautoclick.utils.PlayerInfo;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.ichocomc.antiautoclick.checks.DelayCheck;
import com.github.ichocomc.antiautoclick.listeners.PlayerInteractListener;
import com.github.ichocomc.antiautoclick.listeners.PlayerJoinAndQuitListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiAutoClick extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig(); // Create plugin folder and config.yml

        Map<UUID, PlayerInfo> collection = new HashMap<>();

        // Start listeners
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(getConfig(), collection), this);
        pluginManager.registerEvents(new PlayerJoinAndQuitListener(collection), this);

        // Tasks for reset player info
        BukkitScheduler scheduler = getServer().getScheduler();
        int seconds = getConfig().getInt("reports.reset-seconds");

        scheduler.runTaskTimer(this, () -> collection.values().forEach(PlayerInfo::resetReports), 0, seconds);
        scheduler.runTaskTimer(this, () -> {
            collection.values().forEach(PlayerInfo::reset);
            DelayCheck.resetTime();
        }, 0, 20);
    }
}