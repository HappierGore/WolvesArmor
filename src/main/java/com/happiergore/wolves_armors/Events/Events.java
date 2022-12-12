package com.happiergore.wolves_armors.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author HappieGore
 */
public class Events extends com.happiergore.menusapi.Events implements Listener {

    @EventHandler
    public void onClickEntity(PlayerInteractEntityEvent e) {
        OnWolfTamedClick.listen(e);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageEvent e) {
        OnWolfDamaged.listen(e);
    }

    @EventHandler
    public void onDeathEntity(EntityDeathEvent e) {
        OnWolfDeath.listen(e);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        OnDragItem.listen(e);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        OnItemInteract.listen(e);
    }

    @EventHandler
    public void onWolfKills(EntityDamageByEntityEvent e) {
        OnWolfKill.listen(e);
    }

    @EventHandler
    public void onSetTarget(EntityTargetEvent e) {
        OnWolfSetTarget.listen(e);
    }

    @EventHandler
    public void OnPlaceBlock(BlockPlaceEvent e) {
        OnBlockPlaceEvent.listen(e);
    }

}
