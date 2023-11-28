package com.example.craft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 * PlayerEventHandler.
 */
public class PlayerEventHandler implements Listener {
    /**
     * Called when player dies.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();

        Player killer = player.getKiller();

        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();

            if (hasLooting(weapon)) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
                head.getItemMeta().setDisplayName(player.getName() + "'s head");
                player.getWorld().dropItemNaturally(location, head);
            }
        }

        player.sendMessage("§cYou died at " + (int) (location.getX()) + " " + (int) (location.getY()) + " "
                + (int) (location.getZ()) + "!");
    }

    private boolean hasLooting(ItemStack item) {
        switch (item.getType()) {
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
                return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) >= 3;
            default:
                return false;
        }
    }

    /**
     * Called when player joins.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String name = player.getName();
        String sender = "§b[Server]";

        String message = sender + " Oi " + name + " ping kasto xa ahile timro?";
        player.sendMessage(message);
    }
}
