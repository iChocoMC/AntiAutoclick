package com.github.ichocomc.antiautoclick.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.ichocomc.antiautoclick.interfaces.PlayerInfoStorage;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public class PlayerJoinAndQuitListener implements Listener {

    private final PlayerInfoStorage collection;

    public PlayerJoinAndQuitListener(PlayerInfoStorage collection) {
        this.collection = collection;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        collection.put(event.getPlayer(), new PlayerInfo());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        collection.quitPlayer(event.getPlayer());
    }
}