package com.happiergore.wolves_armors.GUI.Armor;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.MainMenu;
import com.happiergore.wolves_armors.main;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

//Cambiar forma de guardado por UUID : DATA
/**
 *
 * @author HappieGore
 */
public class ArmorsIcon extends Behaviour {

    public ArmorsIcon(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        //Return the item and deleting it
        if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
            WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;
            ItemStack recoveredItem = wolfData.getArmor().getItem();

            e.setCursor(recoveredItem);

            wolfData.setArmor(null);
            main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Armor", null);
            main.wolvesYAML.SaveFile();
            this.getGUI().updateInventory();
            e.setCancelled(true);
        } else {
            e.setCancelled(true);
            this.getGUI().getPlayer().get().closeInventory();
            this.getGUI().getPlayer().sendColoredMsg("&cTo remove an armor, you must not have any items in your cursor.");
        }
    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        return ((MainMenu) this.getGUI()).wolfData.getArmor().getItem();
    }
}
