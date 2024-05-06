package org.unina.minecraft.game;

import org.bukkit.entity.Player;

public interface ITargetShootingGame {
    int MAX_PLAYERS = 4;

    int getPlayers();

    boolean addPlayer(Player player);

    boolean removePlayer(Player player);

    void start();

    void stop();
}
