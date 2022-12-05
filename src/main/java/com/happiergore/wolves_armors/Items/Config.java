package com.happiergore.wolves_armors.Items;

import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class Config {

    public static final List<Armors> armorsLoaded = new ArrayList<>();
    public static final List<Chests> chestsLoaded = new ArrayList<>();

    public static void reloadConfig() {
        armorsLoaded.clear();
        chestsLoaded.clear();
        main.wolvesData.clear();
        loadArmors();
        loadWolves();
        loadChests();
    }

    private static void loadArmors() {
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
    }

    private static void loadWolves() {
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
    }

    private static void loadChests() {
        main.configYML.getConfigurationSection("Chests").getKeys(true).forEach(itm -> {
            if (!itm.contains(".")) {
                String path = "Chests." + itm;
                String identifier = itm;
                String displayname = main.configYML.getString(path + ".Displayname");
                int slotsUnlocked = Integer.parseInt(main.configYML.getString(path + ".Slots"));
                int timesOpenAllowd = Integer.parseInt(main.configYML.getString(path + ".CanOpenAfterDamaged"));
                String material = main.configYML.getString(path + ".Item");
                List<String> lore = main.configYML.getStringList(path + ".Lore");
                chestsLoaded.add(new Chests(identifier, material, lore, displayname, slotsUnlocked, timesOpenAllowd));
            }
        });
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
        NBTItem nbtItem = new NBTItem(armor);
        String identifier = nbtItem.getString("Wolves_Armor_Identifier");
        if (!identifier.isBlank()) {
            for (Armors armors : Config.armorsLoaded) {
                if (armors.getIdentifier().equalsIgnoreCase(identifier)) {
                    return armors;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve un objeto de tipo Chests, el cual será validado en base al NBT
     * "Wolves_Armor_Identifier", sino encuentra nada, devolverá null
     *
     * @param chest Cofre a evaluar
     * @return
     */
    public static Chests getValidChest(ItemStack chest) {
        if (chest == null || chest.getType() == Material.AIR) {
            return null;
        }
        NBTItem nbtItem = new NBTItem(chest);
        String identifier = nbtItem.getString("Wolves_Armor_Identifier");
        if (!identifier.isBlank()) {
            for (Chests chests : Config.chestsLoaded) {
                if (chests.getIdentifier().equalsIgnoreCase(identifier)) {
                    return chests;
                }
            }
        }
        return null;
    }
}
