package com.example.craft.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * CreeperSpawn.
 */
public class EntitySpawnHandler implements Listener {
    private int chargedCreeperDropAmount = 10;
    private int chargedCreeperDropAmountWithLooting = 15;

    /**
     * Called when a creeper spawns.
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        EntityType type = event.getEntityType();

        if (type == EntityType.CREEPER) {
            Creeper creeper = (Creeper) event.getEntity();

            boolean shouldSpawnChargedCreeper = Math.floor(Math.random() * (double) 1000) < 50;

            // This RNG is not very good, but it's good enough for this purpose.
            if (shouldSpawnChargedCreeper) {
                creeper.setPowered(true);
            }
        }
    }

    /**
     * Called when entity dies.
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType type = event.getEntityType();

        if (type == EntityType.CREEPER) {
            Creeper creeper = (Creeper) event.getEntity();

            if (creeper.isPowered()) {
                Player player = (Player) creeper.getKiller();

                int amount = player != null
                        && hasLooting(player.getInventory().getItemInMainHand()) ? chargedCreeperDropAmountWithLooting
                                : chargedCreeperDropAmount;
                ItemStack gunpowder = new ItemStack(Material.GUNPOWDER, amount);

                creeper.getWorld().dropItemNaturally(creeper.getLocation(), gunpowder);
            }
        }
    }

    /**
     * Called when player breaks a block.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        Material block = (Material) event.getBlock().getType();
        ItemStack itemInUse = player.getInventory().getItemInMainHand();

        if (block == Material.SPAWNER && itemInUse.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0) {
            CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            EntityType entityType = spawner.getSpawnedType();
            Material egg = getEgg(entityType);

            if (egg == null) {
                return;
            }

            ItemStack newSpawner = new ItemStack(Material.SPAWNER, 1);
            ItemStack eggItem = new ItemStack(egg, 1);

            World world = event.getBlock().getWorld();

            Location location = event.getBlock().getLocation();

            world.dropItemNaturally(location, newSpawner);
            world.dropItemNaturally(location, eggItem);

            event.setExpToDrop(0);
            event.setDropItems(false);
        }
    }

    /**
     * Returns true if the given item has looting enchantment.
     */
    private boolean hasLooting(ItemStack item) {
        switch (item.getType()) {
            case IRON_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
            case GOLDEN_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:
                return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) > 0;
            default:
                return false;
        }
    }

    /**
     * Returns the egg type for the given entity type.
     */
    private Material getEgg(EntityType entityType) {
        switch (entityType) {
            case CREEPER:
                return Material.CREEPER_SPAWN_EGG;
            case SKELETON:
                return Material.SKELETON_SPAWN_EGG;
            case SPIDER:
                return Material.SPIDER_SPAWN_EGG;
            case ZOMBIE:
                return Material.ZOMBIE_SPAWN_EGG;
            case CAVE_SPIDER:
                return Material.CAVE_SPIDER_SPAWN_EGG;
            case SILVERFISH:
                return Material.SILVERFISH_SPAWN_EGG;
            case BLAZE:
                return Material.BLAZE_SPAWN_EGG;
            case MAGMA_CUBE:
                return Material.MAGMA_CUBE_SPAWN_EGG;
            default:
                return null;
        }
    }
}
