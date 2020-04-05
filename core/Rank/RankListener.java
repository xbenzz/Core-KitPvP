package me.core.Rank;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import me.Kit.Kit;
import me.core.Profile.Profile;

public class RankListener implements Listener {

	public static HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
  
	@EventHandler(priority=EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Kit.getPlugin(), () -> {
			setPerms(p);
		}, 30L);
	}
  
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		perms.remove(p.getUniqueId());
	}
	
	@EventHandler
	public void onLeave(PlayerKickEvent e) {
		Player p = e.getPlayer();
		perms.remove(p.getUniqueId());
	}
  
	public void setPerms(Player player) {
		PermissionAttachment attachment = player.addAttachment(Kit.getPlugin());
		perms.put(player.getUniqueId(), attachment);
		PermissionAttachment pperms = perms.get(player.getUniqueId());
	  
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	  
		pperms.setPermission("bukkit.command.plugins", false);
		pperms.setPermission("bukkit.command.help", false);
	  	pperms.setPermission("bukkit.command.version", false);
	  	pperms.setPermission("minecraft.command.msg", false);
	  
	  	if (user.getRank().isAboveOrEqual(Rank.OWNER)) {
	  		pperms.setPermission("bukkit.command.plugins", true);
	  		pperms.setPermission("bukkit.command.help", true);
	  		pperms.setPermission("bukkit.command.version", true);
	  		pperms.setPermission("matrix.notify", true);
	  		pperms.setPermission("minesecure.2fa", true);
	  		pperms.setPermission("minesecure.required", true);
	  	} else if (user.getRank().isAboveOrEqual(Rank.MODERATOR)) {
	  		pperms.setPermission("matrix.notify", true);
	  		pperms.setPermission("minesecure.2fa", true);
	  		pperms.setPermission("minesecure.required", true);
	 	} else if (user.getRank().isAboveOrEqual(Rank.HELPER)) {
	 		pperms.setPermission("minesecure.2fa", true);
	 		pperms.setPermission("minesecure.required", true);
	 	} 
	}
    
}
