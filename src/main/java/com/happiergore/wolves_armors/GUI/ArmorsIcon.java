package com.happiergore.wolves_armors.GUI;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Items.Armors;
import com.happiergore.wolves_armors.Items.ArmorsLoader;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.Utils.YAML.YAMLList;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

        WolfData olderData = ((MainMenu) this.getGUI()).wolfData;
        WolfData newData = olderData.getClone();
        newData.setArmor(null);

        ItemStack recoveredItem = olderData.getArmor().getItem();

        e.setCursor(recoveredItem);

        YAMLList list = main.wolvesYAML.getList("Wolves");
        if (list.replaceEntry(Serializers.serialize(olderData), Serializers.serialize(newData))) {
            main.wolvesData.remove(olderData.getEntityID());
            main.wolvesData.put(newData.getEntityID(), newData);
            main.wolvesYAML.SaveFile();
            this.getGUI().updateInventory();
        }
    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        String itemPath = "Armors.Leather";
        Material material = Material.getMaterial(main.configYML.getString(itemPath + ".Item"));
        String displayname = main.configYML.getString(itemPath + ".Displayname");
        List<String> lore = main.configYML.getStringList(itemPath + ".Lore");
        return new ItemUtils().generateItem(null, material, displayname, lore, null);
    }
}
