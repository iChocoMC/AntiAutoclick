package com.github.ichocomc.antiautoclick.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public interface AutoClickCheck {

    String prefix = ChatColor.translateAlternateColorCodes('&', "&9&lAnti-AutoClick &8: &b");
    String deco = ChatColor.DARK_GRAY + " : " + ChatColor.GRAY;

    void check(PlayerInteractEvent event, PlayerInfo info, int clickType);

    default void reportMessage(String format) {
        Bukkit.getServer().getConsoleSender().sendMessage(format);
    }
}