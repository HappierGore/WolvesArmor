package com.happiergore.wolves_armors.Items;

import com.happiergore.wolves_armors.Utils.Serializers;
import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class Armors implements Serializable {

    private int protection;
    private int durability;
    private int maxDurability;
    private String itemSerialized;
    private String itemIdentifier;

    public Armors(int protection, int durability, int maxDurability, String itemIdentifier, ItemStack item) {
        this.protection = protection;
        this.durability = durability;
        this.maxDurability = maxDurability;
        this.itemIdentifier = itemIdentifier;
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("Wolves_Armors", true);
        nbtItem.setInteger("Wolves_Armors_Protection", protection);
        nbtItem.setInteger("Wolves_Armors_Max_Durability", durability);
        nbtItem.setInteger("Wolves_Armors_Durability", durability);
        nbtItem.setString("Wolves_Armors_Identifier", itemIdentifier);
        this.itemSerialized = Serializers.serializeItem(nbtItem.getItem());
    }

    public Armors(int protection, int durability, int maxDurability, String itemIdentifier, String itemSerialized) {
        this.protection = protection;
        this.durability = durability;
        this.maxDurability = maxDurability;
        this.itemIdentifier = itemIdentifier;
        ItemStack item = Serializers.deserializeItem(itemSerialized);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("Wolves_Armors", true);
        nbtItem.setInteger("Wolves_Armors_Protection", protection);
        nbtItem.setInteger("Wolves_Armors_Max_Durability", durability);
        nbtItem.setInteger("Wolves_Armors_Durability", durability);
        nbtItem.setString("Wolves_Armors_Identifier", itemIdentifier);
        this.itemSerialized = Serializers.serializeItem(nbtItem.getItem());
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public String getItemSerialized() {
        return itemSerialized;
    }

    public void setItemSerialized(String itemSerialized) {
        this.itemSerialized = itemSerialized;
    }

    public int getProtection() {
        return protection;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public ItemStack getItem() {
        return Serializers.deserializeItem(this.itemSerialized);
    }

    public void setItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("Wolves_Armors", true);
        nbtItem.setInteger("Wolves_Armors_Protection", protection);
        nbtItem.setInteger("Wolves_Armors_Max_Durability", durability);
        nbtItem.setInteger("Wolves_Armors_Durability", durability);
        nbtItem.setString("Wolves_Armors_Identifier", itemIdentifier);
        this.itemSerialized = Serializers.serializeItem(nbtItem.getItem());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Armors{");
        sb.append("protection=").append(protection);
        sb.append(", durability=").append(durability);
        sb.append(", maxDurability=").append(maxDurability);
        sb.append(", itemIdentifier=").append(itemIdentifier);
        sb.append(", itemSerialized=").append(itemSerialized);
        sb.append('}');
        return sb.toString();
    }

}
