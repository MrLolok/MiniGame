package org.unina.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;
import org.unina.minecraft.game.TargetShootingService;

public final class MiniGamePlugin extends JavaPlugin {
    private static MiniGamePlugin instance;
    private ITargetShootingService targetShootingService;

    public static MiniGamePlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        targetShootingService = new TargetShootingService(this);
        targetShootingService.enable();
        Bukkit.getConsoleSender().sendMessage("§aPlugin avviato con successo.", "§aVersione: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        targetShootingService.disable();
        Bukkit.getConsoleSender().sendMessage("§aPlugin disattivato con successo.");
    }
}
