package me.conrdev.attractionstatus.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

public class EffectUtil{

    public static void playEffect(final Block block, final Enum <Particle> particleName, final Enum <Sound> soundName) {
        spawnParticle(block, particleName);
        playSound(block, soundName);
    }

    public static void playSound(final Block block, final Enum <Sound> soundName) {
        Sound sound = Sound.valueOf(soundName.name());
        World world = block.getWorld();
        Location blockLocation = new Location(world, block.getX(), block.getY(), block.getZ());


        world.playSound(blockLocation, sound, 1.0f, 0.9f);
    }

    public static void spawnParticle(final Block block, final Enum <Particle> particleName) {
        Particle particle = Particle.valueOf(particleName.name());
        World world = block.getWorld();
        Location blockLocation = new Location(world, block.getX(), block.getY(), block.getZ());

        double[][] locations = {
                {0.5d, 0.5d, 0.5d},
                {1d, 1d, 0.5d},
                {0.5d, 0.5d, 1d},
                {0.5d, 1d, 0.5d},
        };

        for (double[] xyz : locations) {
            world.spawnParticle(
                    particle,
                    blockLocation.add(xyz[0], xyz[1], xyz[2]),
                    1
            );
        }
    }

}
