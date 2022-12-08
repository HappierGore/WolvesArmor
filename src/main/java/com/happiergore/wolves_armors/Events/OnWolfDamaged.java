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
                        Player player = (Player) wolf.getOwner();
                        //Si se rompe...
                        if (!armor.damageArmor(1)) {
                            wolfData.setArmor(null);
                            e.getEntity().playEffect(EntityEffect.WOLF_SMOKE);
                            try {
                                player.playSound(wolf.getLocation(),
                                        Sound.valueOf(main.configYML.getString("Sounds.armorBroken")),
                                        1.0f, 1.0f);
                            } catch (Exception ex) {
                                PlayerUtils playerUtils = new PlayerUtils(player);
                                playerUtils.sendColoredMsg("&cThe sound of &narmorBroken&r &cfrom config.yml is not valid.");
                            }
                            main.wolvesYAML.getConfig().set(wolfData.getUUID() + ".Armor", null);
                            main.wolvesYAML.SaveFile();
                        } else {
                            try {
                                player.playSound(wolf.getLocation(),
                                        Sound.valueOf(main.configYML.getString("Sounds.wolfDamagedWithArmor")),
                                        1.0f, 1.0f);
                            } catch (Exception ex) {
                                PlayerUtils playerUtils = new PlayerUtils(player);
                                playerUtils.sendColoredMsg("&cThe sound of &nwolfDamagedWithArmor&r &cfrom config.yml is not valid.");
                            }
                            double protection = Double.parseDouble(String.valueOf(armor.getType().getProtection())) / 100.0;
                            double realDamage = e.getDamage() * (1.0 - protection);
                            e.setDamage(realDamage);
                        }
                    }
                }
            }
        }
    }
}
