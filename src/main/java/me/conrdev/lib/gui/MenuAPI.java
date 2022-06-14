package me.conrdev.lib.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public abstract class MenuAPI {

    public static Map<UUID, MenuAPI> invById = new HashMap<>();
    public static Map<UUID, UUID> openInv = new HashMap<>();

    private UUID uuid;
    private Inventory inv;
    private Map<Integer, invAction> actions = new HashMap<>();

    private String title;
    private int size;

    public MenuAPI(String title, int size) {
        this.title = title;
        this.size = size;
        uuid = UUID.randomUUID();
        inv = Bukkit.createInventory(null, this.size, this.title);
        //actions = new HashMap<>();
        invById.put(getId(), this);
    }

    public void setContent(int slot, ItemStack stack, String lore, invAction action) {
        ItemMeta im = stack.getItemMeta();
        im.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        stack.setItemMeta(im);
        inv.setItem(slot, stack);

        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setContent(int slot, ItemStack stack, String lore) {
        setContent(slot, stack, lore, null);
    }

    private ItemStack setItemContent(int slot, Material material, String name, String lore) {
        ItemStack i = new ItemStack(material, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(im);
        inv.setItem(slot, i);

        return i;
    }

    @SuppressWarnings("deprecation")
    private ItemStack setSkullItemContent(int slot, Player p, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(name);
        sm.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(sm);
        inv.setItem(slot, i);

        return i;
    }

    private ItemStack setUUIDSkullItemContent(int slot, String uuid, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        sm.setDisplayName(name);
        sm.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(sm);
        inv.setItem(slot, i);

        return i;
    }

    public ItemStack setCustomSkullItemContent(int slot, String minecraftUrl, String name, String lore) {
        byte[] encodedData = null;
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        if(minecraftUrl.isEmpty())return i;

        ItemMeta sm = i.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + minecraftUrl).getBytes());

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
        inv.setItem(slot, i);


        return i;
    }

    public void setContent(int slot, Material material, String name, String lore, invAction action) {
        setItemContent(slot, material, name, lore);

        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setSkullContent(int slot, Player p, String name, String lore, invAction action) {
        setSkullItemContent(slot, p, name, lore);

        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setUUIDSkullContent(int slot, String uuid, String name, String lore, invAction action) {
        setUUIDSkullItemContent(slot, uuid, name, lore);

        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setCustomSkullContent(int slot, String minecraftUrl, String name, String lore, invAction action) {
        setCustomSkullItemContent(slot, minecraftUrl, name, lore);

        if (action != null) {
            actions.put(slot, action);
        }
    }

    public interface invAction {
        void click(Player p);
    }

    public UUID getId() {
        return uuid;
    }

    public void openMenu(Player p) {
        p.openInventory(inv);
        openInv.put(p.getUniqueId(), getId());
    }

    public void delete() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID id = openInv.get(p.getUniqueId());

            if (id.equals(getId())) {
                p.closeInventory();
            }
        }

        invById.remove(getId());
    }

    // Getters
    public static Map<UUID, MenuAPI> getInvById() {
        return invById;
    }

    public static Map<UUID, UUID> getOpenInv() {
        return openInv;
    }

    public Map<Integer, invAction> getActions() {
        return actions;
    }

    public Inventory getInv() {
        return inv;
    }

    public  int getSize() {
        return size;
    }
}
