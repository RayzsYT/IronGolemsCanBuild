package de.rayzs.igcb;

import de.rayzs.igcb.listener.GolemListener;
import de.rayzs.igcb.task.GolemTasks;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class IGCBPluginLoader extends JavaPlugin {

    @Override
    public void onEnable() {
        final GolemTasks tasks = new GolemTasks(this);


        getServer().getPluginManager().registerEvents(
                new GolemListener(tasks), this
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
