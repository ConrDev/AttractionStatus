package me.conrdev.attractionstatus.events;

import me.conrdev.attractionstatus.commands.attractions.StatusList;
import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.managers.SignsManager;
import me.conrdev.attractionstatus.signs.ASSign;
import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

import static javax.management.remote.JMXConnectionNotification.OPENED;

public class AttractionSign implements Listener {

    @EventHandler
    private void onSignPlace(SignChangeEvent event) {
        final Player player = event.getPlayer();
        final Sign sign = (Sign) event.getBlock().getState();
//        ASSign.isAttraction(firstLine)
        if (!ASSign.isSign(event.getLine(0))) return;

        if (!player.hasPermission("attractionstatus.signs.create")) {
            MsgUtil.NOPERM.msg(player);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("%attraction%", Util.title(event.getLine(1)));

        if (!ASSign.isAttraction(event.getLine(1))) {
            MsgUtil.ATTRACTION_NOT_FOUND.msg(player, map, false);
            EffectUtil.playSound(event.getBlock(), Sound.ENTITY_VILLAGER_NO);
            return;
        }

        Attraction attraction = ASSign.getAttraction(event.getLine(1));

        SignsManager.getInstance().createSign(attraction.getName(), sign.getLocation(), player.getUniqueId());

        for (int i = 0; i < 4; i++) {
            String text = ASSign.setDefaults(attraction, i);
            event.setLine(i, text);
        }

        EffectUtil.playEffect(event.getBlock(), Particle.VILLAGER_HAPPY, Sound.ENTITY_VILLAGER_CELEBRATE);

        MsgUtil.ATTRACTION_SIGN_CREATED.msg(player, map, false);

    }

    @EventHandler
    private void onSignBreak(BlockBreakEvent event) {
        final BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP};
        Sign sign = (event.getBlock().getState() instanceof Sign
                ? (Sign) event.getBlock().getState()
                : null
        );

        if (sign == null) {
            for (BlockFace face : faces) {
                Block attatchedBlock = event.getBlock().getRelative(face);

                if (attatchedBlock.getState() instanceof Sign) {
                    sign = (Sign) attatchedBlock.getState();
                    break;
                }
            }
        }

        if (sign == null) return;
        final Player player = event.getPlayer();

//        Util.msg(player, SignsManager.getInstance().getSign(sign.getLocation()).getOwner().toString());

        if (!SignsManager.getInstance().isASSign(sign.getLocation())) return;

        if (player.getUniqueId().equals(SignsManager.getInstance().getSign(sign.getLocation()).getOwner())
                && !player.hasPermission("attractionstatus.signs.removeAll")) {
            MsgUtil.NOTOWNER.msg(player);
            EffectUtil.playEffect(sign.getBlock(), Particle.VILLAGER_ANGRY, Sound.ENTITY_VILLAGER_NO);

            event.setCancelled(true);
            return;
        }

        if (SignsManager.getInstance().removeSign(sign.getLocation())) {
            Map<String, String> map = new HashMap<>();
            map.put("%attraction%", Util.title(sign.getLine(0)));

            EffectUtil.playEffect(sign.getBlock(), Particle.BLOCK_CRACK, Material.REDSTONE_BLOCK.createBlockData(), Sound.ENTITY_VILLAGER_DEATH);

            MsgUtil.ATTRACTION_SIGN_REMOVED.msg(player, map, false);

            return;
        }

        EffectUtil.playEffect(sign.getBlock(), Particle.VILLAGER_ANGRY, Sound.ENTITY_VILLAGER_NO);
        event.setCancelled(true);
    }

    @EventHandler
    private void onSignClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
                event.getClickedBlock().getState() instanceof Sign) {

            final Sign sign = (Sign) event.getClickedBlock().getState();

//            if (!ASSign.isAttraction(sign.getLine(0))) return;
            if (!SignsManager.getInstance().isASSign(sign.getLocation())) return;

            Map<String, String> map = new HashMap<>();
            map.put("%object%", Util.title(sign.getLine(0)));

//                if (!ASSign.isAttraction(sign.getLine(1))) {
//                    MsgUtil.ATTRACTION_NOT_FOUND.msg(player, map, false);
//                    EffectUtil.playSound(sign.getBlock(), Sound.ENTITY_VILLAGER_NO);
//                    return;
//                }

            Attraction attraction = ASSign.getAttraction(sign.getLine(0));

            attraction.telepertPlayer(player);
//            ASSign.toAttraction(player, attraction.getLocation());
//            EffectUtil.playEffect(attraction.getLocation().getBlock(), Particle.FIREWORKS_SPARK,Sound.ITEM_CHORUS_FRUIT_TELEPORT);

            MsgUtil.SUCCES_TP.msg(player, map, false);

        }
    }

}
