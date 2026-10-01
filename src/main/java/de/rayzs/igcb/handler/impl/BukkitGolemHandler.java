package de.rayzs.igcb.handler.impl;

import de.rayzs.igcb.task.GolemTask;
import de.rayzs.igcb.task.impl.BukkitGolemTask;
import de.rayzs.igcb.handler.GolemHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitGolemHandler extends GolemHandler<BukkitGolemTask> {

    private final Plugin plugin;

    public BukkitGolemHandler(final Plugin plugin) {
        this.plugin = plugin;
    }


    public void setTask(final Player player, final LivingEntity golem) {
        final int id = golem.getEntityId();
        destructTask(id);

        final BukkitGolemTask task = new BukkitGolemTask(player, golem);
        task.runTaskTimer(plugin, 20L, 20L);

        tasks.put(id, task);
    }

    public void destructTask(final int id) {
        final GolemTask task = tasks.get(id);

        if (task != null) {
            task.cancel();
        }
    }
}
