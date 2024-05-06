package org.unina.minecraft.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.unina.minecraft.items.ItemBuilder;

import java.util.List;
import java.util.Map;

public interface ITargetShootingGame {
    World DEFAULT_WORLD = Bukkit.getServer().getWorlds().get(0);
    int MAX_PLAYERS = 4;
    ItemStack BOW = new ItemBuilder().setMaterial(Material.BOW).setName("§3Arco").setUnbreakable(true).setItemFlags(ItemFlag.HIDE_UNBREAKABLE).setEnchantments(Enchantment.ARROW_INFINITE).build();

    int getPlayersCount();

    List<Player> getPlayers();

    boolean isStarted();

    Entity getTarget();

    int getTaskId();

    boolean isPlaying(Player player);

    boolean addPlayer(Player player);

    boolean removePlayer(Player player);

    void addPoints(Player player, int points);

    void removePoints(Player player, int points);

    int getPoints(Player player);

    void start();

    void stop();
}
