package com.happiergore.wolves_armors.GUI.Behaviour;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.MainMenu;
import com.happiergore.wolves_armors.Items.Behaviour.Behaviours;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import java.io.Serializable;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class NeutralMode extends Behaviour implements Serializable {

    public NeutralMode(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;
        wolfData.setBehaviour(Behaviours.AGRESSIVE);

        main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Behaviour", Serializers.serialize(wolfData.getBehaviour()));
        this.getGUI().updateInventory();

        main.wolvesData.put(wolfData.getUUID(), wolfData);
        main.wolvesYAML.SaveFile();
        e.setCancelled(true);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public ItemStack generateMainItem() {
        String itemPath = "OtherItems.NeutralMode";
        Material material = Material.getMaterial(main.configYML.getString(itemPath + ".Item"));
        String displayname = main.configYML.getString(itemPath + ".Displayname");
        List<String> lore = main.configYML.getStringList(itemPath + ".Lore");
        return new ItemUtils().generateItem(null, material, displayname, lore, null);
    }

}
