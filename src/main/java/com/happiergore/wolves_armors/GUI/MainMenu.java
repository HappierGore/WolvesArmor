package com.happiergore.wolves_armors.GUI;

import com.happiergore.wolves_armors.GUI.Armor.ArmorsIcon;
import com.happiergore.wolves_armors.GUI.Armor.ArmorAllowed;
import com.happiergore.menusapi.GUI;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.Chest.ChestAllowed;
import com.happiergore.wolves_armors.GUI.Chest.ChestsIcon;
import com.happiergore.wolves_armors.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappieGore
 */
public class MainMenu extends GUI {

    public final WolfData wolfData;

    public MainMenu(Player player, String entityUUID) {
        super(player, main.configYML.getString("wolf_inventory_name"));
        if (main.wolvesData.containsKey(entityUUID)) {
            this.wolfData = main.wolvesData.get(entityUUID);
        } else {
            this.wolfData = new WolfData(entityUUID);
        }
        this.setInventory(Bukkit.createInventory((GUI) this, 9, this.INVENTORY_TITLE));
    }

    @Override
    public void onClose(InventoryCloseEvent ice) {
    }

    @Override
    public void updateInventory() {
        this.getItems().clear();
        this.onOpen();
    }

    @Override
    public void registerButtons() {
        if (wolfData.getArmor() == null) {
            this.registerBtn(4, new ArmorAllowed(this));
        } else {
            this.registerBtn(4, new ArmorsIcon(this));
        }
        if (wolfData.getChest() == null) {
            this.registerBtn(8, new ChestAllowed(this));
        } else {
            this.registerBtn(8, new ChestsIcon(this));
        }
    }

}
