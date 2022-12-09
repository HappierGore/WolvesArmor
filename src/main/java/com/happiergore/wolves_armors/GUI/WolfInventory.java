package com.happiergore.wolves_armors.GUI;

import com.happiergore.menusapi.GUI;
import com.happiergore.menusapi.Utils.ItemUtils;
import com.happiergore.wolves_armors.Items.DamagedChest;
import com.happiergore.wolves_armors.Utils.Serializers;
import com.happiergore.wolves_armors.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappieGore
 */
public class WolfInventory extends GUI {

    public DamagedChest chest;

    public WolfInventory(Player player, String entityUUID, DamagedChest chest) {
        super(player, main.configYML.getString("wolf_inventory_name"));
        this.setInventory(Bukkit.createInventory((GUI) this,
                (int) (Math.ceil((chest.getType().getSlotsUnlocked() / 9.0f))) * 9,
                this.INVENTORY_TITLE));
        this.chest = chest;
    }

    public WolfInventory() {
        super(null, null);
    }

    @Override
    public void onClose(InventoryCloseEvent ice) {
        this.chest.content.clear();
        ItemStack[] itms = this.getInventory().getContents();
        ItemUtils itemUtils = new ItemUtils();
        ItemStack whoNo = new SlotBlocked(this).generateMainItem();
        for (int i = 0; i < itms.length; i++) {
            if (itms[i] == null) {
                continue;
            }
            if (itemUtils.compareItems(itms[i], whoNo, null)) {
                continue;
            }
            this.chest.content.put(i, Serializers.serializeItem(itms[i]));
        }
        //Si resulta que el cofre ya tiene su UUID guardada, guardamos ahí
        if (main.chestData.getConfig().getString(chest.getChestUUID()) != null) {
            main.chestData.getConfig().set(chest.getChestUUID(), Serializers.serialize(chest));
            main.chestData.SaveFile();
        }
        //Si el cofre ya se rompió, retorna items
        if (chest.getTimesOpened() >= chest.getType().getTimesAllowedToOpen()) {
            if (chest.returnItems(this.getPlayer().get()) > 0) {
                for (String str : chest.content.values()) {
                    getPlayer().get().getWorld().dropItem(
                            this.getPlayer().get().getLocation(),
                            (ItemStack) Serializers.deserializeItem(str));
                }
            }
            main.chestData.getConfig().set(chest.getChestUUID(), null);
            main.chestData.SaveFile();
        }
    }

    @Override
    public void updateInventory() {
        this.getItems().clear();
        this.onOpen();
    }

    @Override
    public void registerButtons() {
        int slotsAllowed = chest.getType().getSlotsUnlocked();
        int totalSlots = (int) (Math.ceil((chest.getType().getSlotsUnlocked() / 9.0f))) * 9;
        int slotsBlocked = totalSlots - slotsAllowed;
        for (int i = 0; i < slotsBlocked; i++) {
            this.registerBtn((this.getInventory().getSize() - 1) - i, new SlotBlocked(this));
        }
        chest.content.forEach((slot, itm) -> this.getInventory().setItem(slot, Serializers.deserializeItem(itm)));
    }
}
