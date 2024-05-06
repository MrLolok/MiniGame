package org.unina.minecraft.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.unina.minecraft.MiniGamePlugin;
import org.unina.minecraft.game.tasks.TargetFireballTask;

import java.util.*;

public class TargetShootingGame implements ITargetShootingGame {
    private final Map<UUID, Integer> scoreboard = new HashMap<>();
    private boolean started = false;
    private Entity target;
    private int taskId = -1;

    @Override
    public int getPlayersCount() {
        return scoreboard.size();
    }

    @Override
    public List<Player> getPlayers() {
        return scoreboard.keySet().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).toList();
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public Entity getTarget() {
        return target;
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

    @Override
    public boolean isPlaying(Player player) {
        return scoreboard.containsKey(player.getUniqueId());
    }

    @Override
    public boolean addPlayer(Player player) {
        if (scoreboard.containsKey(player.getUniqueId()))
            return false;
        if (scoreboard.size() >= MAX_PLAYERS)
            return false;
        if (started)
            equip(player);
        scoreboard.put(player.getUniqueId(), 0);
        return true;
    }

    @Override
    public boolean removePlayer(Player player) {
        return scoreboard.remove(player.getUniqueId()) != null;
    }

    @Override
    public void addPoints(Player player, int points) {
        int current = getPoints(player);
        scoreboard.replace(player.getUniqueId(), current + points);
    }

    @Override
    public void removePoints(Player player, int points) {
        int current = getPoints(player);
        scoreboard.replace(player.getUniqueId(), current - points);
    }

    @Override
    public int getPoints(Player player) {
        return scoreboard.get(player.getUniqueId());
    }

    @Override
    public void start() {
        if (started)
            throw new IllegalStateException("Game already started.");
        target = DEFAULT_WORLD.spawnEntity(getTargetSpawnLocation(), EntityType.ZOMBIE);
        setTargetDetails();
        taskId = Bukkit.getScheduler().runTaskTimer(MiniGamePlugin.getInstance(), new TargetFireballTask(this), 20L, 100L).getTaskId();
        for (UUID uuid : scoreboard.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                equip(player);
        }
        started = true;
    }

    @Override
    public void stop() {
        if (!started) return;
        if (target != null)
            target.remove();
        if (taskId > 0)
            Bukkit.getScheduler().cancelTask(taskId);
        scoreboard.clear();
    }

    private void equip(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(BOW.clone(), new ItemStack(Material.ARROW));
        player.sendTitle("§b§lATTACCA LO ZOMBIE", "Ti è stato equipaggiato un arco, spara!", 20, 60, 20);
    }

    private void setTargetDetails() {
        if (!(target instanceof LivingEntity entity)) return;
        entity.setAI(false);
        entity.setInvulnerable(true);
        entity.setCanPickupItems(false);
        entity.setCustomName("Target");
        entity.setCustomNameVisible(true);
        entity.setSilent(true);
    }

    private Location getTargetSpawnLocation() {
        if (getPlayersCount() == 0)
            return new Location(DEFAULT_WORLD, 0, 0, 0);
        Player player = getPlayers().get(0);
        return player.getLocation();
    }
}
