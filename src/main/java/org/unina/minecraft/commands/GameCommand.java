package org.unina.minecraft.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.unina.minecraft.game.ITargetShootingGame;
import org.unina.minecraft.game.ITargetShootingService;

public class GameCommand implements CommandExecutor {
    private final ITargetShootingService service;

    public GameCommand(ITargetShootingService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Ciao! Sei pronto per giocare? Digita /game list o /game create");
            if (sender instanceof Player player)
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
        } else {
            switch (args[0].toUpperCase()) {
                case "CREATE":
                    ITargetShootingGame game = service.create();
                    if (game == null) {
                        sender.sendMessage("§cErrore durante la creazione della partita!");
                        return true;
                    }
                    if (sender instanceof Player player) {
                        game.addPlayer(player);
                        game.start();
                    }
                    break;
                case "LIST":
                    if (service.getGames().isEmpty()) {
                        sender.sendMessage("§cNessuna partita in corso!");
                        return true;
                    }
                    if (sender instanceof Player player) {
                        Inventory inventory = service.getGamesInventory();
                        player.openInventory(inventory);
                    } else
                        sender.sendMessage("§eSono presenti " + service.getGames().size() + " partite in corso.");
                    break;
                default:
                    sender.sendMessage("§cSotto-comando sconosciuto!");
            }
        }
        return false;
    }
}
