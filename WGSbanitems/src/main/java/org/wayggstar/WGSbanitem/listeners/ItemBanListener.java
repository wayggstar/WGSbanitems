package org.wayggstar.WGSbanitem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.wayggstar.WGSbanitem.BanItemConfig;

public class ItemBanListener implements Listener {

    private final BanItemConfig banItemConfig;

    // Constructor that accepts the BanItemConfig object
    public ItemBanListener(BanItemConfig banItemConfig) {
        this.banItemConfig = banItemConfig;
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Player player = (event.getEntity() instanceof Player) ? (Player) event.getEntity() : null;

        if (player != null && item != null && banItemConfig.isBanned(item.getType())) {
            player.sendMessage("§c이 아이템은 금지되어 있습니다.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Material blockType = event.getBlock().getType();
        if (banItemConfig.isBanned(blockType)) {
            event.getPlayer().sendMessage("§c이 블럭은 설치가 금지되어 있습니다.");
            event.setCancelled(true);
        }
    }
}
