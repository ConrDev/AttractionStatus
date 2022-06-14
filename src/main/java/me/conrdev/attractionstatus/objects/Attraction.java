package me.conrdev.attractionstatus.objects;

import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.TpUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Attraction {

    private int id;
    private String name;
    private String status;

//    private World world;
    private Location location;

    private String zone;
    private UUID owner;

    public Attraction(int id, String name, String status, Location location, String zone, UUID owner) {
        this.id = id;
        this.name = name;
        this.status = status;
//        this.world = world;
        this.location = location;
        this.zone = zone;
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setStatus(String status) {
        this.status = status.toUpperCase();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getZone() {
        return this.zone;
    }

    public String getStatus(boolean... withColor) {
        if (withColor.length == 0 || withColor[0]) return MsgUtil.valueOf(this.status).getMessage();

        return this.status;
    }

    public Location getLocation() {
        return location.clone();
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void removeZone(String zone) {
        this.zone = "";
    }

    public boolean telepertPlayer(Player player) {
        if (TpUtil.tp(player, getLocation())) {
            EffectUtil.playEffect(getLocation().getBlock(), Particle.FIREWORKS_SPARK, Sound.ITEM_CHORUS_FRUIT_TELEPORT);
            return true;
        }

        return false;
    }

    public boolean telepertPlayer(CommandSender sender) {
        if (TpUtil.tp(sender, getLocation())) {
            EffectUtil.playEffect(getLocation().getBlock(), Particle.FIREWORKS_SPARK, Sound.ITEM_CHORUS_FRUIT_TELEPORT);
            return true;
        }

        return false;
    }
}
