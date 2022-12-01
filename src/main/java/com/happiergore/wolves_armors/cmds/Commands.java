package com.happiergore.wolves_armors.cmds;

import com.happiergore.menusapi.Utils.PlayerUtils;
import com.happiergore.wolves_armors.Items.Config;
import com.happiergore.wolves_armors.Utils.TextUtils;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
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
            Config.armorsLoaded.forEach(armor -> {
                if (armor.getIdentifier().equalsIgnoreCase(args[1])) {
                    ((Player) sender).getInventory().addItem(armor.getItem());
                }
            });
        }

        if (args[0].equalsIgnoreCase("read_nbt")) {
            if (sender instanceof Player) {
                int durabilityReduced;
                String identifier;
                String wolfUUID;
                PlayerUtils player = new PlayerUtils((Player) sender);
                if (player.get().getItemInHand() == null || player.get().getItemInHand().getType() == Material.AIR) {
                    player.sendColoredMsg("&cYou need an item in your hand to execute this command.");
                    return false;
                }
                NBTItem nbtItem = new NBTItem(player.get().getItemInHand());
                identifier = nbtItem.getString("Wolves_Armor_Identifier");
                wolfUUID = nbtItem.getString("Wolves_Armor_WolfUUID");
                durabilityReduced = nbtItem.getInteger("Wolves_Armor_Durability_Reduced");
                player.sendColoredMsg("Wolves_Armor_Identifier: &n" + identifier);
                player.sendColoredMsg("Wolves_Armor_WolfUUID: &n" + wolfUUID);
                player.sendColoredMsg("Wolves_Armor_Durability_Reduced: &n" + durabilityReduced);
            }
        }

        if (args[0].equalsIgnoreCase("read_nbt_keys")) {
            if (sender instanceof Player) {
                PlayerUtils player = new PlayerUtils((Player) sender);
                if (player.get().getItemInHand() == null || player.get().getItemInHand().getType() == Material.AIR) {
                    player.sendColoredMsg("&cYou need an item in your hand to execute this command.");
                    return false;
                }
                NBTItem nbtItem = new NBTItem(player.get().getItemInHand());
                nbtItem.getKeys().forEach(key -> player.sendColoredMsg(key));
            }
        }
        return false;
    }

}
