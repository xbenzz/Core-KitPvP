package me.core.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Kit.Kit;
import me.core.Manager.FreezeManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;

public class FreezeListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (FreezeManager.isFrozen(player)) {
			FreezeManager.setFreeze(player, false);
			for (Profile prof : Kit.getProfileManager().getUsersAboveOrERank(Rank.MODERATOR)) {
				Player online = Bukkit.getPlayer(prof.getUUID());
				online.sendMessage(ColorText.translate("&cFreeze> &e" + player.getName() + " &6has logged out while frozen!"));
			}
		}
	}
  
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		  Player player = event.getPlayer();
		  Location from = event.getFrom();
		  Location to = event.getTo();
		  if ((from.getBlockX() == to.getBlockX()) && (from.getBlockY() == to.getBlockY()) && (from.getBlockZ() == to.getBlockZ())) {
			  return;
		  }
		  if (FreezeManager.isFrozen(player)) {
			  event.setTo(event.getFrom());
		  }
	  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    if ((event.getEntity() instanceof Player)) {
      Player player = (Player)event.getEntity();
      if (FreezeManager.isFrozen(player)) {
        event.setCancelled(true);
      }
      if (((event.getDamager() instanceof Player)) && 
        (FreezeManager.isFrozen((Player)event.getDamager()))) {
        event.setCancelled(true);
      }
    }
  }
  
  @EventHandler
  public void onPlayerDrop(PlayerDropItemEvent event)
  {
    Player player = event.getPlayer();
    if (FreezeManager.isFrozen(player)) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    Player player = event.getPlayer();
    if (FreezeManager.isFrozen(player)) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event)
  {
    Player player = event.getPlayer();
    if (FreezeManager.isFrozen(player)) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    Player player = (Player)event.getWhoClicked();
    if (FreezeManager.isFrozen(player)) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
	  final Player player = (Player)event.getPlayer();
	  if (FreezeManager.isFrozen(player)) {
		  new BukkitRunnable() {
			  public void run() {
				  player.openInventory(FreezeManager.getFreezeInventory());
			  }
		  }.runTaskLater(Kit.getPlugin(), 3L);
	  }
  }
  
}
