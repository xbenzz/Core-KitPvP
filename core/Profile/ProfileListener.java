package me.core.Profile;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;

public class ProfileListener implements Listener {
  
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event)  {
		Player p = event.getPlayer();
        Profile profile = Kit.getProfileManager().getUser(p.getUniqueId()).get();
		event.setJoinMessage(null);

		if (!profile.getUsername().equalsIgnoreCase(p.getName())) {
			profile.setSQLUsername(p.getName());
		}
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
		UUID uuid = event.getUniqueId();
		String ip = event.getAddress().getHostAddress();
		if (event.getLoginResult() == Result.KICK_BANNED) {
			return;
		}
		Profile p = new Profile(uuid, false);
		p.load();
		
		if (!p.hasIP(ip)) {
			p.logIP(ip);
		}
		
		KitPlayer kit = new KitPlayer(uuid, false);
		kit.load();
	}
  
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player p = event.getPlayer();
        Optional<Profile> optional = Kit.getProfileManager().getUser(p.getUniqueId());
        if (optional.isPresent()) {
        	optional.get().save();
        }
        Optional<KitPlayer> kitOp = Kit.getKitManager().getUser(p.getUniqueId());
        if (kitOp.isPresent()) {
        	kitOp.get().save();
        }
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);
		Player p = event.getPlayer();
        Optional<Profile> optional = Kit.getProfileManager().getUser(p.getUniqueId());
        if (optional.isPresent()) {
        	optional.get().save();
        }
        
        Optional<KitPlayer> kitOp = Kit.getKitManager().getUser(p.getUniqueId());
        if (kitOp.isPresent()) {
        	kitOp.get().save();
        }
	}	

}
