/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.wolves_armors.Events;

import com.happiergore.menusapi.Utils.PlayerUtils;
import com.happiergore.wolves_armors.Data.WolfData;
import com.happiergore.wolves_armors.Items.Armor;
import com.happiergore.wolves_armors.main;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author HappieGore
 */
public class OnWolfDamaged {

    public static void listen(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.WOLF) {
            Wolf wolf = (Wolf) e.getEntity();
            if (wolf.isTamed()) {
                //Revisar que el lobo esté registrado en la base de datos
                if (main.wolvesData.containsKey(wolf.getUniqueId().toString())) {
                    WolfData wolfData = main.wolvesData.get(wolf.getUniqueId().toString());
                    if (wolfData.getArmor() != null) {
                        Armor armor = wolfData.getArmor();
                        if (!armor.damageArmor(1)) {
                            wolfData.setArmor(null);
                            e.getEntity().playEffect(EntityEffect.WOLF_SMOKE);
                            Player player = (Player) wolf.getOwner();
                            try {
                                player.playSound(wolf.getLocation(),
                                        Sound.valueOf(main.configYML.getString("Sounds.armorBroken")),
                                        1.0f, 1.0f);
                            } catch (Exception ex) {
                                PlayerUtils playerUtils = new PlayerUtils(player);
                                playerUtils.sendColoredMsg("&cThe sound of &narmorBroken&r &c from config.yml is not valid.");
                            }
                            main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Armor", null);
                            main.wolvesYAML.SaveFile();
                        }
                    }
                }
            }
        }
    }
}
