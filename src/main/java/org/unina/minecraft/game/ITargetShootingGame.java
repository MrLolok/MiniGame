package org.unina.minecraft.game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.unina.minecraft.items.ItemBuilder;

import java.util.Map;

public interface ITargetShootingGame {
    int MAX_PLAYERS = 4;
    ItemStack BOW = new ItemBuilder().setMaterial(Material.BOW).setName("§3Arco").setEnchantments(Map.of(Enchantment.ARROW_INFINITE, 1)).build();

    int getPlayers();

    boolean addPlayer(Player player);

    boolean removePlayer(Player player);

    void start();

    void stop();
}
