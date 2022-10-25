package com.happiergore.wolves_armors.Data;

import com.happiergore.wolves_armors.Items.Armors;
import java.io.Serializable;

/**
 *
 * @author HappieGore
 */
public class WolfData implements Serializable {

    private Armors armor;
    //El Id cambia cada que se reinicia el servidor, NO ES
    // UN METODO SEGURO
    private final String entityUUID;

    public WolfData(String entityUUID) {
        this.entityUUID = entityUUID;
    }

    public String getEntityID() {
        return entityUUID;
    }

    public Armors getArmor() {
        return armor;
    }

    public void setArmor(Armors armor) {
        this.armor = armor;
    }

    public WolfData getClone() {
        WolfData clon = new WolfData(entityUUID);
        clon.setArmor(armor);
        return clon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WolfData{");
        sb.append(", entityID=").append(entityUUID);
        sb.append(", armor=").append(armor.toString());
        sb.append('}');
        return sb.toString();
    }

}
