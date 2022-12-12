package com.happiergore.wolves_armors.Items.Armor;

import com.happiergore.menusapi.Utils.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Representa una armadura desde la configuración del usuario.
 *
 * @author HappieGore
 */
public class Armors implements Serializable {

    private int protection;
    private int durability;
    private String item;
    private List<String> lore;
    private String displayName;
    private String identifier;

    public Armors(String identifier, String item, List<String> lore, String displayname, int protection, int durability) {
        this.protection = protection;
        this.durability = durability;
        this.item = item;
        this.lore = lore;
        this.displayName = displayname;
        this.identifier = identifier;
    }

    /**
     * Retorna un nuevo item, tomando ya sus datos principales, como lore y
     * displayname
     *
     * @param asNew false para hacer que no sustituya el valor del placeholder
     * "placeholder"
     * @return ItemStack
     */
    public ItemStack getItem(boolean asNew) {
        Material material = Material.getMaterial(item);
        if (material == null) {
            material = Material.BEDROCK;
            lore.clear();
            lore.add("&7The item type specified (" + this.item + ")");
            lore.add("&7doesn't exists.");
            lore.add("&7Check your config.yml on " + this.identifier + " armor.");
            displayName = "&cError";
        }

        List<String> copyLore = new ArrayList<String>() {
            {
                addAll(lore);
            }
        };

//replace placeholders
        for (int i = 0; i < copyLore.size(); i++) {
            copyLore.set(i, copyLore.get(i).replace("${durability}", String.valueOf(this.durability)));
            if (asNew) {
                copyLore.set(i, copyLore.get(i).replace("${lostedDurability}", String.valueOf(this.durability)));
            }
            copyLore.set(i, copyLore.get(i).replace("${protection}", String.valueOf(this.protection)));
        }

        NBTItem nbtItem = new NBTItem(new ItemUtils().generateItem(null, material, displayName, copyLore, null));
        nbtItem.setString("Wolves_Armor_Identifier", this.identifier);
        return nbtItem.getItem();
    }

    //------------------------------------
    //          Getters & Setters
    //------------------------------------
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

    public void setItem(String item) {
        this.item = item;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
