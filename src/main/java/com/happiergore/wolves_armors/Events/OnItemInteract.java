package com.happiergore.wolves_armors.Events;

import com.happiergore.menusapi.Utils.PlayerUtils;
import com.happiergore.wolves_armors.Items.Chest.DamagedChest;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author HappieGore
 */
public class OnItemInteract {

    public static void listen(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }

        NBTItem nbtItem = new NBTItem(e.getItem());

        if (nbtItem.getBoolean("Wolves_Armor_WolfDeath")) {
            String key = nbtItem.getString("Wolves_Armor_ChestUUID");
            DamagedChest chest = (DamagedChest) Serializers.deserialize(main.chestData.getConfig().getString(key));
            chest.updateChest();
            if (!chest.openChest(e.getPlayer())) {
                try {
                    String sound = main.configYML.getString("Sounds.chestBroken.sound");
                    int pitch = main.configYML.getInt("Sounds.chestBroken.pitch");
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(sound), 1.0f, pitch);
                } catch (Exception ex) {
                    PlayerUtils playerUtils = new PlayerUtils(e.getPlayer());
                    playerUtils.sendColoredMsg("&cThe sound of &nchestBroken&r &cfrom config.yml is not valid.");
                    ex.printStackTrace(System.err);
                }
                e.getPlayer().getInventory().setItemInHand(null);
            }
        }
    }
}
