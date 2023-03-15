package com.github.ichocomc.antiautoclick.listeners;

import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.checks.DelayCheck;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;
import com.github.ichocomc.antiautoclick.utils.ReportsUtil;

import java.util.Map;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final Map<UUID, PlayerInfo> collection;
    private final DelayCheck delay;
    private final ReportsUtil reports;
    private final byte alertIn;

    public PlayerInteractListener(FileConfiguration config, Map<UUID, PlayerInfo> newCollection) {
        alertIn = (byte)config.getInt("cps.alert-in");
        reports = new ReportsUtil(config);
        collection = newCollection;
        delay = new DelayCheck(config);
    }

    @EventHandler
    public void check(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR
            && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        PlayerInfo info = collection.get(event.getPlayer().getUniqueId());

        info.addByte(0);
        if (info.getByte(0) > alertIn) {
            info.addByte(1);
        }

        delay.check(event, info);

        if (reports.reportsLimit(info.getByte(1))) {
            reports.executeCommand(event.getPlayer());
        }
    }
}