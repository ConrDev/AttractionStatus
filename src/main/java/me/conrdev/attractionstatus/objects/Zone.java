package me.conrdev.attractionstatus.objects;

import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Zone {

    private int id;
    private String name;

    private World world;

    private final List<Attraction> attractions;
    private UUID owner;

    public Zone(int id, String name, World world, UUID owner) {
        this.id = id;
        this.name = name;
        this.world = world;
        this.attractions = new ArrayList<>();
        this.owner = owner;
    }

    public Zone(int id, String name, World world, List<Attraction> attractions, UUID owner) {
        this.id = id;
        this.name = name;
        this.world = world;
        this.attractions = attractions;
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAttraction(Attraction attraction) {
        this.attractions.add(attraction);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public int getId() {
        return this.id;
    }

    public int getId(String zoneName) {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public World getWorld() {
        return this.world;
    }

    public List<Attraction> getAttractions() {
        return this.attractions;
    }

    public Attraction getAttraction(int id) {
        return this.attractions.get(id);
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void removeAttraction(Attraction attraction) {
        attractions.remove(attraction);
    }
}
