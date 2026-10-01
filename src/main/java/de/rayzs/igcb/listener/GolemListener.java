package de.rayzs.igcb.listener;

import de.rayzs.igcb.handler.GolemHandler;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class GolemListener implements Listener {


    private final GolemHandler<?> tasks;

    public GolemListener(final GolemHandler<?> tasks) {
        this.tasks = tasks;
    }


    @EventHandler
    public void onGolemTarget(final EntityTargetLivingEntityEvent event) {
        if (! (event.getEntity() instanceof IronGolem golem)) return;

        if (! (event.getTarget() instanceof Player player)) {
            final int id = golem.getEntityId();
            tasks.destructTask(id);
            return;
        }

        tasks.setTask(player, golem);
    }

    @EventHandler
    public void onPlayerAttackingGolem(final EntityDamageByEntityEvent event) {
        if (! (event.getEntity() instanceof IronGolem golem)) return;
        if (! (event.getDamager() instanceof Player player)) return;

        if (golem.isPlayerCreated()) golem.setPlayerCreated(false);
        if (golem.getTarget() != player) {
            tasks.setTask(player, golem);
            golem.setTarget(player);
        }
    }
}
