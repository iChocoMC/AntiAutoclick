package com.github.ichocomc.antiautoclick.listeners;

import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.checks.ClickLimit;
import com.github.ichocomc.antiautoclick.checks.DelayCheck;
import com.github.ichocomc.antiautoclick.interfaces.AutoClickCheck;
import com.github.ichocomc.antiautoclick.interfaces.PlayerInfoStorage;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;
import com.github.ichocomc.antiautoclick.utils.ReportsUtil;

public class PlayerInteractListener implements Listener {

    private final PlayerInfoStorage collection;
    private final AutoClickCheck[] checks;
    private final ReportsUtil reports;

    public PlayerInteractListener(FileConfiguration config, PlayerInfoStorage newCollection) {
        reports = new ReportsUtil(config);
        collection = newCollection;
        checks = new AutoClickCheck[] {
            new ClickLimit(config), new DelayCheck(config)
        };
    }

    @EventHandler
    public void check(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            return;
        }

        PlayerInfo info = collection.get(event.getPlayer());
        int clickType = 
            event.getAction() == Action.LEFT_CLICK_AIR 
            || event.getAction() == Action.LEFT_CLICK_BLOCK 
                ? 0 
                : 1;

        info.addByte(clickType);

        for (AutoClickCheck autoClickCheck : checks) {
            autoClickCheck.check(event, info, clickType);
        }

        if (reports.reportsLimit(info.getByte(2))) {
            reports.executeCommand(event.getPlayer());
        }
    }
}