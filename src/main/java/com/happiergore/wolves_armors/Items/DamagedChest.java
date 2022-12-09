package com.happiergore.wolves_armors.Items;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.Utils.PlayerUtils;
import com.happiergore.menusapi.Utils.TextUtils;
import com.happiergore.wolves_armors.GUI.WolfInventory;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Esta clase permite crear un objeto de tipo armadura. Este contiene datos como
 * la durabilidad que se ha perdido y el lobo al que pertenece.
 *
 * @author HappieGore
 */
public class DamagedChest implements Serializable {

    private Chests type;
    private String chestUUID;
    public final Map<Integer, String> content = new HashMap<>();
    private int timesOpened;
    private String wolfUUID;
    private boolean wolfDeath;

    /**
     * Crear un objeto en base a uno que ya exista(Desde MC) y no desde el lobo.
     * Aqui se supone que ya tiene en el NBT de la durabilidad reducida y el
     * lobo al que pertenece
     *
     * @param chest Armadura
     * @throws Exception Si no fue posible crear el cofre
     */
    public DamagedChest(ItemStack chest) throws Exception {
        this.type = this.tryGetChest(chest);
        NBTItem nbtItem = new NBTItem(chest);
        this.wolfUUID = nbtItem.getString("Wolves_Armor_WolfUUID");
        this.chestUUID = nbtItem.getString("Wolves_Armor_ChestUUID");
        this.wolfDeath = nbtItem.getBoolean("Wolves_Armor_WolfDeath");
    }

    /**
     * Crea un objeto en el momento en el que se da clic derecho a un lobo
     * domesticado o si un lobo domesticado con cofre muere
     *
     * @param chest Armadura
     * @param wolfUUID UUID del lobo en cuestión
     * @throws Exception Si no fue posible crearel cofrte
     */
    public DamagedChest(ItemStack chest, String wolfUUID) throws Exception {
        this.type = this.tryGetChest(chest);
        this.wolfUUID = wolfUUID;

        NBTItem nbtItem = new NBTItem(chest);

        if (nbtItem.hasKey("Wolves_Armor_ChestUUID")) {
            this.chestUUID = nbtItem.getString("Wolves_Armor_ChestUUID");
        } else {
            this.chestUUID = UUID.randomUUID().toString();
        }
        this.wolfDeath = nbtItem.getBoolean("Wolves_Armor_WolfDeath");
    }

    /**
     * Cuando un lobo muere y este tiene un cofre, se debe de registrar su
     * inventario
     *
     */
    public void wolfDeath() {
        this.wolfDeath = true;
        String serializedChest = Serializers.serialize(this);
        main.chestData.getConfig().set(chestUUID, serializedChest);
        main.chestData.SaveFile();
    }

    /**
     * Cuando se abra el cofre y este haya alcanzado el número máximo de veces
     * permitidas abrir, retornará falso
     *
     * @param player Jugador a quien se le abrirá el inventario
     * @return falso si alcanzó su límite de abrir
     */
    public boolean openChest(Player player) {
        GUI gui = new WolfInventory(player, chestUUID, this);
        if (this.wolfDeath) {
            try {
                String sound = main.configYML.getString("Sounds.openDamagedChest.sound");
                int pitch = main.configYML.getInt("Sounds.openDamagedChest.pitch");
                player.playSound(player.getLocation(), Sound.valueOf(sound),
                        1.0f, pitch);
            } catch (Exception ex) {
                PlayerUtils playerUtils = new PlayerUtils(player);
                playerUtils.sendColoredMsg("&cThe sound of &NopenDamagedChest&r &cfrom config.yml is not valid.");
                ex.printStackTrace(System.err);
            }
            this.timesOpened++;
            player.getInventory().setItemInHand(this.getItem());
        } else {
            try {
                String sound = main.configYML.getString("Sounds.openNormalChest.sound");
                int pitch = main.configYML.getInt("Sounds.openNormalChest.pitch");
                player.playSound(player.getLocation(), Sound.valueOf(sound),
                        1.0f, pitch);
            } catch (Exception ex) {
                PlayerUtils playerUtils = new PlayerUtils(player);
                playerUtils.sendColoredMsg("&cThe sound of &nopenNormalChest&r &cfrom config.yml is not valid.");
                ex.printStackTrace(System.err);
            }
        }
        gui.open();
        return this.timesOpened < type.getTimesAllowedToOpen();
    }

