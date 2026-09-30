package de.rayzs.igcb.task;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GolemTask implements Runnable {

    private final GolemTasks tasks;
    private final int id;
    private final Player player;
    private final LivingEntity golem;

    private int taskId = -1;

    public GolemTask(final GolemTasks tasks, final int id, final Player player, final LivingEntity golem) {
        this.tasks = tasks;
        this.id = id;
        this.player = player;
        this.golem = golem;
    }

    public void setTaskId(final int taskId) {
        if (taskId < 0) {
            throw new IllegalStateException("Task ID cannot be negative");
        }

        if (this.taskId == -1) {
            this.taskId = taskId;
            return;
        }

        throw new IllegalStateException("Task ID is already set!");
    }

    public int getTaskId() {
        return this.taskId;
    }

    @Override
    public void run() {

        if (!player.getWorld().getName().equalsIgnoreCase(golem.getWorld().getName()) || player.isDead() || !player.isOnline()) {
            tasks.destructTask(id);
            return;
        }


        final double distance = xzDistance();

        if (distance > 30) {
            tasks.destructTask(id);
            return;
        }


        if (distance > 2) return;
        if (Math.abs(player.getY() - golem.getY()) < 1) return;


        golemPlaceBlockBelow(Material.DIRT);
    }

    private void golemPlaceBlockBelow(final Material placingBlockMaterial) {
        final Location location = golem.getLocation().clone();
        final Block block = location.getBlock();


        if (block.getRelative(BlockFace.DOWN).getType() == Material.AIR) return;


        golem.teleport(location
                .setDirection(new Vector(0, 0, 0))
        );

        golem.setVelocity(golem.getVelocity().add(new Vector(0, 0.25, 0)));
        block.setType(placingBlockMaterial);

        final Sound sound = block.getBlockSoundGroup().getPlaceSound();
        location.getNearbyEntities(10, 10, 10).forEach(entity -> {
            if (entity instanceof Player other) {
                other.playSound(location, sound, 1, 1);
            }
        });
    }

    private double xzDistance() {
        final Location playerLocation = player.getLocation();

        final double playerX = playerLocation.getX();
        final double playerZ = playerLocation.getZ();

        final Location golemLocation = golem.getLocation();
        final double golemX = golemLocation.getX();
        final double golemZ = golemLocation.getZ();

        return Math.sqrt(
                Math.pow(golemX - playerX, 2) + Math.pow(golemZ - playerZ, 2)
        );
    }
}
