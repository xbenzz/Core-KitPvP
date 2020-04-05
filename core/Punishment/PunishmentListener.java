package me.core.Punishment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.Kit.Kit;
import me.core.Utilities.ColorText;

public class PunishmentListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onMuteChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Optional<Punishment> op = Kit.getProfileManager().getMute(player.getUniqueId());
		if (!op.isPresent()) {
			return;
		}
		Punishment punish = Kit.getProfileManager().getMute(player.getUniqueId()).get();
		ColorText msg = new ColorText();
		if (punish.isActive()) {
			if (punish.isPermanent()) {
				event.setCancelled(true);
				player.sendMessage(msg.muteMessage(punish.getReason(), "Permanent"));
				return;
			} else {
				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis()  <= 0L) {
					punish.remove();
					event.setCancelled(false);
					return;
				}
				Long end = punish.getExpire() - System.currentTimeMillis();
				String format = DurationFormatUtils.formatDurationWords(end.longValue(), true, true);
				event.setCancelled(true);
				player.sendMessage(msg.muteMessage(punish.getReason(), format));
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onMuteCmd(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
	    String message = event.getMessage().toLowerCase();
	    List<String> cmds = Kit.getPlugin().getConfig().getStringList("Prevented-Cmds");
		Optional<Punishment> op = Kit.getProfileManager().getMute(player.getUniqueId());
		if (!op.isPresent()) {
			return;
		}
		Punishment punish = Kit.getProfileManager().getMute(player.getUniqueId()).get();
		ColorText msg = new ColorText();
	    for (String blacklist : cmds) {
	    	if ((message.startsWith("/" + blacklist.toLowerCase() + " ")) || (message.equals("/" + blacklist.toLowerCase()))) {
	    		if (punish.isActive()) {
	    			if (punish.isPermanent()) {
	    				event.setCancelled(true);
	    				player.sendMessage(msg.muteMessage(punish.getReason(), "Permanent"));
	    				return;
	    			} else {
	    				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis()  <= 0L) {
	    					punish.remove();
	    					event.setCancelled(false);
	    					return;
	    				}
	    				Long end = punish.getExpire() - System.currentTimeMillis();
	    				String format = DurationFormatUtils.formatDurationWords(end.longValue(), true, true);
	    				event.setCancelled(true);
	    				player.sendMessage(msg.muteMessage(punish.getReason(), format));
	    				return;
	    			}
	    		}
			} 
	    } 
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
		UUID uuid = event.getUniqueId();
		Punishment punish = new Punishment(uuid, Type.BAN);
		ColorText msg = new ColorText();
		if (punish.isActive() && punish.getID() != 0) {
			if (punish.isPermanent()) {
				event.disallow(Result.KICK_BANNED, msg.banMessage(punish.getReason(), "Permanent"));
			} else {
				if (Long.valueOf(punish.getExpire()) - System.currentTimeMillis() <= 0L) {
					punish.remove();
					event.allow();
					return;
				}
				Long end = punish.getExpire() - System.currentTimeMillis();
				String format = DurationFormatUtils.formatDurationWords(end.longValue(), true, true);
				event.disallow(Result.KICK_BANNED, msg.banMessage(punish.getReason(), format));
				return;
			}
		}
	} 
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onIPBanLogin(AsyncPlayerPreLoginEvent event) {
		String ip = event.getAddress().getHostAddress();
		Punishment punish = new Punishment(ip, Type.IP);
		ColorText msg = new ColorText();
		if (punish.isActive() && punish.getID() != 0) {
			event.disallow(Result.KICK_BANNED, msg.ipbanMessage(punish.getReason()));
		}
	}
  
}
