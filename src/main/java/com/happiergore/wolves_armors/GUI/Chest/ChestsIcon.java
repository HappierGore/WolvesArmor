package com.happiergore.wolves_armors.GUI.Chest;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.MainMenu;
import com.happiergore.wolves_armors.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

//Cambiar forma de guardado por UUID : DATA
/**
 *
 * @author HappieGore
 */
public class ChestsIcon extends Behaviour {

    public ChestsIcon(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        //Return the item and deleting it
        WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;

        if (e.isLeftClick()) {
            if (e.getCursor().getType() == Material.AIR) {
                int requiredSlots = wolfData.getChest().returnItems(this.getGUI().getPlayer().get());
                switch (requiredSlots) {
                    case 0:
                    case -1: {
                        ItemStack recoveredItem = wolfData.getChest().getItem();
                        e.setCursor(recoveredItem);
                        wolfData.setChest(null);
                        main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Chest", null);
                        main.wolvesYAML.SaveFile();
                        this.getGUI().updateInventory();
                        e.setCancelled(true);
                        break;
                    }
                    default: {
                        e.setCancelled(true);
                        this.getGUI().getPlayer().get().closeInventory();
                        String msg = "&cTo remove a chest, you must not have any items in your cursor";
                        if (requiredSlots > 0) {
                            msg += " and have at least &n" + ++requiredSlots + "&r&c free slots in your inventory to claim the items inside.";
                        }
                        this.getGUI().getPlayer().sendColoredMsg(msg);
                        break;
                    }
                }
            }
        } else if (e.isRightClick()) {
            wolfData.getChest().openChest((Player) e.getWhoClicked());
        }
    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        return ((MainMenu) this.getGUI()).wolfData.getChest().getItem();
    }
}
