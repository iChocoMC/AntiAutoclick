package com.github.ichocomc.antiautoclick.interfaces;

import org.bukkit.entity.Player;

import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public interface PlayerInfoStorage {
    void put(Player player, PlayerInfo info);
    PlayerInfo get(Player player);
    void quitPlayer(Player player);

    void reset();
    void resetReports();
}
