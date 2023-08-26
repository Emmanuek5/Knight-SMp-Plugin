package com.obsidian.knightsmp.utils;
import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class ArmouredEntity  {
    public void spawnArmoredZombie(Location location) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        // Equip the zombie with iron armor
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);

        // Set armor items for the zombie
        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);
        zombie.getEquipment().setItemInMainHand(sword);

        // Make the zombie wear the armor
        zombie.getEquipment().setHelmetDropChance(0.0F);
        zombie.getEquipment().setChestplateDropChance(0.0F);
        zombie.getEquipment().setLeggingsDropChance(0.0F);
        zombie.getEquipment().setBootsDropChance(0.0F);
        zombie.getEquipment().setItemInMainHandDropChance(0.0F);
    }

    public void spawnArmoredZombieWithTarget(Location location, Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            Location spawnLocation = location.clone(); // Clone the original location
            // Check if the spawn location is inside a solid block
            if (spawnLocation.getBlock().getType().isSolid()) {
                spawnLocation.add(0, 1, 0); // Adjust the Y coordinate to spawn higher
            }
            Zombie zombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
            // Equip the zombie with iron armor
            ItemStack helmet = new ItemStack(Material.IRON_HELMET);
            ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            // Set armor items and weapon for the zombie
            zombie.getEquipment().setHelmet(helmet);
            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setLeggings(leggings);
            zombie.getEquipment().setBoots(boots);
            zombie.getEquipment().setItemInMainHand(sword);
            // Make the zombie wear the armor and hold the weapon
            zombie.getEquipment().setHelmetDropChance(0.0F);
            zombie.getEquipment().setChestplateDropChance(0.0F);
            zombie.getEquipment().setLeggingsDropChance(0.0F);
            zombie.getEquipment().setBootsDropChance(0.0F);

            // Find nearby entities
            boolean foundTarget = false;
            for (Entity entity : location.getWorld().getNearbyEntities(location, 100, 100, 100)) {
                if (entity instanceof Player && !entity.equals(player)) {
                    // Check if the entity is not the player who spawned the zombies
                    if (!entity.equals(player)) {
                        Bukkit.getServer().getConsoleSender().sendMessage(entity.getName());
                        zombie.setTarget((LivingEntity) entity);
                        foundTarget = true;
                        break;
                    }else {
                        Bukkit.getServer().getConsoleSender().sendMessage("You cannot target yourself!");
                    }
                }
            }

            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    zombie.remove();
                }
            };
            task.runTaskLater(KnightSmp.getPlugin(),  10 * 20); // Schedule the task to run after 5 seconds (5 * 20 ticks)
             // 300 ticks = 15 seconds (20 ticks per second
            // If no target is found, make the zombie passive
            if (!foundTarget) {
                zombie.setAI(false); // Disable AI so the zombie doesn't move or attack
            }
        }

}

}
