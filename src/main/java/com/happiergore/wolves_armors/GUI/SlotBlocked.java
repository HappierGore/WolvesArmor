package com.happiergore.wolves_armors.GUI;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.main;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

//Cambiar forma de guardado por UUID : DATA
/**
 *
 * @author HappieGore
 */
public class SlotBlocked extends Behaviour {

    public SlotBlocked(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        String path = "OtherItems.SlotBlocked";
        String displayName = main.configYML.getString(path + ".Displayname");
        Material material = Material.getMaterial(main.configYML.getString(path + ".Item"));
        List<String> lore = main.configYML.getStringList(path + ".Lore");
        return new ItemUtils().generateItem(null, material, displayName, lore, null);
    }
}
