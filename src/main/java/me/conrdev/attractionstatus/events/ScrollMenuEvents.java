package me.conrdev.attractionstatus.events;

import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.managers.ZoneManager;
import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.objects.Zone;
import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.lib.gui.ScrollMenuAPI;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ScrollMenuEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();

        //Get the current scroller inventory the player is looking at, if the player is looking at one.
        if (!ScrollMenuAPI.users.containsKey(p.getUniqueId())) return;

        ScrollMenuAPI inv = ScrollMenuAPI.users.get(p.getUniqueId());

        if (!p.getOpenInventory().getTitle().equalsIgnoreCase(ScrollMenuAPI.invTitle)) return;

        if (event.getCurrentItem() == null) {
            event.setCancelled(true);
            return;
        }
        if (event.getCurrentItem().getItemMeta() == null) {
            event.setCancelled(true);
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            event.setCancelled(true);
            return;
        }

        //If the pressed item was a nextpage button
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollMenuAPI.nextPageName)) {
            event.setCancelled(true);
            //If there is no next page, don't do anything
            if (inv.currpage >= inv.pages.size() - 1) return;

            //Next page exists, flip the page
            inv.currpage += 1;
            p.openInventory(inv.pages.get(inv.currpage));
            EffectUtil.playSound(p.getLocation().getBlock(), Sound.ITEM_BOOK_PAGE_TURN);
            return;
        }

        //if the pressed item was a previous page button
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollMenuAPI.prevPageName)) {
            event.setCancelled(true);

            //If the page number is more than 0 (So a previous page exists)
            if (inv.currpage == 0) {
                //Flip to last page
                inv.currpage = inv.pages.size() - 1;
                p.openInventory(inv.pages.get(inv.currpage));
                EffectUtil.playSound(p.getLocation().getBlock(), Sound.ITEM_BOOK_PAGE_TURN);
                return;
            }

            //Flip to previous page
            inv.currpage -= 1;
            p.openInventory(inv.pages.get(inv.currpage));
            EffectUtil.playSound(p.getLocation().getBlock(), Sound.ITEM_BOOK_PAGE_TURN);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollMenuAPI.closeMenu)) {
            p.closeInventory();
            EffectUtil.playSound(p.getLocation().getBlock(), Sound.ITEM_BOOK_PUT);
            return;
        }

        if (event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            p.closeInventory();

            Attraction attraction = AttractionManager.getInstance().getAttraction(event.getCurrentItem().getItemMeta().getDisplayName());
            Zone zone = ZoneManager.getInstance().getZone(event.getCurrentItem().getItemMeta().getDisplayName());

            if (attraction != null) attraction.telepertPlayer(p);
//            if (zone != null) zone.telepertPlayer(p);
            return;
        }
    }

}