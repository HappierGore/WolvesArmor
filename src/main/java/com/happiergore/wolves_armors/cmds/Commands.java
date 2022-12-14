package com.happiergore.wolves_armors.cmds;

import com.happiergore.menusapi.Utils.PlayerUtils;
import com.happiergore.wolves_armors.Items.Armor.Armors;
import com.happiergore.wolves_armors.Items.Chest.Chests;
import com.happiergore.wolves_armors.Items.Config;
import com.happiergore.wolves_armors.Utils.TextUtils;
import com.happiergore.wolves_armors.main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class Commands implements CommandExecutor {

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        TextUtils textUtils = new TextUtils();

        if (args.length == 0) {
            sender.sendMessage(textUtils.parseColor("&cImcomplete command. Use /wa reload."));
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(textUtils.parseColor("&aConfiguration reloaded."));
            main.getPlugin(main.class).reloadConfig();
            main.configYML = main.getPlugin(main.class).getConfig();
            main.debugMode = main.getPlugin(main.class).getConfig().getBoolean("debug_mode");
            Config.reloadConfig(true);
            return true;
        }

        if (args[0].equalsIgnoreCase("get")) {

            if (args.length == 1) {
                sender.sendMessage(textUtils.parseColor("&cIncomplete command, select &narmor&r or &c&nchest"));
                return false;
            }

            if (args[1].equalsIgnoreCase("armor")) {
                if (args.length == 2) {
                    sender.sendMessage(textUtils.parseColor("&cPlease select one armor from tab."));
                    return false;
                }
                for (Armors armor : Config.armorsLoaded) {
                    if (armor.getIdentifier().equalsIgnoreCase(args[2])) {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (Exception ex) {
                            amount = 1;
                        }
                        ItemStack item = armor.getItem(true);
                        item.setAmount(amount);
                        ((Player) sender).getInventory().addItem(item);
                        return true;
                    }
                }
                sender.sendMessage(textUtils.parseColor("&cThat armor doesn't exists."));
                return false;
            }

            if (args[1].equalsIgnoreCase("chest")) {
                if (args.length == 2) {
                    sender.sendMessage(textUtils.parseColor("&cPlease select one chest from tab."));
                    return false;
                }
                for (Chests chest : Config.chestsLoaded) {
                    if (chest.getIdentifier().equalsIgnoreCase(args[2])) {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (Exception ex) {
                            amount = 1;
                        }
                        ItemStack item = chest.getItem();
                        item.setAmount(amount);
                        ((Player) sender).getInventory().addItem(item);
                        return true;
                    }
                }
                sender.sendMessage(textUtils.parseColor("&cThat chest doesn't exists."));
                return false;
            }
        }
        if (args[0].equalsIgnoreCase("read_nbt")) {
            if (sender instanceof Player) {
                int durabilityReduced;
                String identifier;
                String wolfUUID;
                String chestUUID;
                boolean wolfDeath;
                PlayerUtils player = new PlayerUtils((Player) sender);
                if (player.get().getItemInHand() == null || player.get().getItemInHand().getType() == Material.AIR) {
                    player.sendColoredMsg("&cYou need an item in your hand to execute this command.");
                    return false;
                }
                NBTItem nbtItem = new NBTItem(player.get().getItemInHand());
                identifier = nbtItem.getString("Wolves_Armor_Identifier");
                wolfUUID = nbtItem.getString("Wolves_Armor_WolfUUID");
                durabilityReduced = nbtItem.getInteger("Wolves_Armor_Durability_Reduced");
                chestUUID = nbtItem.getString("Wolves_Armor_ChestUUID");
                wolfDeath = nbtItem.getBoolean("Wolves_Armor_WolfDeath");
                if (identifier != null && !identifier.isBlank()) {
                    player.sendColoredMsg("Wolves_Armor_Identifier: &n" + identifier);
                }
                if (wolfUUID != null && !wolfUUID.isBlank()) {
                    player.sendColoredMsg("Wolves_Armor_WolfUUID: &n" + wolfUUID);
                }
                if (chestUUID != null && !chestUUID.isBlank()) {
                    player.sendColoredMsg("Wolves_Armor_ChestUUID: &n" + chestUUID);
                }
                if (durabilityReduced != 0) {
                    player.sendColoredMsg("Wolves_Armor_Durability_Reduced: &n" + durabilityReduced);
                }
                if (nbtItem.hasKey("Wolves_Armor_WolfDeath")) {
                    player.sendColoredMsg("Wolves_Armor_WolfDeath: &n" + wolfDeath);
                }
                return true;
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
                return true;
            }
        }

        return false;
    }
}
