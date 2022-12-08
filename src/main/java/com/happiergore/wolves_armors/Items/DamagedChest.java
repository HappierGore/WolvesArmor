package com.happiergore.wolves_armors.Items;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.Utils.TextUtils;
import com.happiergore.wolves_armors.GUI.WolfInventory;
import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        //Guardar el cofre en un archivo (inventario) y ponerle una UUID
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
            this.timesOpened++;
            //Recuperar inventario por UUID y abrirlo
        }
        gui.open();
        return this.timesOpened > type.getTimesAllowedToOpen();
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

    public void updateChest() {
        try {
            this.type = this.tryGetChest(getItem());
        } catch (Exception ex) {
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
