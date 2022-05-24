package me.conrdev.attractionstatus.Objects;

import org.bukkit.Location;
import org.bukkit.World;

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
        this.status = status;
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

    public String getStatus() {
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
}
