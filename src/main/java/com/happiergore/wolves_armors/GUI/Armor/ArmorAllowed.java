package com.happiergore.wolves_armors.GUI.Armor;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.ItemsTypes.Behaviour;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.GUI.MainMenu;
import com.happiergore.wolves_armors.Items.Armor;
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
public class ArmorAllowed extends Behaviour {

    public final boolean setArmor;

    public ArmorAllowed(GUI inventory) {
        super(inventory);
        this.setArmor = this.getGUI().getPlayer().get().hasPermission("wolves_armor_set_armor");
        this.loadMainItem();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (!this.setArmor) {
            e.setCancelled(setArmor);
            return;
        }
        if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }
        WolfData wolfData = ((MainMenu) this.getGUI()).wolfData;
        Armor armor;
        try {
            armor = new Armor(e.getCursor(), wolfData.getUUID());
        } catch (Exception ex) {
            e.setCancelled(true);
            this.getGUI().getPlayer().get().closeInventory();
            this.getGUI().getPlayer().sendColoredMsg("&c" + ex.getMessage());
            return;
        }

        wolfData.setArmor(armor);

        main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Armor", Serializers.serialize(armor));
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
        String itemPath = "OtherItems." + (setArmor ? "ArmorAllowed" : "ArmorNotAllowed");
        Material material = Material.getMaterial(main.configYML.getString(itemPath + ".Item"));
        String displayname = main.configYML.getString(itemPath + ".Displayname");
        List<String> lore = main.configYML.getStringList(itemPath + ".Lore");
        return new ItemUtils().generateItem(null, material, displayname, lore, null);
    }

}
