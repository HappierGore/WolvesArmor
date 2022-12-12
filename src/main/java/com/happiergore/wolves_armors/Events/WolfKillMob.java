package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Items.Behaviour.Behaviours;
import static com.happiergore.wolves_armors.Runnables.RefreshTargets.findWolf;
import com.happiergore.wolves_armors.main;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author HappieGore
 */
public class WolfKillMob {

    public static void listen(EntityDamageByEntityEvent e) {
        boolean entityDeath = ((LivingEntity) e.getEntity()).getHealth() <= e.getDamage();
        if (entityDeath && e.getDamager() instanceof Wolf) {
            WolfData wolfData = main.wolvesData.get(e.getDamager().getUniqueId().toString());
            if (wolfData == null || wolfData.getBehaviour() != Behaviours.AGRESSIVE) {
                return;
            }
            double radius = main.configYML.getDouble("Behaviour.Agressive.MaxRadius");
            new BukkitRunnable() {
                @Override
                public void run() {
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
                                    break;
                                }
                            }
                        }
                    }
                }
            }.runTaskLater(main.getPlugin(main.class), 20 * main.configYML.getLong("wolf_find_targets_time_when_kill"));
        }
    }
}
