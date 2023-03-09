package com.github.ichocomc.antiautoclick.checks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.ichocomc.antiautoclick.interfaces.AutoClickCheck;
import com.github.ichocomc.antiautoclick.utils.PlayerInfo;

public class ClickLimit implements AutoClickCheck {

    private final byte alertIn;

    public ClickLimit(FileConfiguration config) {
        alertIn = (byte)config.getInt("cps.alert-in");
    }

    @Override
    public void check(PlayerInteractEvent event, PlayerInfo info, int clickType) {
        if (info.getByte(clickType) >= alertIn) {
            info.addByte(2); // Add new report
        }
    }
}