package com.happiergore.wolves_armors.Items;

import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.Utils.YAML.YAMLList;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class Config {

    public static final List<Armors> armorsLoaded = new ArrayList<>();

    public static void reloadConfig() {
        loadArmors();
    }

    private static boolean loadArmors() {
        main.configYML.getConfigurationSection("Armors").getKeys(true).forEach(itm -> {
            if (!itm.contains(".")) {
                String path = "Armors." + itm;
                //New entry
                String identifier = itm;
                String displayname = main.configYML.getString(path + ".Displayname");
                int protection = Integer.parseInt(main.configYML.getString(path + ".Protection").replace("%", ""));
                int durability = Integer.parseInt(main.configYML.getString(path + ".Durability"));
                String material = main.configYML.getString(path + ".Item");
                List<String> lore = main.configYML.getStringList(path + ".Lore");
                armorsLoaded.add(new Armors(identifier, material, lore, displayname, protection, durability));
            }
        });

        main.wolvesYAML.getConfig().getKeys(true).forEach(itm -> {
            System.out.println("itm:" + itm);
            if (!itm.contains(".")) {
                String wolfUUID = itm;
                WolfData wolfData = new WolfData(wolfUUID);
                String serializedArmor = main.wolvesYAML.getConfig().getString(wolfUUID + ".Armor");
                if (serializedArmor != null) {
                    wolfData.setArmor((Armor) Serializers.deserialize(serializedArmor));
                }
                main.wolvesData.put(wolfUUID, wolfData);
            }
        });
        return true;
    }

    /**
     * Devuelve un objeto de tipo Armors, el cual será validado en base al NBT
     * "Wolves_Armor_Identifier", sino encuentra nada, devolverá null
     *
     * @param armor Armadura a evaluar
     * @return
     */
    public static Armors getValidArmors(ItemStack armor) {
        if (armor == null || armor.getType() == Material.AIR) {
            return null;
        }
        for (Armors armors : Config.armorsLoaded) {
            NBTItem nbtItem = new NBTItem(armor);
            String identifier = nbtItem.getString("Wolves_Armor_Identifier");
            if (identifier.isBlank()) {
                continue;
            }
            if (armors.getIdentifier().equalsIgnoreCase(identifier)) {
                return armors;
            }
        }
        return null;
    }
}
