package de.rayzs.igcb.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class GolemTasks {

    private final HashMap<Integer, GolemTask> tasks = new HashMap<>();
    private final Plugin plugin;

    public GolemTasks(final Plugin plugin) {
        this.plugin = plugin;
    }


    public void setTask(final Player player, final LivingEntity golem) {
        final int id = golem.getEntityId();
        destructTask(id);

        final GolemTask task = new GolemTask(this, id, player, golem);
        tasks.put(id, task);

        task.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20, 20));
    }

    public void destructTask(final int id) {
        final GolemTask task = tasks.get(id);

        if (task != null) {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
        }
    }
}
