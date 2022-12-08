package com.happiergore.wolves_armors.Data;

import com.happiergore.wolves_armors.Items.Armor;
import com.happiergore.wolves_armors.Items.DamagedChest;

/**
 *
 * @author HappieGore
 */
public class WolfData {

    private Armor armor;
    private DamagedChest chest;

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

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public DamagedChest getChest() {
        return chest;
    }

    public void setChest(DamagedChest chest) {
        this.chest = chest;
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
