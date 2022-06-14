package me.conrdev.attractionstatus.objects;

import org.bukkit.Location;

import java.util.UUID;

public class Sign {
    private int id;

    private String attractionName;
    private Location location;
    private UUID owner;

    public Sign(int id, String attractionName, Location location, UUID owner) {
        this.id = id;
        this.attractionName = attractionName;
        this.location = location;
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAttraction(String name) {
        this.attractionName = name;
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

    public String getAttraction() {
        return this.attractionName;
    }

    public Location getLocation() {
        return location.clone();
    }

    public UUID getOwner() {
        return this.owner;
    }

}
