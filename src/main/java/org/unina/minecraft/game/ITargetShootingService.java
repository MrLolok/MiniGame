package org.unina.minecraft.game;

import org.bukkit.inventory.Inventory;
import org.unina.minecraft.Service;

import java.util.List;

public interface ITargetShootingService extends Service {
    List<ITargetShootingGame> getGames();

    ITargetShootingGame create();

    Inventory getGamesInventory();
}
