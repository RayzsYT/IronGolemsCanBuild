package de.rayzs.igcb.task.impl;

import de.rayzs.igcb.task.GolemTask;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

        if (isInvalid(player, golem, distance)) {
            cancel();
            return;
        }

        if (distance.get() > 2) return;
        if (Math.abs(player.getY() - golem.getY()) < 1) return;


        golemPlaceBlockBelow(golem, Material.DIRT);
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
    }
}
