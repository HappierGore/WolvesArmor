package com.happiergore.wolves_armors.GUI;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Items.Armors;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.Utils.YAML.YAMLList;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class ArmorAllowed extends Behaviour {

    public ArmorAllowed(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getCursor().getType() == Material.AIR) {
            return;
        }

        NBTItem nbtItem = new NBTItem(e.getCursor());

        if (!nbtItem.hasKey("Wolves_Armors")) {
            return;
        }
        System.out.println("Valid item is entering...");

        int potection = nbtItem.getInteger("Wolves_Armors_Protection");
        int maxDurability = nbtItem.getInteger("Wolves_Armors_Max_Durability");
        int durability = nbtItem.getInteger("Wolves_Armors_Durability");
        String itemIdentifier = nbtItem.getString("Wolves_Armors_Identifier");

        Armors armor = new Armors(potection, durability, maxDurability, itemIdentifier, e.getCursor());
        WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;
        wolfData.setArmor(armor);

        System.out.println("New entry created: " + wolfData.toString());

        YAMLList list = main.wolvesYAML.getList("Wolves");
        if (list.addEntry(Serializers.serialize(wolfData))) {
            e.setCursor(null);
            main.wolvesData.put(wolfData.getEntityID(), wolfData);
            main.wolvesYAML.SaveFile();
            this.getGUI().updateInventory();

        }
        System.out.println("Wolves data:" + main.wolvesData.toString());
        
        e.setCancelled(true);

    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        String itemPath = "OtherItems." + (true ? "ArmorAllowed" : "ArmorNotAllowed");
        Material material = Material.getMaterial(main.configYML.getString(itemPath + ".Item"));
        String displayname = main.configYML.getString(itemPath + ".Displayname");
        List<String> lore = main.configYML.getStringList(itemPath + ".Lore");
        return new ItemUtils().generateItem(null, material, displayname, lore, null);
    }

}
