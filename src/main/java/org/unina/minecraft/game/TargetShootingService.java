package org.unina.minecraft.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.unina.minecraft.commands.GameCommand;
import org.unina.minecraft.items.ItemBuilder;
import org.unina.minecraft.listeners.FireballExplosionListener;
import org.unina.minecraft.listeners.InventoryClickListener;
import org.unina.minecraft.listeners.PlayerConnectionListener;
import org.unina.minecraft.listeners.TargetShootingListener;

import java.util.LinkedList;
import java.util.List;

public class TargetShootingService implements ITargetShootingService {
    private final JavaPlugin plugin;
    private final List<ITargetShootingGame> games = new LinkedList<>();
    private Listener playerConnectionListener, inventoryClickListener, targetShootingListener, fireballExplosionListener;

    public TargetShootingService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerConnectionListener = new PlayerConnectionListener(this);
        this.inventoryClickListener = new InventoryClickListener(this);
        this.targetShootingListener = new TargetShootingListener(this);
        this.fireballExplosionListener = new FireballExplosionListener();
    }

    @Override
    public void enable() {
        GameCommand command = new GameCommand(this);
        plugin.getServer().getPluginManager().registerEvents(playerConnectionListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(inventoryClickListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(targetShootingListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(fireballExplosionListener, plugin);
        plugin.getCommand("game").setExecutor(command);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(playerConnectionListener);
        HandlerList.unregisterAll(inventoryClickListener);
        HandlerList.unregisterAll(targetShootingListener);
        HandlerList.unregisterAll(fireballExplosionListener);
    }

    @Override
    public List<ITargetShootingGame> getGames() {
        return games;
    }

    @Override
    public ITargetShootingGame getGame(Player player) {
        for (ITargetShootingGame game : games)
            if (game.isPlaying(player))
                return game;
        return null;
    }

    @Override
    public ITargetShootingGame create() {
        ITargetShootingGame game = new TargetShootingGame();
        games.add(game);
        return game;
    }

    @Override
    public Inventory getGamesInventory() {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Partite in corso");
        for (int i = 0; i < games.size(); i++) {
            ITargetShootingGame game = games.get(i);
            ItemStack item = new ItemBuilder()
                    .setMaterial(game.getPlayersCount() >= ITargetShootingGame.MAX_PLAYERS ? Material.BARRIER : Material.ARROW)
                    .setName("§e§lPartita §6§l" + (i + 1))
                    .setLore("§f§lCLICK §7Unisciti alla partita")
                    .build();
            inventory.addItem(item);
        }
        return inventory;
    }

}
