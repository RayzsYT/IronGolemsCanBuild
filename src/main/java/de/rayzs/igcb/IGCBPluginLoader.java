package de.rayzs.igcb;

import de.rayzs.igcb.handler.impl.FoliaGolemHandler;
import de.rayzs.igcb.listener.GolemListener;
import de.rayzs.igcb.handler.GolemHandler;
import de.rayzs.igcb.handler.impl.BukkitGolemHandler;
import de.rayzs.igcb.utils.VersionHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class IGCBPluginLoader extends JavaPlugin {

    @Override
    public void onEnable() {
        VersionHelper.initialize(Bukkit.getBukkitVersion());

        final GolemHandler<?> tasks = VersionHelper.getSoftware() == VersionHelper.Software.FOLIA
                ? new FoliaGolemHandler(this)
                : new BukkitGolemHandler(this);


        getServer().getPluginManager().registerEvents(
                new GolemListener(tasks), this
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
