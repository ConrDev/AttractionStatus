package me.conrdev.attractionstatus.utils;

import me.conrdev.attractionstatus.Core;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class EffectUtil {

    private static final Map<Block, BukkitTask> effectTasks = new HashMap<>();

    public static void playEffect(final Block block, final Enum<Particle> particleName, BlockData materialData, final Enum<Sound> soundName) {
        spawnParticle(block, particleName, materialData);
        playSound(block, soundName);
    }

    public static void playEffect(final Block block, final Enum<Particle> particleName, final Enum<Sound> soundName) {
        playEffect(block, particleName, null, soundName);
    }

    public static void playSound(final Block block, final Enum<Sound> soundName) {
        Sound sound = Sound.valueOf(soundName.name());
        World world = block.getWorld();
        Location blockLocation = new Location(world, block.getX(), block.getY(), block.getZ());


        world.playSound(blockLocation, sound, 1.0f, 0.9f);
    }

    public static void spawnParticle(final Block block, final Enum<Particle> particleName, BlockData materialData) {
        double[][] locations = {
                {0.0d, 0.0d, 0.0d}
        };

        Particle particle = Particle.valueOf(particleName.name());
        World world = block.getWorld();

        Location blockCenter = block.getLocation().add(0.5, 0.5, 0.5);

        for (double[] xyz : locations) {

            world.spawnParticle(
                    particle,
                    blockCenter.add(xyz[0], xyz[1], xyz[2]),
//                    blockLocation,
                    5,
                    materialData
            );
        }
    }

    public static void spawnSignParticle(final Block block, final Enum<Particle> particleName) {
        boolean WallSign = (block.getBlockData() instanceof WallSign);

        /* particles around the sound
         * - bottom left
         * - bottom right
         * - top left
         * - top right
         */
        double[][] locations = {
                {-0.5, -0.3, (WallSign ? 0.35 : -0.05)},
                {1, 0, 0},
                {0, 0.5, 0},
                {-1, 0, 0}
        };

        Particle particle = Particle.valueOf(particleName.name());
        World world = block.getWorld();

        BlockFace signFace = (WallSign
                ? ((Directional) block.getBlockData()).getFacing()
                : ((Sign) block.getBlockData()).getRotation());

        float yaw = Util.faceToYaw(signFace);

        Location blockCenter = (WallSign
                ? block.getLocation().add(0.5, 0.5, 0.5)
                : block.getLocation().add(0.5, 0.85, 0.5));

        for (double[] xyz : locations) {
            double angle = Math.toRadians(yaw);
//            double angle = yaw * Math.PI / 180;

            double yCos = Math.cos(-angle);
            double ySin = Math.sin(-angle);

            double x = xyz[0]; //* Math.cos(-angle);
            double z = xyz[2]; //* Math.sin(-angle);

            Vector v = new Vector(x, 0, z);
            Vector newVector = rotateAroundYAxis(v, yCos, ySin);

            world.spawnParticle(
                    particle,
                    blockCenter.add(newVector.getX(), xyz[1], newVector.getZ()),
//                    blockLocation,
                    1
            );
        }
    }

    public static void spawnParticleLoop(final Block block, final Enum<Particle> particleName) {

        BukkitTask effectTask = new BukkitRunnable() {

            @Override
            public void run() {

                spawnSignParticle(
                        block,
                        particleName
                );
            }

        }.runTaskTimer(Core.getInstance(), 0, 10);

        effectTasks.put(block, effectTask);
    }

    public static BukkitTask getParticleTask(final Block block) {
        for (Map.Entry<Block, BukkitTask> effectTask : effectTasks.entrySet()) {
            if (effectTask.getKey().equals(block)) {
                return effectTask.getValue();
            }
        }

        return null;
    }

    public static Map.Entry<Block, BukkitTask> getParticleLoop(final Block block) {
        for (Map.Entry<Block, BukkitTask> effectTask : effectTasks.entrySet()) {
            if (effectTask.getKey().equals(block)) {
                return effectTask;
            }
        }

        return null;
    }

    public static void removeParticleLoop(final Block block) {
        Map.Entry<Block, BukkitTask> effectTask = getParticleLoop(block);

        if (effectTask == null) return;

        effectTask.getValue().cancel();
        effectTasks.remove(block);
    }

    public static void resetParticles() {
        effectTasks.clear();
    }

    private static Vector rotateAroundYAxis(Vector v, double cos, double sin) {
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(-x).setZ(-z);
    }

}
