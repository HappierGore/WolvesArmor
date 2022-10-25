package com.happiergore.wolves_armors.Items;

import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.Utils.YAML.YAMLList;
import com.happiergore.wolves_armors.main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class ArmorsLoader {

    public static List<Armors> armorsLoaded = new ArrayList<>();

    public final boolean load() {

        main.configYML.getConfigurationSection("Armors").getKeys(true).forEach(itm -> {
            if (!itm.contains(".")) {
                String path = "Armors." + itm;
                //New entry
                String displayname = main.configYML.getString(path + ".Displayname");
                int protection = Integer.parseInt(main.configYML.getString(path + ".Protection"));
                int durability = Integer.parseInt(main.configYML.getString(path + ".Durability"));
                Material material = Material.valueOf(main.configYML.getString(path + ".Item"));
                List<String> lore = main.configYML.getStringList(path + ".Lore");
                ItemStack item = new ItemUtils().generateItem(null, material, displayname, lore, null);
                armorsLoaded.add(new Armors(protection, durability, durability, itm, item));
            }
        });

        YAMLList savedWolves = main.wolvesYAML.getList("Wolves");
        savedWolves.getEntries().forEach(rawWolf -> {
            WolfData wolf = (WolfData) Serializers.deserialize(rawWolf);
            main.wolvesData.put(wolf.getEntityID(), wolf);
        });

        return true;
    }
}
