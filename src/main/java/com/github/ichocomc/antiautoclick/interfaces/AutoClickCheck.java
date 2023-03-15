package com.github.ichocomc.antiautoclick.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public interface AutoClickCheck {

    void check(PlayerInteractEvent event, PlayerInfo info);

    default void reportMessage(String format) {
        Bukkit.getServer().getConsoleSender().sendMessage(format);
    }
}