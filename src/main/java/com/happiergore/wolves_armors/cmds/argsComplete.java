package com.happiergore.wolves_armors.cmds;

import com.happiergore.wolves_armors.Items.Config;
import com.happiergore.wolves_armors.main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 *
 * @author HappierGore
 */
public class argsComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("reload");
            completions.add("gui");
            completions.add("get");
            if (main.debugMode) {
                completions.add("read_nbt");
                completions.add("read_nbt_keys");
                completions.add("transform");
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("get")) {
                completions.clear();
                completions.add("armor");
                completions.add("chest");
            }
        }

        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("armor")) {
                completions.clear();
                Config.armorsLoaded.forEach(armor -> completions.add(armor.getIdentifier()));
            }
            if (args[1].equalsIgnoreCase("chest")) {
                completions.clear();
                Config.chestsLoaded.forEach(chest -> completions.add(chest.getIdentifier()));
            }
        }
        return completions;
    }

}
