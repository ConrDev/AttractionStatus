package me.conrdev.lib.gui.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class ItemAPI {

    public static ItemStack getCustomSkullItem(String minecraftUrl, String name, String lore) {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        if (minecraftUrl.isEmpty()) return i;

        ItemMeta sm = i.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + minecraftUrl).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        Field profileField = null;

        try {
            assert sm != null;
            profileField = sm.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        assert profileField != null;
        profileField.setAccessible(true);

        try {
            profileField.set(sm, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        sm.setDisplayName(Util.color(name));
        sm.setLore(new ArrayList<String>(Arrays.asList(Util.color(lore.split("\n")))));
        i.setItemMeta(sm);


        return i;
    }

}
