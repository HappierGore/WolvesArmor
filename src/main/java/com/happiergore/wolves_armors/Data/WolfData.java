package com.happiergore.wolves_armors.Data;

import com.happiergore.wolves_armors.Items.Armor;

/**
 *
 * @author HappieGore
 */
public class WolfData {

    private Armor armor;

    private final String UUID;

    public WolfData(String entityUUID) {
        this.UUID = entityUUID;
    }

    public WolfData getClone() {
        WolfData clon = new WolfData(UUID);
        clon.setArmor(armor);
        return clon;
    }

    public String getUUID() {
        return UUID;
    }

    public void setArmor(Armors armor) {
    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WolfData{");
        sb.append(", entityID=").append(UUID);
        sb.append(", armor=").append(armor.toString());
        sb.append('}');
        return sb.toString();
    }

}
