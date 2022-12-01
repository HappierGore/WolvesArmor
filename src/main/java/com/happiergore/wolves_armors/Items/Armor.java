package com.happiergore.wolves_armors.Items;

import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import org.bukkit.inventory.ItemStack;

/**
 * Esta clase permite crear un objeto de tipo armadura. Este contiene datos como
 * la durabilidad que se ha perdido y el lobo al que pertenece.
 *
 * @author HappieGore
 */
public class Armor implements Serializable {

    private Armors type;
    private int durabilityReduced;
    private String wolfUUID;

    /**
     * Crear un objeto en base a uno que ya exista(Desde MC) y no desde el lobo.
     * Aqui se supone que ya tiene en el NBT de la durabilidad reducida y el
     * lobo al que pertenece
     *
     * @param armor Armadura
     * @throws Exception Si no fue posible crear la armadura
     */
    public Armor(ItemStack armor) throws Exception {
        this.type = this.tryGetArmor(armor);
        NBTItem nbtItem = new NBTItem(armor);
        this.durabilityReduced = nbtItem.getInteger("Wolves_Armor_Durability_Reduced");
        this.wolfUUID = nbtItem.getString("Wolves_Armor_WolfUUID");
    }

    /**
     * Crea un objeto en el momento en el que se da clic derecho a un lobo
     * domesticado o si un lobo domesticado con armadura obtiene daño
     *
     * @param armor Armadura
     * @param wolfUUID UUID del lobo en cuestión
     * @throws Exception Si no fue posible crear la armadura
     */
    //Crear un objeto en el momento en el que se da clic derecho a un lobo
    //u obtienen daño
    public Armor(ItemStack armor, String wolfUUID) throws Exception {
        this.type = this.tryGetArmor(armor);
        NBTItem nbtItem = new NBTItem(armor);
        this.durabilityReduced = nbtItem.getInteger("Wolves_Armor_Durability_Reduced");
        this.wolfUUID = wolfUUID;
    }

    /**
     * Ocasiona daño a la armadura y verifica que aún no se haya roto.
     *
     * @param damage Daño que la armadura va a recibir
     * @return true si la armadura aun no se rompe, false si esta rota.
     */
    public boolean damageArmor(int damage) {
        this.durabilityReduced += damage;
        return durabilityReduced <= type.getDurability();
    }

    /**
     * Crea un objeto de tipo ItemStack y lo retorna
     *
     * @return Item con sus propiedades actualizadas.
     */
    public ItemStack getItem() {
        NBTItem nbtItem = new NBTItem(this.type.getItem());
        nbtItem.setInteger("Wolves_Armor_Durability_Reduced", this.durabilityReduced);
        if (!this.wolfUUID.isBlank()) {
            nbtItem.setString("Wolves_Armor_WolfUUID", wolfUUID);
        }
        return nbtItem.getItem();
    }

    //------------------------------------
    //      Private methods
    //------------------------------------
    private Armors tryGetArmor(ItemStack armor) throws Exception {
        Armors armors = Config.getValidArmors(armor);
        if (armors == null) {
            throw new Exception("The item (" + armor.getType() + ")"
                    + "is not a valid armor.");
        }
        return armors;
    }

    //------------------------------------
    //          Getters & Setters
    //------------------------------------
    public Armors getType() {
        return type;
    }

    public void setType(Armors type) {
        this.type = type;
    }

    public int getDurabilityReduced() {
        return durabilityReduced;
    }

    public void setDurabilityReduced(int durabilityReduced) {
        this.durabilityReduced = durabilityReduced;
    }

    public String getWolfUUID() {
        return wolfUUID;
    }

    public void setWolfUUID(String wolfUUID) {
        this.wolfUUID = wolfUUID;
    }

}
