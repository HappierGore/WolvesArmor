package com.happiergore.wolves_armors.GUI.Chest;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.MainMenu;
import com.happiergore.wolves_armors.Items.DamagedChest;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class ChestAllowed extends Behaviour {

    public ChestAllowed(GUI inventory) {
        super(inventory);
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }
        WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;
        DamagedChest chest;
        try {
            chest = new DamagedChest(e.getCursor(), wolfData.getUUID());
        } catch (Exception ex) {
            e.setCancelled(true);
            this.getGUI().getPlayer().get().closeInventory();
            this.getGUI().getPlayer().sendColoredMsg("&c" + ex.getMessage());
            return;
        }

        wolfData.setChest(chest);

        main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Chest", Serializers.serialize(chest));
        e.setCursor(null);
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
        String itemPath = "OtherItems." + (true ? "ChestAllowed" : "ChestNotAllowed");
        Material material = Material.getMaterial(main.configYML.getString(itemPath + ".Item"));
        String displayname = main.configYML.getString(itemPath + ".Displayname");
        List<String> lore = main.configYML.getStringList(itemPath + ".Lore");
        return new ItemUtils().generateItem(null, material, displayname, lore, null);
    }

}
