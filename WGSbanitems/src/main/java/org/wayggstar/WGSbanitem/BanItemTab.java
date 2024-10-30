package org.wayggstar.WGSbanitem;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BanItemTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("추가", "제거", "리로드");
        } else if (args.length == 2) {
            List<String> itemNames = new ArrayList<>();
            for (Material material : Material.values()) {
                itemNames.add(material.name());
            }
            return itemNames;
        }
        return new ArrayList<>();
    }
}
