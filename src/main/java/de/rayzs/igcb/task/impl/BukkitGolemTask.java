package de.rayzs.igcb.task.impl;

import de.rayzs.igcb.task.GolemTask;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.function.Supplier;

public class BukkitGolemTask extends BukkitRunnable implements GolemTask {

    private final Player player;
    private final LivingEntity golem;

    public BukkitGolemTask(
            final Player player,
            final LivingEntity golem
    ) {
        this.player = player;
        this.golem = golem;
    }

    @Override
    public void run() {
        final Supplier<Double> distance = () -> xzDistance(player, golem);


        // Cancel scheduler if any scenario is invalid.
        if (isInvalid(player, golem, distance)) {
            cancel();
            return;
        }


        // Ignore if player's and golem's height have no gap.
        if (Math.abs(player.getY() - golem.getY()) < 1) return;


        // Ignore if distance is too great.
        if (distance.get() > 2) return;


        // Push golem towards player is there's a block distance.
        if (distance.get() >= 1.25) {
            golem.setVelocity(golem.getVelocity().add(new Vector(
                    player.getX() - golem.getX(),
                    0,
                    player.getZ() - golem.getZ()
            )));
        }


        golemPlaceBlockBelow(golem, Material.DIRT);
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
    }
}
