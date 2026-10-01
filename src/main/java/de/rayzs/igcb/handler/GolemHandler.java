package de.rayzs.igcb.handler;

import de.rayzs.igcb.task.GolemTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class GolemHandler<T extends GolemTask> {

    protected final HashMap<Integer, T> tasks = new HashMap<>();

    public abstract void setTask(final Player player, final LivingEntity golem);
    public abstract void destructTask(final int id);
}
