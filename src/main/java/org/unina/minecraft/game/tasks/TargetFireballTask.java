package org.unina.minecraft.game.tasks;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.unina.minecraft.game.ITargetShootingGame;

public class TargetFireballTask implements Runnable {
    private final ITargetShootingGame game;

    public TargetFireballTask(ITargetShootingGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (!game.isStarted() || game.getTarget() == null) return;
        for (Player player : game.getPlayers()) {
            Fireball fireball = player.getWorld().spawn(game.getTarget().getLocation(), Fireball.class);
            fireball.setDirection(player.getLocation().toVector().subtract(game.getTarget().getLocation().toVector()).normalize());
        }
    }
}
