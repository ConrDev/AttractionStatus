package me.conrdev.attractionstatus.managers;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.Objects.Zone;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.*;

public class AttractionManager {

    private static AttractionManager instance = null;
    private Core plugin = null;

    private final ConfigManager configManager = Core.getConfigManager();
    private final Configs configs = Core.getConfigs();

    private static final Map<Integer, Attraction> attractions = new HashMap<Integer, Attraction>();

    public static AttractionManager getInstance() {
        if (instance == null) instance = new AttractionManager();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    AttractionManager() {
//        this.configManager = plugin.getConfigManager();
//        this.configs = plugin.getConfigs();
    }

    /**
     * Create an attraction from players location
     */
    public Attraction createAttraction(String name, String status, Location location, String zoneName, UUID playerUUID) {
        int id = generateId();

        Attraction attraction = new Attraction(id, name, status, location, zoneName, playerUUID);

        attractions.put(id, attraction);

        saveAttraction(attraction);

        return attraction;
    }

    /**
     * Create an attraction from players location with ID
     */
    public Attraction createAttraction(int id, String name, String status, Location location, String zoneName, UUID playerUUID) {
//        int id = generateId();

        Attraction attraction = new Attraction(id, name, status, location, zoneName, playerUUID);

        attractions.put(id, attraction);

        saveAttraction(attraction);

        return attraction;
    }

    public void loadAttractions() {
        if (configs.getAttractions().getConfigurationSection("attractions") == null) {
            MsgUtil.NOATTRACTIONS.msg(Bukkit.getConsoleSender());
            return;
        }

        MsgUtil.ATTRACTIONS_LOADING.msg(Bukkit.getConsoleSender());

        for (String id : configs.getAttractions().getConfigurationSection("attractions").getKeys(false)) {
            if (Util.isNumber(id)) {
                int attractionId = Integer.parseInt(id);

                String path = "attractions." + attractionId;

                String name = configManager.getRawString(configs.getAttractions(), path + ".name");
                String status = configManager.getRawString(configs.getAttractions(), path + ".status");
                Location location = configManager.getRawLocation(configs.getAttractions(), path + ".location");
                String zoneName = configManager.getRawString(configs.getAttractions(), path + ".zone");
                UUID playerUUID = configManager.getUUID(configs.getAttractions(), path + ".owner");

//                Zone zone = ZoneManager.getZone(zoneName);

                Attraction attraction = createAttraction(attractionId, name, status, location, zoneName, playerUUID);

                Map<String, String> map = new HashMap<>();
                map.put("%object%", attraction.getName());

                MsgUtil.SUCCES_LOADED.msg(Bukkit.getConsoleSender(), map, false);
            }
        }
    }

    public boolean saveAttraction(Attraction attraction) {
        int id = attraction.getId();
        String path = "attractions." + id;

        ArrayList<Boolean> data = new ArrayList<>();

        data.add(configManager.setData(configs.getAttractions(), path + ".name", attraction.getName()));
        data.add(configManager.setData(configs.getAttractions(), path + ".status", attraction.getStatus()));
        data.add(configManager.setData(configs.getAttractions(), path + ".zone", attraction.getZone()));
        data.add(configManager.setData(configs.getAttractions(), path + ".owner", attraction.getOwner().toString()));
        data.add(configManager.setLocation(configs.getAttractions(), path + ".location", attraction.getLocation()));

        Set<Boolean> checks = new HashSet<>(data);

        int length = checks.size();
        int successes = Collections.frequency(checks, true);

        return length == successes;
    }

    public boolean removeAttraction(Attraction attraction) {
        int id = attraction.getId();
        String path = "attractions." + id;
        Zone zone = ZoneManager.getZone(attraction.getZone());

        if (zone != null) {
            zone.removeAttraction(attraction);
            ZoneManager.getInstance().saveZone(zone);
        }

        configManager.setData(configs.getAttractions(), path, null);
        attractions.remove(id);

        return true;
    }

    public Map<Integer, Attraction> getAttractions() {
        return attractions;
    }

    public void resetAttractions() {
        attractions.clear();
    }

    public Attraction getAttraction(int id) {
        return attractions.get(id);
    }

    public Attraction getAttraction(String attractionName) {
        attractionName = ChatColor.stripColor(attractionName);
        for (Attraction attraction : attractions.values()) {
            if (attraction.getName().equalsIgnoreCase(attractionName)) {
                return getAttraction(attraction.getId());
            }
        }

        return null;
    }

    private int generateId() {
        if (attractions.size() == 0) return 1;

        return Util.getHighestId(attractions) + 1;
    }
}
