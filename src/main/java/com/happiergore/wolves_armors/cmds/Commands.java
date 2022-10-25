package com.happiergore.wolves_armors.cmds;

import com.happiergore.wolves_armors.Items.ArmorsLoader;
import com.happiergore.wolves_armors.Utils.TextUtils;
import com.happiergore.wolves_armors.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        TextUtils textUtils = new TextUtils();

        if (args.length == 0) {
            sender.sendMessage(textUtils.parseColor("&cImcomplete command. Use /wva reload."));
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(textUtils.parseColor("&aConfiguration reloaded."));
            main.getPlugin(main.class).reloadConfig();
            main.configYML = main.getPlugin(main.class).getConfig();
            main.debugMode = main.getPlugin(main.class).getConfig().getBoolean("debug_mode");
            return false;
        }

        if (args[0].equalsIgnoreCase("get")) {
            if (args.length == 1) {
                sender.sendMessage(textUtils.parseColor("&cPlease select one item using tab completion."));
                return false;
            }
            ArmorsLoader.armorsLoaded.forEach(armor -> {
                if (armor.getItemIdentifier().equalsIgnoreCase(args[1])) {
                    ((Player) sender).getInventory().addItem(armor.getItem());
                }
            });
        }
        return false;
    }

}
