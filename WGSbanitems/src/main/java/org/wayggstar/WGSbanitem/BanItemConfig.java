package org.wayggstar.WGSbanitem;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BanItemConfig {
    private final Plugin plugin;
    private final File configFile;
    private FileConfiguration config;
    private final Set<Material> bannedItems = new HashSet<>();

    public BanItemConfig(Plugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "banned_items.yml");

        // Load the config on initialization
        this.config = YamlConfiguration.loadConfiguration(configFile);
        loadBannedItems();
    }

    // Load banned items from config
    public void loadBannedItems() {
        bannedItems.clear();
        List<String> itemNames = config.getStringList("bannedItems");

        for (String itemName : itemNames) {
            Material material = Material.getMaterial(itemName.toUpperCase());
            if (material != null) {
                bannedItems.add(material);
            } else {
                plugin.getLogger().warning("Invalid item in config: " + itemName);
            }
        }
    }

    // Save banned items to config
    public void saveBannedItems() {
        List<String> itemNames = bannedItems.stream()
                .map(Material::name)
                .toList(); // Java 16+ stream collector
        config.set("bannedItems", itemNames);

        try {
            config.save(configFile);
            plugin.getLogger().info("Banned items saved successfully.");
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save banned items: " + e.getMessage());
        }
    }

    // Add an item to the banned list and save
    public void banItem(Material material) {
        if (bannedItems.add(material)) {
            saveBannedItems();
        }
    }

    // Remove an item from the banned list and save
    public void unbanItem(Material material) {
        if (bannedItems.remove(material)) {
            saveBannedItems();
        }
    }

    // Check if an item is banned
    public boolean isBanned(Material material) {
        return bannedItems.contains(material);
    }
}
