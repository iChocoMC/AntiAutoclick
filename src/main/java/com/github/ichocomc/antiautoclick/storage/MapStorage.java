package com.github.ichocomc.antiautoclick.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.ichocomc.antiautoclick.interfaces.PlayerInfoStorage;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public class MapStorage implements PlayerInfoStorage {

    private final Map<UUID, PlayerInfo> infos = new HashMap<>();

    @Override
    public void put(Player player, PlayerInfo info) {
        infos.put(player.getUniqueId(), info);
    }

    @Override
    public PlayerInfo get(Player player) {
        return infos.get(player.getUniqueId());
    }

    @Override
    public void quitPlayer(Player player) {
        infos.remove(player.getUniqueId());
    }

    @Override
    public void reset() {
        Collection<PlayerInfo> collection = infos.values();
        for (PlayerInfo info : collection) {
            info.reset();
        }
    }

    @Override
    public void resetReports() {
        Collection<PlayerInfo> collection = infos.values();
        for (PlayerInfo info : collection) {
            info.resetReports();
        }
    }
}