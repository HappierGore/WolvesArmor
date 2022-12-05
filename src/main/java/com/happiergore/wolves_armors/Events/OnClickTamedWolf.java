package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.GUI.MainMenu;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 *
 * @author HappieGore
 */
public class OnClickTamedWolf {

    public static void listen(PlayerInteractAtEntityEvent e) {
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        if (e.getRightClicked().getType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) e.getRightClicked();
            if (!wolf.isTamed()
                    || !wolf.getOwner().getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
                return;
            }

            System.out.println("The owner of " + wolf.getName() + "(" + wolf.getUniqueId().toString() + ")" + " clicked upon it");
            e.setCancelled(true);
            new MainMenu(e.getPlayer(), wolf.getUniqueId().toString()).open();
        }
    }
}
