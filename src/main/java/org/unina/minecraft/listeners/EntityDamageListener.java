package org.unina.minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;

public class EntityDamageListener implements Listener {
    private final ITargetShootingService service;

    public EntityDamageListener(ITargetShootingService service) {
        this.service = service;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Zombie zombie && service.getGame(zombie.getEntityId()) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ITargetShootingGame game = service.getGame(player);
        if (game != null)
            event.setKeepInventory(true);
    }
}
