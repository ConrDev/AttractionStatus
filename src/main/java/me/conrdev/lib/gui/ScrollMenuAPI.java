package me.conrdev.lib.gui;

import java.lang.reflect.Field;
import java.util.*;

import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ScrollMenuAPI {

    public ArrayList<Inventory> pages = new ArrayList<Inventory>();
    public UUID id;
    public int currpage = 0;
    public static HashMap<UUID, ScrollMenuAPI> users = new HashMap<UUID, ScrollMenuAPI>();

    public static String invTitle;

    public ScrollMenuAPI(ArrayList<ItemStack> items, String name, Player p){
        this.id = UUID.randomUUID();
        invTitle = Util.color(name);

        Inventory firstpage = getFirstBlankPage(name);
        Inventory blankPage = getBlankPage(name);
        Inventory lastPage = getBlankPage(name);



        for (ItemStack item : items) {
            if (blankPage.firstEmpty() == -1) {
                pages.add(blankPage);
//                blankPage = getBlankPage(name);
                blankPage.addItem(item);
            } else {
                blankPage.addItem(item);
            }
        }
        pages.add(blankPage);

        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }

    public static final String nextPageName = Util.color("&e&lNEXT PAGE");
    public static final String prevPageName = Util.color("&e&LPREVIOUS PAGE");
    public static final String closeMenu = Util.color("&c&lClose");
    private Inventory getFirstBlankPage(String name) {
        Inventory page = Bukkit.createInventory(null, 54, name);

        ItemStack nextPage = setCustomSkullItemContent("141ff6bc67a481232d2e669e43c4f087f9d2306665b4f829fb86892d13b70ca", Util.color("&e&lNEXT PAGE"), " ");
        //ItemStack prevPage = setCustomSkullItemContent("49b2bee39b6ef47e182d6f1dca9dea842fcd68bda9bacc6a6d66a8dcdf3ec", Util.color("&e&LPREVIOUS PAGE"), " ");

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta borderm = border.getItemMeta();
        borderm.setDisplayName(" ");
        borderm.setLore(new ArrayList<String>(Arrays.asList(" ")));
        border.setItemMeta(borderm);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName(closeMenu);
        closem.setLore(new ArrayList<String>(Arrays.asList(" ")));
        close.setItemMeta(closem);

        page.setItem(53, nextPage);
        //page.setItem(45, prevPage);
        page.setItem(49, close);

        for (int i = 0; i < page.getSize(); i++) {
            ItemStack is = page.getItem(i);
            if ((i >= page.getSize() - 9 && i <= page.getSize() - 1)) {
                if (is == null || is.getType() == Material.AIR) {
                    page.setItem(i, border);
                }
            }
        }

        return page;
    }

    private Inventory getBlankPage(String name) {
        Inventory page = Bukkit.createInventory(null, 54, name);

        ItemStack nextPage = setCustomSkullItemContent("141ff6bc67a481232d2e669e43c4f087f9d2306665b4f829fb86892d13b70ca", Util.color("&e&lNEXT PAGE"), " ");
        ItemStack prevPage = setCustomSkullItemContent("49b2bee39b6ef47e182d6f1dca9dea842fcd68bda9bacc6a6d66a8dcdf3ec", Util.color("&e&LPREVIOUS PAGE"), " ");

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta borderm = border.getItemMeta();
        borderm.setDisplayName(" ");
        borderm.setLore(new ArrayList<String>(Arrays.asList(" ")));
        border.setItemMeta(borderm);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName(closeMenu);
        closem.setLore(new ArrayList<String>(Arrays.asList(" ")));
        close.setItemMeta(closem);

        page.setItem(53, nextPage);
        page.setItem(45, prevPage);
        page.setItem(49, close);

        for (int i = 0; i < page.getSize(); i++) {
            ItemStack is = page.getItem(i);
            if ((i >= page.getSize() - 9 && i <= page.getSize() - 1)) {
                if (is == null || is.getType() == Material.AIR) {
                    page.setItem(i, border);
                }
            }
        }

        return page;
    }

    private Inventory getLastBlankPage(String name) {
        Inventory page = Bukkit.createInventory(null, 54, name);

        //ItemStack nextPage = setCustomSkullItemContent("141ff6bc67a481232d2e669e43c4f087f9d2306665b4f829fb86892d13b70ca", Util.color("&e&lNEXT PAGE"), " ");
        ItemStack prevPage = setCustomSkullItemContent("49b2bee39b6ef47e182d6f1dca9dea842fcd68bda9bacc6a6d66a8dcdf3ec", Util.color("&e&LPREVIOUS PAGE"), " ");

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta borderm = border.getItemMeta();
        borderm.setDisplayName(" ");
        borderm.setLore(new ArrayList<String>(Arrays.asList(" ")));
        border.setItemMeta(borderm);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName(closeMenu);
        closem.setLore(new ArrayList<String>(Arrays.asList(" ")));
        close.setItemMeta(closem);

        //page.setItem(53, nextPage);
        page.setItem(45, prevPage);
        page.setItem(49, close);

        for (int i = 0; i < page.getSize(); i++) {
            ItemStack is = page.getItem(i);
            if ((i >= page.getSize() - 9 && i <= page.getSize() - 1)) {
                if (is == null || is.getType() == Material.AIR) {
                    page.setItem(i, border);
                }
            }
        }

        return page;
    }

    private ItemStack setCustomSkullItemContent(String minecraftUrl, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        if(minecraftUrl.isEmpty())return i;

        ItemMeta sm = i.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + minecraftUrl).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        Field profileField = null;

        try {
            profileField = sm.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(sm, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        sm.setDisplayName(name);
        sm.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(sm);


        return i;
    }
}
