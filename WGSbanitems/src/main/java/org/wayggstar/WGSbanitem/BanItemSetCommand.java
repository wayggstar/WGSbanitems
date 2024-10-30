package org.wayggstar.WGSbanitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BanItemSetCommand implements CommandExecutor {

    private final Plugin plugin;
    private final BanItemConfig banItemConfig;

    public BanItemSetCommand(Plugin plugin, BanItemConfig banItemConfig) {
        this.plugin = plugin;
        this.banItemConfig = banItemConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "사용법: /금지아이템 <추가|제거|리로드> <아이템이름>");
            return false;
        }

        String action = args[0];

        switch (action.toLowerCase()) {
            case "추가":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "아이템 이름을 입력하세요!");
                    return false;
                }
                return addBannedItem(sender, args[1]);

            case "제거":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "아이템 이름을 입력하세요!");
                    return false;
                }
                return removeBannedItem(sender, args[1]);

            case "리로드":
                return reloadPlugin(sender);

            default:
                sender.sendMessage(ChatColor.RED + "잘못된 명령어입니다. <추가|제거|리로드> 중 하나를 사용하세요.");
                return false;
        }
    }

    // Add an item to the banned list
    private boolean addBannedItem(CommandSender sender, String itemName) {
        Material material = Material.getMaterial(itemName.toUpperCase());
        if (material == null) {
            sender.sendMessage(ChatColor.RED + "유효하지 않은 아이템입니다.");
            return false;
        }

        if (banItemConfig.isBanned(material)) {
            sender.sendMessage(ChatColor.YELLOW + material.name() + " 은 이미 금지된 아이템입니다.");
            return false;
        }

        banItemConfig.banItem(material);
        sender.sendMessage(ChatColor.GREEN + material.name() + " 아이템이 성공적으로 금지되었습니다.");
        return true;
    }

    // Remove an item from the banned list
    private boolean removeBannedItem(CommandSender sender, String itemName) {
        Material material = Material.getMaterial(itemName.toUpperCase());
        if (material == null) {
            sender.sendMessage(ChatColor.RED + "유효하지 않은 아이템입니다.");
            return false;
        }

        if (!banItemConfig.isBanned(material)) {
            sender.sendMessage(ChatColor.YELLOW + material.name() + " 은 금지되지 않은 아이템입니다.");
            return false;
        }

        banItemConfig.unbanItem(material);
        sender.sendMessage(ChatColor.GREEN + material.name() + " 아이템이 금지 해제되었습니다.");
        return true;
    }

    // Reload the plugin configuration
    private boolean reloadPlugin(CommandSender sender) {
        plugin.reloadConfig();
        banItemConfig.loadBannedItems();
        sender.sendMessage(ChatColor.GREEN + "플러그인 설정이 리로드되었습니다.");
        return true;
    }
}
