package com.happiergore.wolves_armors.Runnables;

import com.happiergore.wolves_armors.Items.Behaviour.Behaviours;
import com.happiergore.wolves_armors.main;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author HappieGore
 */
public class RefreshTargets {

    public static void refreshTarget() {
        new BukkitRunnable() {
            double radius = main.configYML.getDouble("Behaviour.Agressive.MaxRadius");

            @Override
            public void run() {
                main.wolvesData.values().forEach(wolfData -> {
                    if (wolfData.getBehaviour() == Behaviours.AGRESSIVE) {
                        Wolf wolf = findWolf(wolfData.getUUID());
                        if (wolf == null) {
                            return;
                        }
                        if (wolf.getTarget() == null || wolf.getTarget().isDead()) {
                            List<Entity> entities = wolf.getNearbyEntities(radius, radius, radius);
                            for (Entity entity : entities) {
                                if (entity instanceof LivingEntity) {
                                    boolean targetIsOwner = wolf.getOwner().getUniqueId().toString().equalsIgnoreCase(entity.getUniqueId().toString());
                                    boolean targetInBlakList = wolfData.getBehaviour().getBlackList().contains(entity.getType().toString());
                                    boolean targetIsFriend = entity instanceof Wolf && ((Wolf) entity).isTamed() && ((Wolf) entity).getOwner().getUniqueId().toString().equalsIgnoreCase(wolf.getOwner().getUniqueId().toString());
                                    if (!targetIsOwner && !targetInBlakList && !targetIsFriend) {
                                        wolf.setTarget((LivingEntity) entity);
                                        return;
                                    }
                                }
                            }
                            wolf.setTarget(null);
                        }
                    }
                }
                );
            }
        }.runTaskTimer(main.getPlugin(main.class
        ), 0, 20 * main.configYML.getLong("wolf_find_targets_time"));
    }

    public static Wolf findWolf(String UUID) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntitiesByClass(Wolf.class
            )) {
                if (entity.getUniqueId()
                        .toString().equalsIgnoreCase(UUID)) {
                    return (Wolf) entity;
                }
            }
        }
        return null;
    }
}
