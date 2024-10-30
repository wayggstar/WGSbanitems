package org.wayggstar.WGSbanitem;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.wayggstar.WGSbanitem.listeners.ItemBanListener;  // Import Listener class
import org.wayggstar.WGSbanitem.BanItemConfig;              // Import Config class
import org.wayggstar.WGSbanitem.BanItemSetCommand;          // Import Command class
import org.wayggstar.WGSbanitem.BanItemTab;                 // Import Tab Completer class

public final class WGSBanItem extends JavaPlugin implements Listener {

    private BanItemConfig banItemConfig;  // Declare the config object

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "금지아이템 플러그인 활성화 (by.wayggstar)");

        // Create plugin folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Initialize the BanItemConfig object
        banItemConfig = new BanItemConfig(this);

        // Register commands and listeners
        getCommand("금지아이템").setExecutor(new BanItemSetCommand(this, banItemConfig));
        getCommand("금지아이템").setTabCompleter(new BanItemTab());

        // Register the event listener with the initialized banItemConfig
        getServer().getPluginManager().registerEvents(new ItemBanListener(banItemConfig), this);
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "금지아이템 플러그인 비활성화 (by.wayggstar)");
    }
}
