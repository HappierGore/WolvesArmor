package com.happiergore.wolves_armors.Events;

import com.happiergore.wolves_armors.GUI.MainMenu;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 *
 * @author HappieGore
 */
public class OnDragItem {

    public static void listen(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof MainMenu) {
            e.setCancelled(true);
        }
    }
}
