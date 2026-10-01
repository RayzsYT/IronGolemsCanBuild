package de.rayzs.igcb.task.impl;

import de.rayzs.igcb.task.GolemTask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Material;

import java.util.function.Supplier;

public class FoliaGolemTask implements GolemTask {

    private final Player player;
    private final LivingEntity golem;

    private ScheduledTask scheduledTask;

    public FoliaGolemTask(
            final Player player,
            final LivingEntity golem
    ) {
        this.player = player;
        this.golem = golem;
    }

    public void setScheduledTask(final ScheduledTask scheduledTask) {
        if (this.scheduledTask == null) {
            this.scheduledTask = scheduledTask;
            return;
        }

        throw new IllegalStateException("ScheduledTask is already set!");
    }

    public void runScheduledTask(final ScheduledTask scheduledTask) {
        final Supplier<Double> distance = () -> xzDistance(player, golem);

        if (isInvalid(player, golem, distance)) {
            scheduledTask.cancel();
            return;
        }

        if (distance.get() > 2) return;
        if (Math.abs(player.getY() - golem.getY()) < 1) return;


        golemPlaceBlockBelow(golem, Material.DIRT);
    }

    @Override
    public void cancel() {
        if (this.scheduledTask != null) {
            this.scheduledTask.cancel();
        }
    }
}
