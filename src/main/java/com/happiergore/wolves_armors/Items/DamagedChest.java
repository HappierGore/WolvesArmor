package com.happiergore.wolves_armors.Items;

import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

/**
 * Esta clase permite crear un objeto de tipo armadura. Este contiene datos como
 * la durabilidad que se ha perdido y el lobo al que pertenece.
 *
 * @author HappieGore
 */
public class DamagedChest implements Serializable {

    private Chests type;
    private String chestUUID;
    private Map<Integer, ItemStack> content = new HashMap<>();
    private int timesOpened;
    private String wolfUUID;

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
    }

    /**
     * Crea un objeto en el momento en el que se da clic derecho a un lobo
     * domesticado o si un lobo domesticado con cofre muere
     *
     * @param chest Armadura
     * @param wolfUUID UUID del lobo en cuestión
     * @throws Exception Si no fue posible crearel cofrte
     */
    //Crear un objeto en el momento en el que se da clic derecho a un lobo
    //u obtienen daño
    public DamagedChest(ItemStack chest, String wolfUUID) throws Exception {
        this.type = this.tryGetChest(chest);
        NBTItem nbtItem = new NBTItem(chest);
        this.chestUUID = nbtItem.getString("Wolves_Armor_ChestUUID");
        this.wolfUUID = wolfUUID;
    }

    /**
     * Cuando un lobo muere y este tiene un cofre, se debe de registrar su
     * inventario
     *
     */
    public void wolfDeath() {
        this.chestUUID = UUID.randomUUID().toString();
        //Guardar el cofre en un archivo (inventario) y ponerle una UUID
    }

    /**
     * Crea un objeto de tipo ItemStack y lo retorna
     *
     * @return Item con sus propiedades actualizadas.
     */
    public ItemStack getItem() {
        NBTItem nbtItem = new NBTItem(this.type.getItem());
        if (!this.wolfUUID.isBlank()) {
            nbtItem.setString("Wolves_Armor_WolfUUID", wolfUUID);
        }
        if (!this.chestUUID.isBlank()) {
            nbtItem.setString("Wolves_Armor_ChestUUID", wolfUUID);
        }

        ItemStack item = nbtItem.getItem();
        for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
            item.getItemMeta().getLore().set(i, item.getItemMeta().getLore().get(i).
                    replace("${remaining_opens}",
                            String.valueOf(this.type.getTimesAllowedToOpen() - this.timesOpened)));
        }

        return nbtItem.getItem();
    }

    //------------------------------------
    //      Private methods
    //------------------------------------
    private Chests tryGetChest(ItemStack chest) throws Exception {
        Chests chests = Config.getValidChest(chest);
        if (chests == null) {
            throw new Exception("The item (" + chest.getType() + ")"
                    + "is not a valid chest.");
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

    public Map<Integer, ItemStack> getContent() {
        return content;
    }

    public void setContent(Map<Integer, ItemStack> content) {
        this.content = content;
    }

    public int getTimesOpened() {
        return timesOpened;
    }

    public void setTimesOpened(int timesOpened) {
        this.timesOpened = timesOpened;
    }

}
