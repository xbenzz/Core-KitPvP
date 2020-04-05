package me.core.Listener;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import me.Kit.Kit;
import me.core.Commands.MessageCommand;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class ServerListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOW)
	public void onTryJoin(PlayerLoginEvent event) {
		UUID player = event.getPlayer().getUniqueId();
	    Profile profile = Kit.getProfileManager().getUser(player).get();
	    if (event.getResult() == Result.KICK_FULL) {
	    	if (profile.getRank().isAboveOrEqual(Rank.VIP)) {
	    		event.allow();
	    	} else {
	    		event.disallow(Result.KICK_FULL, ColorText.translate("&cServer is full! \n\n &cBuy VIP rank at &estore.vendettapvp.org"));
	     	}
	    }
	}
	  
	  @EventHandler
	  public void onCommandProcess(PlayerCommandPreprocessEvent event) {
	    Player player = event.getPlayer();
	    if ((event.getMessage().toLowerCase().startsWith("//calc")) || (event.getMessage().toLowerCase().startsWith("//eval")) || (event.getMessage().toLowerCase().startsWith("//solve")) || (event.getMessage().toLowerCase().startsWith("/bukkit:")) || (event.getMessage().toLowerCase().startsWith("/me")) || (event.getMessage().toLowerCase().startsWith("/bukkit:me")) || (event.getMessage().toLowerCase().startsWith("/minecraft:")) || (event.getMessage().toLowerCase().startsWith("/minecraft:me"))) {
	    	player.sendMessage(Utils.NO_PERMISSION);
	    	event.setCancelled(true);
	    }
	  }
	  
	  @EventHandler(priority = EventPriority.NORMAL)
	  public void onCommand(PlayerCommandPreprocessEvent event) {
		  boolean plugins = event.getMessage().toLowerCase().startsWith("/plugins");
		  boolean pl = event.getMessage().toLowerCase().startsWith("/pl") && !event.getMessage().toLowerCase().startsWith("/plugman") && !event.getMessage().toLowerCase().startsWith("/plane") && !event.getMessage().toLowerCase().startsWith("/planeshop") && !event.getMessage().toLowerCase().startsWith("/player") && !event.getMessage().toLowerCase().startsWith("/playtime");
		  boolean icanhasbukkit = event.getMessage().toLowerCase().startsWith("/icanhasbukkit");
		  boolean unknown = event.getMessage().toLowerCase().startsWith("/?");
		  boolean version = event.getMessage().toLowerCase().startsWith("/version");
		  boolean ver = event.getMessage().toLowerCase().startsWith("/ver");
		  boolean bukkitplugin = event.getMessage().toLowerCase().startsWith("/bukkit:plugins");
		  boolean bukkitpl = event.getMessage().toLowerCase().startsWith("/bukkit:pl");
		  boolean bukkitunknown = event.getMessage().toLowerCase().startsWith("/bukkit:?");
		  boolean about = event.getMessage().toLowerCase().startsWith("/about");
		  boolean a = event.getMessage().equalsIgnoreCase("/a");
		  boolean bukkitabout = event.getMessage().toLowerCase().startsWith("/bukkit:about");
		  boolean bukkita = event.getMessage().toLowerCase().startsWith("/bukkit:a");
		  boolean bukkitversion = event.getMessage().toLowerCase().startsWith("/bukkit:version");
		  boolean bukkitver = event.getMessage().toLowerCase().startsWith("/bukkit:ver");
		  boolean bukkithelp = event.getMessage().toLowerCase().startsWith("/bukkit:help");
		    
		  Player player = event.getPlayer();
		  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		    
		  if ((plugins) || (pl) || (bukkitunknown) ||  (unknown) ||  (bukkitplugin) ||  (bukkitpl) || (version) || (ver) ||  (icanhasbukkit) ||  (a) ||  (about) ||  (bukkitversion) ||  (bukkitver)||  (bukkitabout)  ||  (bukkita) ||  (bukkithelp)) {
			  if (!p.getRank().isAboveOrEqual(Rank.OWNER)) {
	 	    		event.setCancelled(true);
	 	      }
	 	  }
	  }
	  
	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent event) {
		  Player p = event.getPlayer();
		  if (MessageCommand.reply.containsKey(p.getUniqueId())) {
			  MessageCommand.reply.remove(p.getUniqueId());
		  }
	  }	
	
	 @EventHandler
	 public void onEntitySpawn(CreatureSpawnEvent event) {
	    if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
	      return;
	    }
	    event.setCancelled(true);
	  }
	  
	  @EventHandler
	  public void onFoodLevelChange(FoodLevelChangeEvent event) {
	    event.setFoodLevel(20);
	  }
	  
	  @EventHandler
	  public void onWeather(WeatherChangeEvent event) {
	    event.setCancelled(true);
	  }
	  
	  @EventHandler
	  public void onBlockBreak(BlockBreakEvent event) {
	    Player player = event.getPlayer();
		Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (p.getRank().isAboveOrEqual(Rank.ADMIN) && player.getGameMode() == GameMode.CREATIVE) {
	    	return;
	    }
	    event.setCancelled(true);
	  }
	  
	  @EventHandler
	  public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (p.getRank().isAboveOrEqual(Rank.ADMIN) && player.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		event.setCancelled(true);
	  } 
	  
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
	    if (!(event.getEntity() instanceof Player)) {
	      return;
	    }
	    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
	      event.setCancelled(true);
	    }
	}
	
}
