package com.happiergore.wolves_armors.GUI;

import com.happiergore.wolves_armors.GUI.Armor.ArmorsIcon;
import com.happiergore.wolves_armors.GUI.Armor.ArmorAllowed;
import com.happiergore.menusapi.GUI;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.Behaviour.AgressiveMode;
import com.happiergore.wolves_armors.GUI.Behaviour.NeutralMode;
import com.happiergore.wolves_armors.GUI.Behaviour.PassiveMode;
import com.happiergore.wolves_armors.GUI.Chest.ChestAllowed;
import com.happiergore.wolves_armors.GUI.Chest.ChestsIcon;
import com.happiergore.wolves_armors.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappieGore
 */
public class MainMenu extends GUI {

    public final WolfData wolfData;
    public final boolean onlyDisplay;

    public MainMenu(Player player, String entityUUID, boolean onlyDisplay) {
        super(player, main.configYML.getString("wolf_menu_name"));
        if (main.wolvesData.containsKey(entityUUID)) {
            this.wolfData = main.wolvesData.get(entityUUID);
        } else {
            this.wolfData = new WolfData(entityUUID);
        }
        this.setInventory(Bukkit.createInventory((GUI) this, 9, this.INVENTORY_TITLE));
        this.onlyDisplay = onlyDisplay;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (onlyDisplay) {
            e.setCancelled(true);
            return;
        }
        super.onInventoryClick(e);
        if (e.getClickedInventory() == this.getInventory()) {
            if (e.getCurrentItem() == null) {
                e.setCancelled(true);
            }
        }
        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            e.setCancelled(true);
        }
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

        switch (this.wolfData.getBehaviour()) {
            case NEUTRAL: {
                this.registerBtn(1, new NeutralMode(this));
                break;
            }
            case AGRESSIVE: {
                this.registerBtn(1, new AgressiveMode(this));
                break;
            }
            default: {
                this.registerBtn(1, new PassiveMode(this));
                break;
            }
        }

    }

}
