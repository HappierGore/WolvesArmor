package com.happiergore.wolves_armors.Data;

import com.happiergore.wolves_armors.Items.Armor.Armor;
import com.happiergore.wolves_armors.Items.Behaviour.Behaviours;
import com.happiergore.wolves_armors.Items.Chest.DamagedChest;

/**
 *
 * @author HappieGore
 */
public class WolfData {

    private Armor armor;
    private DamagedChest chest;
    private Behaviours behaviour;

    private final String UUID;

    public WolfData(String entityUUID) {
        this.UUID = entityUUID;
        this.behaviour = Behaviours.NEUTRAL;
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

    public Behaviours getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviours behaviour) {
        this.behaviour = behaviour;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WolfData{");
        sb.append("armor=").append(armor);
        sb.append(", chest=").append(chest);
        sb.append(", behaviour=").append(behaviour);
        sb.append(", UUID=").append(UUID);
        sb.append('}');
        return sb.toString();
    }
}
