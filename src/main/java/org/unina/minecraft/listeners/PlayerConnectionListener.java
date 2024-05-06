package org.unina.minecraft.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {
    private final ITargetShootingService service;

    public PlayerConnectionListener(ITargetShootingService service) {
        this.service = service;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§e§lBenvenuto! §fSei pronto per giocare?");
        notifyOnlinePlayers(player.getName() + " si è unito al server.", ChatColor.GOLD, ChatMessageType.ACTION_BAR, player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        notifyOnlinePlayers(player.getName() + " ha abbandonato il server.", ChatColor.GOLD, ChatMessageType.ACTION_BAR, player);
        ITargetShootingGame game = service.getGame(player);
        if (game != null)
            game.removePlayer(player);
    }

    private void notifyOnlinePlayers(String message, ChatColor color, ChatMessageType type, Player... ignored) {
        List<UUID> ignoredUUIDs = Arrays.stream(ignored).map(Player::getUniqueId).toList();
        BaseComponent[] components = new ComponentBuilder().append(message).color(color).create();
        for (Player other : Bukkit.getOnlinePlayers())
            if (!ignoredUUIDs.contains(other.getUniqueId()))
                other.spigot().sendMessage(type, components);
    }
}
