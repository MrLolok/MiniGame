package org.unina.minecraft.game;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TargetShootingGame implements ITargetShootingGame {
    private final Map<UUID, Integer> players = new HashMap<>();
    private boolean started = false;
    private Entity target;

    @Override
    public int getPlayers() {
        return players.size();
    }

    @Override
    public boolean addPlayer(Player player) {
        if (players.containsKey(player.getUniqueId()))
            return false;
        if (players.size() >= MAX_PLAYERS)
            return false;
        if (started)
            equip(player);
        players.put(player.getUniqueId(), 0);
        return true;
    }

    @Override
    public boolean removePlayer(Player player) {
        return players.remove(player.getUniqueId()) != null;
    }

    @Override
    public void start() {
        if (started)
            throw new IllegalStateException("Game already started.");
        for (UUID uuid : players.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                equip(player);
        }
        started = true;
    }

    @Override
    public void stop() {
        if (!started) return;
        if (target != null)
            target.remove();
        players.clear();
    }

    private void equip(Player player) {
        player.getInventory().addItem(BOW.clone(), new ItemStack(Material.ARROW));
        player.sendTitle("§b§lATTACCA LO ZOMBIE", "Ti è stato equipaggiato un arco, spara!", 20, 60, 20);
    }
}
