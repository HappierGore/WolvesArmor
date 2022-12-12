package com.happiergore.wolves_armors.Items.Behaviour;

import com.happiergore.wolves_armors.main;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HappieGore
 */
public enum Behaviours {
    PASSIVE, AGRESSIVE, NEUTRAL;

    public List<String> getBlackList() {
        switch (this) {
            case AGRESSIVE: {
                List<String> entry = main.configYML.getStringList("Behaviour.Agressive.IgnoreMobs");
                List<String> data = new ArrayList<>();
                for (int i = 0; i < entry.size(); i++) {
                    data.add(entry.get(i).toUpperCase());
                }
                return data;
            }
            case NEUTRAL: {
                List<String> entry = main.configYML.getStringList("Behaviour.Neutral.IgnoreMobs");
                List<String> data = new ArrayList<>();
                for (int i = 0; i < entry.size(); i++) {
                    data.add(entry.get(i).toUpperCase());
                }
                return data;
            }
            default: {
                return new ArrayList<>();
            }
        }
    }
}
