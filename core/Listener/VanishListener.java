package me.core.Listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Kit.Kit;
import me.core.Manager.VanishManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;

public class VanishListener implements Listener {
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Kit.getPlugin(), () -> {
			for (UUID l : VanishManager.getVanished()) {
				Player pl = Bukkit.getPlayer(l);
				Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
				Profile profile2 = Kit.getProfileManager().getUser(pl.getUniqueId()).get();
		       
				if (!profile.getRank().isAboveOrEqual(Rank.HELPER) && profile2.getRank().isAboveOrEqual(Rank.HELPER)) { 
					player.hidePlayer(pl);
				} else if (!profile.getRank().isAboveOrEqual(Rank.MODERATOR) && profile2.getRank().isAboveOrEqual(Rank.MODERATOR)) {
					player.hidePlayer(pl);
				} else if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD) && profile2.getRank().isAboveOrEqual(Rank.MODERATOR)) {
					player.hidePlayer(pl);
				} else if (!profile.getRank().isAboveOrEqual(Rank.ADMIN) && profile2.getRank().isAboveOrEqual(Rank.ADMIN)) {
					player.hidePlayer(pl);
				} else if (!profile.getRank().isAboveOrEqual(Rank.HELPER) && profile2.getRank().isAboveOrEqual(Rank.YT)) {
					player.hidePlayer(pl);
				}
			}
		}, 30L);
	 }
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		  Player player = event.getPlayer();
		  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		  if (VanishManager.isVanished(player) && !p.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			  VanishManager.setVanished(player, false);
			  player.setFlying(false);
			  player.setAllowFlight(false);
	      }
		  if (!p.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			  player.setFlying(false);
			  player.setAllowFlight(false);
		  }
	  }
	
	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent event) {
		  Player player = event.getPlayer();
		  if (VanishManager.isVanished(player)) {
			  VanishManager.setVanished(player, false);
	      }
	  }

	  @EventHandler
	  public void onEntityDamage(EntityDamageByEntityEvent event) {
	    if ((event.getEntity() instanceof Player)) {
	      Player player = (Player)event.getEntity();
	      if (VanishManager.isVanished(player)) {
	    	  event.setCancelled(true);
	      }
	      if (((event.getDamager() instanceof Player)) && 
	    	   (VanishManager.isVanished((Player)event.getDamager()))) {
	    	  	event.setCancelled(true);
	      }
	    }
	  }
	  
	  @EventHandler
	  public void onPlayerPick(PlayerPickupItemEvent event) {
	    Player player = event.getPlayer();
	    if (VanishManager.isVanished(player)) {
	      event.setCancelled(true);
	    }
	  }
	  
	  @EventHandler
	  public void onPlayerDrop(PlayerDropItemEvent event) {
	    Player player = event.getPlayer();
	    if (VanishManager.isVanished(player)) {
	      event.setCancelled(true);
	    }
	  }
	  
	  @EventHandler
	  public void onBlockPlace(BlockPlaceEvent event) {
	    Player player = event.getPlayer();
	    if (VanishManager.isVanished(player)) {
	      event.setCancelled(true);
	    }
	  }
	  
	  @EventHandler
	  public void onBlockBreak(BlockBreakEvent event) {
	    Player player = event.getPlayer();
	    if (VanishManager.isVanished(player)) {
	      event.setCancelled(true);
	    }
	  }

}
