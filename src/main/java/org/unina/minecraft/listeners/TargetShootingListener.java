package org.unina.minecraft.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;

public class TargetShootingListener implements Listener {
    private final ITargetShootingService service;

    public TargetShootingListener(ITargetShootingService service) {
        this.service = service;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof Zombie zombie) {
            if (arrow.getShooter() instanceof Player player) {
                ITargetShootingGame game = service.getGame(player);
                if (game == null || game.getTarget().getEntityId() != zombie.getEntityId()) return;
                game.addPoints(player, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                player.sendMessage("§a§oColpito! §f" + game.getPoints(player));
            }
        }
    }
}
