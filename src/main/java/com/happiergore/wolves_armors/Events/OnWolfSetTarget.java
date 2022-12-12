/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Items.Behaviour.Behaviours;
import com.happiergore.wolves_armors.main;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author HappieGore
 */
public class OnWolfSetTarget {

    public static void listen(EntityTargetEvent e) {
        if (e.getEntity() instanceof Wolf) {
            WolfData wolfData = main.wolvesData.get(e.getEntity().getUniqueId().toString());
            if (wolfData == null || wolfData.getBehaviour() == Behaviours.AGRESSIVE || e.getTarget() == null) {
                return;
            }
            switch (wolfData.getBehaviour()) {
                case PASSIVE: {
                    e.setCancelled(true);
                    break;
                }
                case NEUTRAL: {
                    if (wolfData.getBehaviour().getBlackList().contains(e.getTarget().getType().toString())) {
                        e.setCancelled(true);
                    }
                    break;
                }

            }
        }
    }
}
