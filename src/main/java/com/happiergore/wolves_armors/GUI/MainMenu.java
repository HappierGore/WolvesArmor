package com.happiergore.wolves_armors.GUI;

import com.happiergore.menusapi.GUI;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappieGore
 */
public class MainMenu extends GUI {

    WolfData wolfData;

    public MainMenu(Player player, String entityUUID) {
        super(player, main.configYML.getString("wolf_inventory_name"));
        if (main.wolvesData.containsKey(entityUUID)) {
            System.out.println("There was a register of this entity (" + entityUUID + ")");
            this.wolfData = main.wolvesData.get(entityUUID);
        } else {
            System.out.println("It is a new entry! Creating one with ID: " + entityUUID);
            this.wolfData = new WolfData(entityUUID);
        }
        this.setInventory(Bukkit.createInventory((GUI) this, 9, this.INVENTORY_TITLE));
    }

    @Override
    public void onClose(InventoryCloseEvent ice) {
    }

    @Override
    public void updateInventory() {
        System.out.println("Items before clear");
        getItems().forEach((t, v) -> {
            System.out.println(t + ", " + v.getItem().toString());
        });
        this.getItems().clear();
        System.out.println("Items after clear");
        getItems().forEach((t, v) -> {
            System.out.println(t + ", " + v.getItem().toString());
        });
        this.onOpen();
        System.out.println("Items after open again:" + getItems().toString());
        getItems().forEach((t, v) -> {
            System.out.println(t + ", " + v.getItem().toString());
        });
    }

    @Override
    public void registerButtons() {
        if (wolfData.getArmor() == null) {
            this.registerBtn(8, new ArmorAllowed(this));
        } else {
            this.registerBtn(8, new ArmorsIcon(this));
        }
        this.registerBtn(4, new ArmorAllowed(this));
        this.registerBtn(1, new ArmorsIcon(this));
    }

}
