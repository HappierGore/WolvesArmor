package com.happiergore.wolves_armors.cmds;

import com.happiergore.wolves_armors.Items.Config;
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
            completions.add("read_nbt");
            completions.add("read_nbt_keys");
        }
        if (args.length == 2) {
            Config.armorsLoaded.forEach(armor -> completions.add(armor.getIdentifier()));
        }
        return completions;
    }

}
