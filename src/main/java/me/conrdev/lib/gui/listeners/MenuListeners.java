package me.conrdev.lib.gui.listeners;

import me.conrdev.lib.gui.MenuAPI_OLD;
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
		
		UUID invUUID = MenuAPI_OLD.openInv.get(pUUID);
		if (invUUID != null) {
			e.setCancelled(true);
			
			MenuAPI_OLD menu = MenuAPI_OLD.getInvById().get(invUUID);
			MenuAPI_OLD.invAction action = menu.getActions().get(e.getSlot());
			
			if (action != null) {
				action.click(p);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		UUID pUUID = p.getUniqueId();
		
		MenuAPI_OLD.openInv.remove(pUUID);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID pUUID = p.getUniqueId();
		
		MenuAPI_OLD.openInv.remove(pUUID);
	}
}
