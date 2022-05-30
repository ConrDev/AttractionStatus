package me.conrdev.attractionstatus.events;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.managers.SignsManager;
import me.conrdev.attractionstatus.signs.ASSign;
import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class AttractionSign implements Listener {

    @EventHandler
    private void onSignPlace(SignChangeEvent event) {
        final Player player = event.getPlayer();
//        ASSign.isAttraction(firstLine)
        if (!ASSign.isSign(event.getLine(0))) return;

        if (!player.hasPermission("attractionstatus.sign")) {
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

        // TODO: SignsManager.getInstance().createSign()
        Attraction attraction = ASSign.getAttraction(event.getLine(1));

        for (int i = 0; i < 4; i++) {
            String text = ASSign.setDefaults(attraction, i);
            event.setLine(i, text);
        }

        EffectUtil.playEffect(event.getBlock(), Particle.VILLAGER_HAPPY, Sound.ENTITY_VILLAGER_CELEBRATE);

        MsgUtil.ATTRACTION_SIGN_CREATED.msg(player, map, false);

    }

    @EventHandler
    private void onSignClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
                event.getClickedBlock().getState() instanceof Sign) {

            final Sign sign = (Sign) event.getClickedBlock().getState();

            if (!ASSign.isAttraction(sign.getLine(0))) return;

            Map<String, String> map = new HashMap<>();
            map.put("%object%", Util.title(sign.getLine(0)));

//                if (!ASSign.isAttraction(sign.getLine(1))) {
//                    MsgUtil.ATTRACTION_NOT_FOUND.msg(player, map, false);
//                    EffectUtil.playSound(sign.getBlock(), Sound.ENTITY_VILLAGER_NO);
//                    return;
//                }

            // TODO: SignsManager.getInstance().isASSign(event.getClickedBlock().getLocation())
            Attraction attraction = ASSign.getAttraction(sign.getLine(0));

            ASSign.toAttraction(player, attraction.getLocation());

            MsgUtil.SUCCES_TP.msg(player, map, false);

        }
    }

}
