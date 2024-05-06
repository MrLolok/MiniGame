package org.unina.minecraft.game;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
        if (started) {
            //...
        }
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
        started = true;
    }

    @Override
    public void stop() {
        if (!started) return;
        if (target != null)
            target.remove();
        players.clear();
    }
}
