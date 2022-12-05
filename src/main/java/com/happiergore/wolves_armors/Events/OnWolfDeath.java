/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
                    main.wolvesData.remove(wolfUUID);
                    main.wolvesYAML.getConfig().set(wolfUUID, null);
                    main.wolvesYAML.SaveFile();
                }
            }
        }
    }
}
