package me.conrdev.attractionstatus.menus;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import me.conrdev.lib.gui.InvAPI;
import me.conrdev.lib.gui.MenuAPI;
import me.conrdev.lib.gui.MenuAPI_OLD;
import me.conrdev.lib.gui.ScrollMenuAPI;
import me.conrdev.lib.gui.utils.ItemAPI;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public class AttractionsMenu {

    private static final ArrayList<ItemStack> items = new ArrayList<>();

    public AttractionsMenu(Player player) {
        items.clear();
        getAttractions();
        new ScrollMenuAPI(items, getConfigTitle(), player);
    }

    private void getAttractions() {
        for (Attraction attraction : AttractionManager.getInstance().getAttractions().values()) {
            ItemStack attItem = ItemAPI.getCustomSkullItem(
                    getStatusItem(attraction),
                    "&f" + attraction.getName(),
                    "&d" + attraction.getZone()
            );

            items.add(attItem);
        }
    }

    private static String getStatusItem(Attraction attraction) {
        if (attraction.getStatus().equalsIgnoreCase(MsgUtil.OPENED.getMessage()))
            return "a7695f96dda626faaa010f4a5f28a53cd66f77de0cc280e7c5825ad65eedc72e";

        if (attraction.getStatus().equalsIgnoreCase(MsgUtil.MAINTENANCE.getMessage()))
            return "13330fbed377c244f487e4bc5682d15af40d3ce4c32ee03fc24a7f952e7d29a9";

        return "3c4d7a3bc3de833d3032e85a0bf6f2bef7687862b3c6bc40ce731064f615dd9d";

    }


    private static String getConfigTitle() {
        if (ConfigManager.getInstance().getRawString(Configs.getInstance().getConfig(), "Menus.Attractions.Title") != null) {
            return ConfigManager.getInstance().getRawString(Configs.getInstance().getConfig(), "Menus.Attractions.Title");
        }

        return ConfigManager.getInstance().getRawString(Configs.getInstance().getLang(), "menus.attractions.title");
    }

    private static int getConfigSize() {
        int menuSize = ConfigManager.getInstance().getRawInt(Configs.getInstance().getConfig(), "Menus.Attractions.Size");
        if (menuSize == 0) {
            return 27;
        }

        return menuSize;
    }


}


//        super(getConfigTitle(), getConfigSize(), player.getUniqueId());
//
//        setItem(getInv().getSize() - 5, Material.BARRIER, Util.color("&c&lCLOSE"), "",
//                event -> {
//                    Player p = (Player) event.getWhoClicked();
//                    removeInv(p);
//                }
//        );
//
//        int rowCount = getInv().getSize()/9;
//
//        for(int i = 0; i < getInv().getSize(); i++) {
//            int row = i / 9;
//            int column = (i % 9) + 1;
//
//            ItemStack is = getInv().getItem(i);
//
//            if(row == 0 || row == rowCount-1 || column == 1 || column == 9) {
//                if (is == null || is.getType() == Material.AIR) {
//                    setContent(i, Material.BLACK_STAINED_GLASS_PANE, " ", "", p -> {
//                    });
//                }
//            } else {
//                if (is == null || is.getType() == Material.AIR) {
//                    Attraction attraction = AttractionManager.getInstance().getAttraction(i);
//                    if (attraction != null) {
//                        setCustomSkullContent(
//                                i,
//                                getStatusItem(attraction),
//                                attraction.getName(),
//                                " ",
//                                attraction::telepertPlayer
//                        );
//                    }
//                }
//            }
//        }
//
//        for (int i = 0; i < getInv().getSize(); i++) {
//            ItemStack is = getInv().getItem(i);
//
//            if (i <= 8 || i >= getConfigSize() - 8 && i <= getConfigSize() - 1 || i % 9 == 0 || (i - 8) % 9 == 0) {
//                if (is == null || is.getType() == Material.AIR) {
//
//                }
//            } else {
//
//            }
//        }