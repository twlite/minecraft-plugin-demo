package com.example.craft.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CatRaveCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public CatRaveCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        Location spawnLocation = player.getLocation().clone();

        spawnLocation.add(player.getLocation().getDirection().multiply(5));
        spawnLocation.setY(player.getLocation().getY());

        Cat cat = (Cat) player.getWorld().spawnEntity(spawnLocation, EntityType.CAT);
        cat.setCatType(Cat.Type.TABBY);
        cat.setCustomName("oiia");
        cat.setCustomNameVisible(true);
        cat.setAI(false);

        new BukkitRunnable() {
            private long startTime = System.currentTimeMillis();
            private final double RADIUS = 0.15;
            private final double VERY_FAST_SPEED = 75.0;
            private final double FAST_SPEED = 55.0;
            private final double NORMAL_SPEED = 45.0;
            private float yaw = 0;
            private float pitch = 0;

            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                double elapsedSeconds = (currentTime - startTime) / 1000.0;

                if (elapsedSeconds >= 11) {
                    cat.setHealth(0);
                    this.cancel();
                    player.sendMessage("Cat spin ended!");
                    return;
                }

                // Initial pause after spawn
                if (elapsedSeconds < 1) {
                    return;
                }

                // Very fast spin cycle
                if (elapsedSeconds >= 1 && elapsedSeconds < 3) {
                    spinCat(VERY_FAST_SPEED, true);
                }
                // Pause between cycles
                else if (elapsedSeconds >= 3 && elapsedSeconds < 5) {
                    return;
                }
                // Fast spin cycle
                else if (elapsedSeconds >= 5 && elapsedSeconds < 7) {
                    spinCat(FAST_SPEED, true);
                }
                // Pause between cycles
                else if (elapsedSeconds >= 7 && elapsedSeconds < 8) {
                    return;
                }
                // Normal spin cycle
                else if (elapsedSeconds >= 8 && elapsedSeconds < 10) {
                    spinCat(NORMAL_SPEED, false);
                }
            }

            private void spinCat(double speed, boolean withPitch) {
                yaw = (yaw + (float) speed) % 360;
                if (withPitch) {
                    pitch = (pitch + (float) (speed / 2)) % 360;
                }

                Location catLoc = spawnLocation.clone();
                catLoc.setYaw(yaw);
                catLoc.setPitch(withPitch ? pitch : 0);

                // Add slight circular motion
                double offsetX = RADIUS * Math.cos(Math.toRadians(yaw));
                double offsetZ = RADIUS * Math.sin(Math.toRadians(yaw));
                catLoc.add(offsetX, 0, offsetZ);

                cat.teleport(catLoc);
            }
        }.runTaskTimer(plugin, 0L, 1L);

        player.sendMessage("Cat spin started!");
        return true;
    }
}
