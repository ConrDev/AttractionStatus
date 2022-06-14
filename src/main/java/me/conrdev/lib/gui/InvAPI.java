package me.conrdev.lib.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class InvAPI {

    private Inventory inv;
    private static Map<UUID, InvAPI> inventories = new HashMap<>();
    private Map<Integer, ClickRunnable> actions = new HashMap<>();
    private UUID uuid;

    private static String title;

    public InvAPI(String name, int size, UUID uuid) {
        this(name, size, uuid, null);
    }

    public InvAPI(String name, int size, UUID uuid, ItemStack placeholder) {
        this.uuid = uuid;
        this.title = name;
        if (size == 0) return;
        this.inv = Bukkit.createInventory(null, size, name);
        if (placeholder != null) {
            for (int i = 0; i < size; i++) inv.setItem(i, placeholder);
        }

        this.register();
    }

    public Inventory getInv() {
        return inv;
    }

    public int getSize() {
        return inv.getSize();
    }

    public void setItem(Integer slot, Material material, ClickRunnable action) {
        setItem(slot, material, null, null, action);
    }

    public void setItem(Integer slot, Material material, String name, String lore, ClickRunnable action) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta im = item.getItemMeta();

        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        if (name != null) im.setDisplayName(name);
        if (lore != null) im.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        item.setItemMeta(im);

        inv.setItem(slot, item);
        actions.put(slot, action);
    }

    public void setItemStack(Integer slot, ItemStack itemStack, ClickRunnable action) {
        inv.setItem(slot, itemStack);
        actions.put(slot, action);
    }

    public void setSkull(Integer slot, String SkullPlayer, String name, String lore, ClickRunnable action) {
        inv.setItem(slot, setSkullItem(Bukkit.getPlayer(SkullPlayer), name, lore));
        actions.put(slot, action);
    }

    public void setUUIDSkull(Integer slot, String uuid, String name, String lore, ClickRunnable action) {
        inv.setItem(slot, setUUIDSkullItem(uuid, name, lore));
        actions.put(slot, action);
    }

    public void setCustomSkull(Integer slot, String minecraftUrl, String name, String lore, ClickRunnable action) {
        inv.setItem(slot, setCustomSkullItem(minecraftUrl, name, lore));
        actions.put(slot, action);
    }

    public void setItemStack(Integer slot, ItemStack itemStack) {
        inv.setItem(slot, itemStack);
    }

    public void setItem(Integer slot, Material material, String name, String lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta im = item.getItemMeta();

        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        if (name != null) im.setDisplayName(name);
        if (lore != null) im.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        item.setItemMeta(im);

        inv.setItem(slot, item);
    }

    @SuppressWarnings("deprecation")
    private ItemStack setSkullItem(Player p, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(name);
        sm.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(sm);

        return i;
    }

    private ItemStack setUUIDSkullItem(String uuid, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        sm.setDisplayName(name);
        sm.setLore(new ArrayList<String>(Arrays.asList(lore.split("\n"))));
        i.setItemMeta(sm);

        return i;
    }

    public ItemStack setCustomSkullItem(String minecraftUrl, String name, String lore) {
        byte[] encodedData = null;
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        if (minecraftUrl.isEmpty()) return i;

        ItemMeta sm = i.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        encodedData = java.util.Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + minecraftUrl).getBytes());

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

    public void removeItem(int slot) {
        inv.setItem(slot, new ItemStack(Material.AIR));
    }

    private void register() {
        inventories.put(this.uuid, this);
    }

    private void unRegister() {
        inventories.remove(this.uuid);
    }

    public void openInv(Player p) {
        Inventory inv = (Inventory) getInv();
        InventoryView openInv = p.getOpenInventory();

        if (openInv == null) return;

        Inventory openTop = p.getOpenInventory().getTopInventory();

        if (openTop.equals(inv)) {
            openTop.setContents(inv.getContents());
        } else {
            p.openInventory(inv);
        }

        register();

    }

    public void removeInv(Player p) {
        p.closeInventory();
        unRegister();
    }

    public static Listener getListener() {
        return new Listener() {

            @EventHandler
            public void onClick(InventoryClickEvent event) {
                HumanEntity clicker = event.getWhoClicked();
                if (clicker instanceof Player) {
                    if (event.getCurrentItem() == null) return;
                }

                Player p = (Player) clicker;

                if (p != null) {
                    UUID uuid = p.getUniqueId();

                    if (inventories.containsKey(uuid)) {
                        InvAPI current = inventories.get(uuid);

                        if (!event.getInventory().equals(current.getInv())) return;

                        event.setCancelled(true);
                        int slot = event.getSlot();

                        if (current.actions.get(slot) != null) {
                            current.actions.get(slot).run(event);
                        }
                    }
                }
            }

            @EventHandler
            public void onClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    if (event.getInventory() == null) return;

                    Player p = (Player) event.getPlayer();
                    UUID uuid = p.getUniqueId();

                    if (inventories.containsKey(uuid)) inventories.get(uuid).unRegister();
                }
            }
        };
    }

    @FunctionalInterface
    public interface ClickRunnable {
        public void run(InventoryClickEvent event);
    }

    @FunctionalInterface
    public interface CloseRunnable {
        public void run(InventoryCloseEvent event);
    }

}
