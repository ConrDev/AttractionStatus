package me.conrdev.attractionstatus.managers;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.commands.attractions.StatusList;
import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.objects.Sign;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.type.WallSign;

import java.util.*;

public class SignsManager {

    private static SignsManager instance = null;
    private Core plugin = null;

    private final ConfigManager configManager = Core.getConfigManager();
    private final Configs configs = Core.getConfigs();

    private static final Map<Integer, Sign> signs = new HashMap<>();

    public static SignsManager getInstance() {
        if (instance == null) instance = new SignsManager();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
//        this.configs = Core.getConfigs();
    }

    SignsManager() {
//        this.configManager = plugin.getConfigManager();
    }

    public Sign createSign(String attractionName, Location location, UUID playerUUID) {
        int id = generateId();

        Sign sign = new Sign(id, attractionName, location, playerUUID);

        signs.put(id, sign);

        saveSign(sign);

        Attraction attraction = AttractionManager.getInstance().getAttraction(attractionName);

        if (attraction.getStatus(false).equalsIgnoreCase(StatusList.OPENED.toString())) {
            EffectUtil.spawnParticleLoop(location.getBlock(), Particle.VILLAGER_HAPPY);
        }

        return sign;
    }

    public Sign createSign(int id, String attractionName, Location location, UUID playerUUID) {

        Sign sign = new Sign(id, attractionName, location, playerUUID);

        signs.put(id, sign);

        saveSign(sign);

        Attraction attraction = AttractionManager.getInstance().getAttraction(attractionName);

        if (attraction.getStatus(false).equalsIgnoreCase(StatusList.OPENED.toString())) {
            EffectUtil.spawnParticleLoop(location.getBlock(), Particle.VILLAGER_HAPPY);
        }

        return sign;
    }

    public void loadSigns() {
        if (configs.getSigns().getConfigurationSection("signs") == null) {
            MsgUtil.NOZONES.msg(Bukkit.getConsoleSender());
            return;
        }

        MsgUtil.SIGNS_LOADING.msg(Bukkit.getConsoleSender());

        for (String id : configs.getSigns().getConfigurationSection("signs").getKeys(false)) {
            if (Util.isNumber(id)) {
                int signId = Integer.parseInt(id);

                String path = "signs." + signId;

                String attraction = configManager.getRawString(configs.getSigns(), path + ".attraction");
                Location location = configManager.getRawLocation(configs.getSigns(), path + ".location");
                UUID playerUUID = configManager.getUUID(configs.getSigns(), path + ".owner");

                if (!(location.getBlock().getState() instanceof org.bukkit.block.Sign)) {
                    configManager.setData(configs.getSigns(), path, null);
                    continue;
                }

                Sign sign = createSign(signId, attraction, location, playerUUID);

                Map<String, String> map = new HashMap<>();
                map.put("%object%", sign.getAttraction() + " Sign");

                MsgUtil.SUCCES_LOADED.msg(Bukkit.getConsoleSender(), map, false);
            }
        }
    }

    public boolean saveSign(Sign sign) {
        int id = sign.getId();
        String path = "signs." + id;

        ArrayList<Boolean> data = new ArrayList<>();

        data.add(configManager.setData(configs.getSigns(), path + ".attraction", sign.getAttraction()));
        data.add(configManager.setLocation(configs.getSigns(), path + ".location", sign.getLocation()));
        data.add(configManager.setData(configs.getSigns(), path + ".owner", sign.getOwner().toString()));

        Set<Boolean> checks = new HashSet<>(data);

        int length = checks.size();
        int successes = Collections.frequency(checks, true);

        return length == successes;
    }

    public boolean removeSign(Sign sign) {
        int id = sign.getId();
        String path = "signs." + id;

        configManager.setData(configs.getSigns(), path, null);
        signs.remove(id);

        // Checking and removing IF has particles
        EffectUtil.removeParticleLoop(sign.getLocation().getBlock());

        return true;
    }

    public boolean removeSign(Location location) {
        int id = getSign(location).getId();
        String path = "signs." + id;

        configManager.setData(configs.getSigns(), path, null);
        signs.remove(id);

        // Checking and removing IF has particles
        EffectUtil.removeParticleLoop(location.getBlock());

        return true;
    }

    public Map<Integer, Sign> getSigns() {
        return signs;
    }

    public void resetSigns() {
        signs.clear();
    }

    public Sign getSign(int id) {
        return signs.get(id);
    }

    public Sign getSign(String attractionName) {
        for (Sign sign : signs.values()) {
            if (sign.getAttraction().equalsIgnoreCase(attractionName)) {
                return getSign(sign.getId());
            }
        }

        return null;
    }

    public Sign getSign(Location location) {
        for (Sign sign : signs.values()) {
            if (sign.getLocation().equals(location)) {
                return getSign(sign.getId());
            }
        }

        return null;
    }

    public Set<Sign> getSigns(String attractionName) {
        Set<Sign> signSet = new HashSet<>();

        for (Sign sign : signs.values()) {
            if (sign.getAttraction().equalsIgnoreCase(attractionName)) {
                signSet.add(sign);
            }
        }

        return signSet;
    }

    public boolean isASSign(Location location) {
        return getSign(location) != null;
    }

    private int generateId() {
        if (signs.size() == 0) return 1;

        return Util.getHighestId(signs) + 1;
    }

}
