package com.github.ichocomc.antiautoclick.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

import java.util.Map;
import java.util.UUID;

public class PlayerJoinAndQuitListener implements Listener {

    private final Map<UUID, PlayerInfo> collection;

    public PlayerJoinAndQuitListener(Map<UUID, PlayerInfo> collection) {
        this.collection = collection;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        collection.put(event.getPlayer().getUniqueId(), new PlayerInfo());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        collection.remove(event.getPlayer().getUniqueId());
    }
}