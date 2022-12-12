package com.happiergore.wolves_armors.Events;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author HappieGore
 */
public class OnBlockPlaceEvent {

    public static void listen(BlockPlaceEvent e) {
        if (e.getPlayer() != null) {
            NBTItem nbtItem = new NBTItem(e.getItemInHand());
            if (nbtItem.hasKey("Wolves_Armor_Identifier")) {
                e.setCancelled(true);
            }
        }
    }
}
