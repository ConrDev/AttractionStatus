package me.conrdev.attractionstatus.managers;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.objects.Zone;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.*;

//import sun.misc.Unsafe;

public class ZoneManager {

    private static ZoneManager instance = null;
    private Core plugin = null;

    private final ConfigManager configManager = Core.getConfigManager();
    private final Configs configs = Core.getConfigs();

    private static final Map<Integer, Zone> zones = new HashMap<>();

    public static ZoneManager getInstance() {
        if (instance == null) instance = new ZoneManager();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
//        this.configs = Core.getConfigs();
    }

    ZoneManager() {
//        this.configManager = plugin.getConfigManager();
    }

    public Zone createZone(String name, World world, UUID playerUUID) {
        int id = generateId();

        Zone zone = new Zone(id, name, world, playerUUID);

        zones.put(id, zone);

        saveZone(zone);

        return zone;
    }

    public Zone createZone(int id, String name, World world, UUID playerUUID) {
//        int id = generateId();

        Zone zone = new Zone(id, name, world, playerUUID);

        zones.put(id, zone);

        saveZone(zone);

        return zone;
    }

    public Zone createZone(String name, World world, List<Attraction> attractions, UUID playerUUID) {
        int id = generateId();

        Zone zone = new Zone(id, name, world, attractions, playerUUID);

        zones.put(id, zone);

        saveZone(zone);

        return zone;
    }

    public Zone createZone(int id, String name, World world, List<Attraction> attractions, UUID playerUUID) {
//        int id = generateId();

        Zone zone = new Zone(id, name, world, attractions, playerUUID);

        zones.put(id, zone);

        saveZone(zone);

        return zone;
    }

    public void loadZones() {
        if (configs.getZones().getConfigurationSection("zones") == null) {
            MsgUtil.NOZONES.msg(Bukkit.getConsoleSender());
            return;
        }

        MsgUtil.ZONES_LOADING.msg(Bukkit.getConsoleSender());

        for (String id : configs.getZones().getConfigurationSection("zones").getKeys(false)) {
            if (Util.isNumber(id)) {
                int zoneId = Integer.parseInt(id);

                String path = "zones." + zoneId;

                String name = configManager.getRawString(configs.getZones(), path + ".name");
                String worldName = configManager.getRawString(configs.getZones(), path + ".world");
                List<?> attractions = configManager.getList(configs.getZones(), path + ".attractions");
                UUID playerUUID = configManager.getUUID(configs.getZones(), path + ".owner");

//                Zone zone = ZoneManager.getZone(zoneName);
                World world = Bukkit.getWorld(worldName);

                Zone zone = createZone(zoneId, name, world, (List<Attraction>) attractions, playerUUID);

                Map<String, String> map = new HashMap<>();
                map.put("%object%", zone.getName());

                MsgUtil.SUCCES_LOADED.msg(Bukkit.getConsoleSender(), map, false);
            }
        }
    }

    public boolean saveZone(Zone zone) {
        int id = zone.getId();
        String path = "zones." + id;

        ArrayList<Boolean> data = new ArrayList<>();

        data.add(configManager.setData(configs.getZones(), path + ".name", zone.getName()));
        data.add(configManager.setData(configs.getZones(), path + ".world", zone.getWorld().getName()));
        data.add(configManager.setData(configs.getZones(), path + ".attractions", zone.getAttractions()));
        data.add(configManager.setData(configs.getZones(), path + ".owner", zone.getOwner().toString()));

        Set<Boolean> checks = new HashSet<>(data);

        int length = checks.size();
        int successes = Collections.frequency(checks, true);

        return length == successes;
    }

    public boolean removeZone(Zone zone) {
        int id = zone.getId();
        String path = "zones." + id;

        for (Attraction attraction : zone.getAttractions()) {
            attraction.removeZone(zone.getName());

            AttractionManager.getInstance().saveAttraction(attraction);
        }

        configManager.setData(configs.getZones(), path, null);
        zones.remove(id);

        return true;
    }

    public Map<Integer, Zone> getZones() {
        return zones;
    }

    public void resetZones() {
        zones.clear();
    }

    public Zone getZone(int id) {
        return zones.get(id);
    }

    public Zone getZone(String zoneName) {
        for (Zone zone : zones.values()) {
            if (zone.getName().equalsIgnoreCase(zoneName)) {
                return getZone(zone.getId());
            }
        }

        return null;
    }

    private int generateId() {
        if (zones.size() == 0) return 1;

        return Util.getHighestId(zones) + 1;
    }


}
