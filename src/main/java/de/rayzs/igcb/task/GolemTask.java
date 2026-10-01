package de.rayzs.igcb.task;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.function.Supplier;

public interface GolemTask {

    void cancel();

    default boolean isInvalid(
            final Player player,
            final LivingEntity golem,
            final Supplier<Double> distance
    ) {
        if (player.isDead() || !player.isOnline()) {
            return true;
        }

        if (!player.getWorld().getName().equalsIgnoreCase(golem.getWorld().getName())) {
            return true;
        }

        return distance.get() > 30;
    }

    default void golemPlaceBlockBelow(
            final LivingEntity golem,
            final Material placingBlockMaterial
    ) {
        final Location location = golem.getLocation().clone();
        final Block block = location.getBlock();


        if (block.getRelative(BlockFace.DOWN).getType() == Material.AIR) return;


        golem.teleport(location
                .setDirection(new Vector(0, 0, 0))
        );

        golem.setVelocity(golem.getVelocity().add(new Vector(0, 0.4, 0)));
        block.setType(placingBlockMaterial);

        final Sound sound = block.getBlockSoundGroup().getPlaceSound();
        location.getNearbyEntities(10, 10, 10).forEach(entity -> {
            if (entity instanceof Player other) {
                other.playSound(location, sound, 1, 1);
            }
        });
    }

    default double xzDistance(
            final Player player,
            final LivingEntity golem
    ) {
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
