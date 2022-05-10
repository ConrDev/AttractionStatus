package me.conrdev.lib.gui.listeners;

import me.conrdev.lib.gui.MenuAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class MenuListeners implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getWhoClicked();
		UUID pUUID = p.getUniqueId();
		
		UUID invUUID = MenuAPI.openInv.get(pUUID);
		if (invUUID != null) {
			e.setCancelled(true);
			
			MenuAPI menu = MenuAPI.getInvById().get(invUUID);
			MenuAPI.invAction action = menu.getActions().get(e.getSlot());
			
			if (action != null) {
				action.click(p);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		UUID pUUID = p.getUniqueId();
		
		MenuAPI.openInv.remove(pUUID);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID pUUID = p.getUniqueId();
		
		MenuAPI.openInv.remove(pUUID);
	}
}
