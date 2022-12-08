package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author HappieGore
 */
public class OnWolfDeath {

    public static void listen(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) e.getEntity();
            if (wolf.isTamed()) {
                String wolfUUID = wolf.getUniqueId().toString();
                if (main.wolvesData.containsKey(wolfUUID)) {
                    WolfData wolfData = main.wolvesData.get(wolfUUID);
                    if (wolfData.getArmor() != null) {
                        e.getDrops().add(wolfData.getArmor().getItem());
                    }
                    if (wolfData.getChest() != null) {
                        wolfData.getChest().wolfDeath();
                        e.getDrops().add(wolfData.getChest().getItem());
                    }
                    main.wolvesData.remove(wolfUUID);
                    main.wolvesYAML.getConfig().set(wolfUUID, null);
                    main.wolvesYAML.SaveFile();
                }
            }
        }
    }
}
