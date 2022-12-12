package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.GUI.MainMenu;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 *
 * @author HappieGore
 */
public class OnWolfTamedClick {

    public static void listen(PlayerInteractEntityEvent e) {
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        if (e.getRightClicked().getType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) e.getRightClicked();

            if (!wolf.isTamed()) {
                return;
            }
            String ownerUUID = wolf.getOwner().getUniqueId().toString();
            String whoClicksUUID = e.getPlayer().getUniqueId().toString();

            boolean seeOther = e.getPlayer().hasPermission("wolves_armor_see_others");
            boolean editOthers = e.getPlayer().hasPermission("wolves_armor_edit_others");
            boolean isOwner = ownerUUID.equals(whoClicksUUID);

            if (editOthers || isOwner) {
                e.setCancelled(true);
                new MainMenu(e.getPlayer(), wolf.getUniqueId().toString(), false).open();
            } else if (seeOther) {
                e.setCancelled(true);
                new MainMenu(e.getPlayer(), wolf.getUniqueId().toString(), true).open();
            }
        }
    }
}
