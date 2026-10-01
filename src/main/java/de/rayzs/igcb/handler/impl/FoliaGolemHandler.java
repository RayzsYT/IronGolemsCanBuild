package de.rayzs.igcb.handler.impl;

import de.rayzs.igcb.task.GolemTask;
import de.rayzs.igcb.task.impl.FoliaGolemTask;
import de.rayzs.igcb.handler.GolemHandler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FoliaGolemHandler extends GolemHandler<FoliaGolemTask> {

    private final Plugin plugin;

    public FoliaGolemHandler(final Plugin plugin) {
        this.plugin = plugin;
    }


    public void setTask(final Player player, final LivingEntity golem) {
        final int id = golem.getEntityId();
        destructTask(id);

        final FoliaGolemTask task = new FoliaGolemTask(player, golem);
        tasks.put(id, task);

        final ScheduledTask scheduledTask = golem.getScheduler().runAtFixedRate(
                plugin, task::runScheduledTask,
                null, 20, 20
        );

        task.setScheduledTask(scheduledTask);
    }

    public void destructTask(final int id) {
        final GolemTask task = tasks.get(id);

        if (task != null) {
            task.cancel();
        }
    }
}