    /**
     * Crea un objeto de tipo ItemStack y lo retorna
     *
     * @return Item con sus propiedades actualizadas.
     */
    public ItemStack getItem() {
        NBTItem nbtItem = new NBTItem(this.type.getItem());
        nbtItem.setString("Wolves_Armor_WolfUUID", wolfUUID);
        nbtItem.setString("Wolves_Armor_ChestUUID", chestUUID);
        nbtItem.setBoolean("Wolves_Armor_WolfDeath", wolfDeath);

        ItemStack item = nbtItem.getItem();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> copyLore = new ArrayList<String>() {
            {
                addAll(itemMeta.getLore());
            }
        };

        if (this.wolfDeath) {
            item = nbtItem.getItem();
            copyLore.add("");
            copyLore.addAll(type.getAlternativeLore());
        }

        for (int i = 0; i < copyLore.size(); i++) {
            copyLore.set(i, copyLore.get(i).
                    replace("${remaining_opens}",
                            String.valueOf(this.type.getTimesAllowedToOpen() - this.timesOpened)));
        }
        copyLore = new TextUtils().parseColor(copyLore);
        itemMeta.setLore(copyLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Actualizará el tipo del cofre, logrando así mantener al día las
     * configuraciones hechas por el usuario desde confiy.yml
     */
    public void updateChest() {
        try {
            this.type = this.tryGetChest(getItem());
        } catch (Exception ex) {
        }
    }

    /**
     * Permitirá regresar los items almacenados hacia el jugador
     *
     * @param player Jugador a quien se devolverán los items
     * @return 0 - Todo correcto, todos los items fueron devueltos != 0 -
     * Cantidad de slots necesarios, -1 si no hay items que regresar
     */
    public int returnItems(Player player) {
        int emptySlots = 0;
        int requiredSlots = this.content.size();

        if (requiredSlots == 0) {
            return -1;
        }

        ItemStack[] playerInv = player.getInventory().getContents();
        for (int i = 0; i < playerInv.length - 5; i++) {
            if (playerInv[i] == null) {
                emptySlots++;
            }
        }

        if (emptySlots > requiredSlots) {
            //Regresar items si es que hay
            this.content.values().forEach(itm -> player.getInventory().
                    addItem(Serializers.deserializeItem(itm)));
            return 0;

        } else {
            return requiredSlots;
        }
    }

    //------------------------------------
    //      Private methods
    //------------------------------------
    private Chests tryGetChest(ItemStack chest) throws Exception {
        Chests chests = Config.getValidChest(chest);
        if (chests == null) {
            throw new Exception("The item (" + chest.getType() + ")"
                    + " is not a valid chest.");
        }
        return chests;
    }

    //------------------------------------
    //          Getters & Setters
    //------------------------------------
    public Chests getType() {
        return type;
    }

    public void setType(Chests type) {
        this.type = type;
    }

    public String getWolfUUID() {
        return wolfUUID;
    }

    public void setWolfUUID(String wolfUUID) {
        this.wolfUUID = wolfUUID;
    }

    public String getChestUUID() {
        return chestUUID;
    }

    public void setChestUUID(String chestUUID) {
        this.chestUUID = chestUUID;
    }

    public int getTimesOpened() {
        return timesOpened;
    }

    public void setTimesOpened(int timesOpened) {
        this.timesOpened = timesOpened;
    }

    public boolean isWolfDeath() {
        return wolfDeath;
    }

    public void setWolfDeath(boolean wolfDeath) {
        this.wolfDeath = wolfDeath;
    }

}
