package me.core.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Handler.ChatHandler;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Cooldowns;
import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener {
	
	  @EventHandler(priority=EventPriority.NORMAL)
	  public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		  if (e.isCancelled()) {
			  return;
		  }
		  Player player = e.getPlayer();
		  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		  Date now = new Date();
		  SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		  String server = Bukkit.getServerName();
		  p.addLog(server, e.getMessage(), format.format(now), "CHAT");
	  }
	  
	  @EventHandler(priority=EventPriority.NORMAL)
	  public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		  if (e.isCancelled()) {
		      return;
		  }
		  Player player = e.getPlayer();
		  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		  Date now = new Date();
		  SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
		  String server = Bukkit.getServerName();
		  p.addLog(server, e.getMessage(), format.format(now), "CMD");
	 }
	  
		
	  @EventHandler(priority=EventPriority.NORMAL)
	  public void onPlayerChat(AsyncPlayerChatEvent event) {
		  if (event.isCancelled()) {
			  return;
		  }
	    
		  if (!isMessageValid(event.getPlayer(), event.getMessage())) {
			  event.setCancelled(true);
			  return;
		  }
		  
		  Player player = event.getPlayer();
		  Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    
		  if ((ChatHandler.isMuted()) && (!profile.getRank().isAboveOrEqual(Rank.HELPER))) {
			  event.setCancelled(true);
			  player.sendMessage(ColorText.translate("&cChat> &6Chat is currently muted."));
			  return;
		  }
	    
		  if (profile.getRank().isAboveOrEqual(Rank.ELITE)) {
			  event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		  }
	    
		  KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
	    
		  String format = "&7[&2&l{level}&7] {rank}{player}&7: &f{message}";
		  format = format.replace("{level}", String.valueOf(kit.getLevel()));
		  format = format.replace("{player}", profile.getRank().getColor() + player.getName());
		  format = format.replace("{rank}", profile.getRank().getColor() + profile.getRank().getPrefix());
		  format = ChatColor.translateAlternateColorCodes('&', format);
		  format = format.replace("%", "%%");
		  format = format.replace("{message}", "%2$s");
		  event.setFormat(format);
	  }
  
  
	  @EventHandler(priority=EventPriority.LOW)
	  public void onCooldown(AsyncPlayerChatEvent e) {
		  Player p = e.getPlayer();
		  if (e.isCancelled()) {
			  return;
		  }
		  Profile profile = Kit.getProfileManager().getUser(p.getUniqueId()).get();
		  if (!profile.getRank().isAboveOrEqual(Rank.VIP)) {
			  if (Cooldowns.isOnCooldown("chat_delay", p)) {
				  e.setCancelled(true);
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cChat> &6Please wait before sending another message."));
			  } else {
				  Cooldowns.addCooldown("chat_delay", p, 3);
			  }
		  }
	  }
  
	  private boolean isMessageValid(Player player, String message) {
		  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		  
		  if (p.getRank().isAboveOrEqual(Rank.ADMIN)) {
			  return true;
		  }
		  
		  String msg = message.toLowerCase();
		  List<String> words = Kit.getPlugin().getConfig().getStringList("Blacklisted-Words");
		  for (String blacklist : words) {
			  if ((msg.startsWith(blacklist.toLowerCase())) || (msg.equals(blacklist.toLowerCase()) || msg.toLowerCase().contains(blacklist))) {
		          player.sendMessage(ColorText.translate("&cChat> &6That word is blacklisted."));
		          return false;
		      }
		  }
		  Pattern pattern = Pattern.compile("(\\\\s|^)[e]+[z]+($|\\\\s)", 2);
		  pattern = Pattern.compile("(\\s|^)(hackers?|hacks?|hax|hacking|cheaters?|cheating|cheats?)($|\\s)", 2);
		  if (pattern.matcher(message).find()) {
			  player.sendMessage(ColorText.translate("&cChat> &6Accusing players of cheating is not allowed."));
			  return false;
		  }
		  return true;
	  }
  
  
  
}
