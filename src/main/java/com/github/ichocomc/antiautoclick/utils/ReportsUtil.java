package com.github.ichocomc.antiautoclick.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReportsUtil {

    private final byte maxReports;
    private final String command;

    public ReportsUtil(FileConfiguration config) {
        maxReports = (byte)config.getInt("reports.max");
        command = config.getString("reports.command-on-max");
    }

    public boolean reportsLimit(byte reports) {
        return reports >= maxReports;
    }

    public void executeCommand(Player player) {
        Bukkit.getServer().dispatchCommand(
            Bukkit.getServer().getConsoleSender(),
            command.replace("%player%", player.getDisplayName()));
    }
}