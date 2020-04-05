package me.Kit.Listener;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.Kit.Kit;
import me.Kit.Manager.BotManager;
import me.Kit.Manager.CombatManager;
import me.Kit.Manager.KitManager;
import me.Kit.PvPBot.CitizensNPC;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class DeathListener implements Listener {
	
	  @EventHandler
	  public void onJoin(PlayerJoinEvent event) {
	      Player p = event.getPlayer();
	      p.setGameMode(GameMode.SURVIVAL);
	      KitManager.kitOnJoin(p);
	  }
	  
	  @EventHandler
	  public void leave(PlayerQuitEvent event) {
		  Player p = event.getPlayer();
		  if (BotManager.getMatches().containsKey(p.getUniqueId())) {
			  CitizensNPC task = BotManager.getMatches().get(p.getUniqueId());
			  if (task.combatTask != null) {
				  task.combatTask.cancel();
				  task.destroy();
				  BotManager.remove(p);
			  }
		  }
	  }
	  
	  @EventHandler
	  public void onDeathEvent(PlayerDeathEvent event) {
		  if ((event.getEntity() instanceof Player)) { 
		    event.setDeathMessage(null);
		    event.getDrops().clear();
		    event.setDroppedExp(0);
		    Player victim = (Player) event.getEntity(); 
 
		    if (event.getEntity().getKiller() instanceof Player) {
		    	Player killer = (Player) event.getEntity().getKiller();
				Optional<KitPlayer> optionalV = Kit.getKitManager().getUser(victim.getUniqueId());
				Optional<KitPlayer> optionalK = Kit.getKitManager().getUser(killer.getUniqueId());
				if (!optionalV.isPresent() || !optionalK.isPresent()) {
					return;
				}
				
				System.out.println("I have ran #1");
				
				KitPlayer vUser = Kit.getKitManager().getUser(victim.getUniqueId()).get();
				KitPlayer kUser = Kit.getKitManager().getUser(killer.getUniqueId()).get();
				Profile vProfile = Kit.getProfileManager().getUser(victim.getUniqueId()).get();
				Profile kProfile = Kit.getProfileManager().getUser(killer.getUniqueId()).get();
				
				int rand = (int) ((Math.random() * 2) + 5);
				int rand2 = (int) (Math.random() * ((8 - 5) + 1 )) + 5;
				
		    	Utils.respawn(victim);
		    	
		    	kUser.setCurrentStreak(kUser.getStreak() + 1);
		    	if (kUser.getStreak() % 5 == 0 && kUser.getStreak() != 0) {
		    		Bukkit.broadcastMessage(ColorText.translate("&cStreak> &6KillStreak of &a&l" + kUser.getStreak() + " &6by " + kProfile.getRank().getColor() + killer.getName()));
		    	}    
		    	
		    	if (vUser.getStreak() > vUser.getHighStreak()) {
		    		vUser.setHighStreak(vUser.getStreak());
		    	}
		    	vUser.setCurrentStreak(0);
		    	
		    	kUser.addKill();
		    	vUser.addDeath();
		    	
		    	System.out.println("I have ran #2");
		    	
				if (kUser.getKills() >= kUser.getKillsNeeded()) {
					kUser.levelup();
				}
		    	
		    	if (kProfile.getRank().isAboveOrEqual(Rank.MVP)) {
		    		kUser.setCoins(kUser.getCoins() + (rand * 2));
		    		kUser.setExp(kUser.getExp() + (rand2 * 2));
		    	} else {
		    		kUser.setCoins(kUser.getCoins() + rand);
		    		kUser.setExp(kUser.getExp() + rand2);
		    	}
		    	
		    	victim.sendMessage(ColorText.translate("&cPvP> &6You were killed by " + kProfile.getRank().getColor() + killer.getName() + "&6."));
		    	killer.sendMessage(ColorText.translate("&cPvP> &6You have killed " + vProfile.getRank().getColor()  + victim.getName() + "&6."));
		    	
		    	System.out.println("I have ran #3");
		    	
		    }
		  }
	  }
	  
	  @EventHandler
	  public void combatTag(EntityDamageByEntityEvent e) {
		  if (e.getDamager() instanceof Player) {
			  if (e.getEntity() instanceof Player) {
				  Player k = (Player) e.getDamager();
				  Player v = (Player) e.getEntity();
				  if (e.isCancelled()) {
					  return;
				  }
				  if (BotManager.getMatches().containsKey(k.getUniqueId())) {
					  return;
				  }
				  if (BotManager.getMatches().containsKey(v.getUniqueId())) {
					  return;
				  }
				  CombatManager.tag(k, v);
			  }
		  }
	  }
	  
	  @EventHandler
	  public void damage(EntityDamageByEntityEvent e) {
		  if (e.getDamager() instanceof Player) {
			  Player p = (Player) e.getDamager();
			  Optional<KitPlayer> optionalK = Kit.getKitManager().getUser(p.getUniqueId());
			  if (e.isCancelled()) {
				  return;
			  }
			  if (BotManager.getMatches().containsKey(p.getUniqueId())) {
				  return;
			  }
			  if (!optionalK.isPresent()) {
				  return;
			  }
			  if (p.getInventory().getItemInHand() != null) {
				  if (p.getInventory().getItemInHand().getType() == Material.DIAMOND_SWORD 
						  || p.getInventory().getItemInHand().getType() == Material.IRON_SWORD 
						  || p.getInventory().getItemInHand().getType() == Material.GOLD_SWORD || p.getInventory().getItemInHand().getType() == Material.STONE_SWORD) {
					  optionalK.get().addSword();
				  }
			  }
		  }
	  }
	  
	  @EventHandler
	  public void arrows(EntityShootBowEvent e) {
		 if (e.getEntity() instanceof Player) {
			  Player p = (Player) e.getEntity();
			  Optional<KitPlayer> optionalK = Kit.getKitManager().getUser(p.getUniqueId());
			  if (e.isCancelled()) {
				  return;
			  }
			  if (BotManager.getMatches().containsKey(p.getUniqueId())) {
				  return;
			  }
			  if (!optionalK.isPresent()) {
				  return;
			  }
			  optionalK.get().addArrow();
		 }
	  }
	  
	  @EventHandler
	  public void gapples(PlayerItemConsumeEvent e) {
		  Player p = e.getPlayer();
		  ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1, (short)1);
		  Optional<KitPlayer> optionalK = Kit.getKitManager().getUser(p.getUniqueId());
		  
		  if (e.isCancelled()) {
			  return;
		  }
		  if (BotManager.getMatches().containsKey(p.getUniqueId())) {
			  return;
		  }
		  if (!optionalK.isPresent()) {
			  return;
		  }
		  if (e.getItem().getType() == Material.GOLDEN_APPLE && e.getItem().getData().equals(item.getData())) {
			  optionalK.get().addGap();
		  }
	  }
	  
	  @EventHandler
	  public void drop(PlayerDropItemEvent event) {
		 ItemStack item = event.getItemDrop().getItemStack();
		 for (ItemStack i : KitManager.getFilter()) {
			 if (item.getType() == i.getType()) {
				 event.getItemDrop().remove();
			 }
		 }
	  }
	  
}
