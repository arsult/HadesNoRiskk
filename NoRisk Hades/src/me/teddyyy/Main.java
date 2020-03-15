package me.teddyyy;

import me.teddyyy.listener.HadesListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main handler;

    @Override
    public void onEnable() {
        handler = this;

        Bukkit.getPluginManager().registerEvents(new HadesListener(), this);
    }

    public static Main getHandler() {
        return handler;
    }

}
