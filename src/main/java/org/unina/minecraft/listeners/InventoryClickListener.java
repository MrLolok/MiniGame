package org.unina.minecraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.unina.minecraft.game.ITargetShootingService;

public class InventoryClickListener implements Listener {
    private final ITargetShootingService service;

    public InventoryClickListener(ITargetShootingService service) {
        this.service = service;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Partite in corso")) return;
        Inventory inv = event.getClickedInventory();
        if (inv == null || inv.getType() == InventoryType.PLAYER) return;
        event.setCancelled(true);
        int slot = event.getSlot();
        service.getGames().get(slot).addPlayer((Player) event.getWhoClicked());
    }
}
