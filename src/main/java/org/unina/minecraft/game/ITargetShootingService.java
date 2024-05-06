package org.unina.minecraft.game;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.unina.minecraft.Service;

import java.util.List;

public interface ITargetShootingService extends Service {
    List<ITargetShootingGame> getGames();

    ITargetShootingGame getGame(Player player);

    ITargetShootingGame create();

    Inventory getGamesInventory();
}
