package org.unina.minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;

public class InventoryClickListener implements Listener {
    private final ITargetShootingService service;

    public InventoryClickListener(ITargetShootingService service) {
        this.service = service;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Partite in corso")) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (inv == null || inv.getType() == InventoryType.PLAYER) return;
        event.setCancelled(true);
        int slot = event.getSlot();
        ITargetShootingGame game = service.getGames().get(slot);
        player.closeInventory();
        if (game.isPlaying(player)) {
            player.sendTitle("§c§lSEI IN GIOCO!", "", 0, 60, 20);
            return;
        }
        service.getGames().get(slot).addPlayer((Player) event.getWhoClicked());
    }
}
