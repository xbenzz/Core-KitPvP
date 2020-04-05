package me.core.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;

public class TeleportManager {
	
	 public static void playerToSelf(Player player, Player target) {
		 Profile tProfile = Kit.getProfileManager().getUser(target.getUniqueId()).get();
		    
		 target.teleport(player.getLocation());
		 player.sendMessage(ColorText.translate("&cTeleport> " + tProfile.getRank().getColor() + target.getName() + " &6has been teleported to you."));
	 }
		  
	 public static void teleportToPlayer(Player player, Player target) {
		 Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
		    
		 player.sendMessage(ColorText.translate("&cTeleport> &6You teleported to " + t.getRank().getColor() + target.getName()));
		 player.teleport(target.getLocation());
	 }
		 
	 public static void teleportCoords(Player player, int x, int y, int z) {
		 player.teleport(new Location(player.getWorld(), x, y, z));
		 player.sendMessage(ColorText.translate("&cTeleport> &6You teleported to &e" + x + "&6, &e" + y + "&6, &e" + z + "&6."));
	 }
		  
	 public static void teleportAllPlayers(Player player) {
		 for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			 p.teleport(player.getLocation());
		 }
		 player.sendMessage(ColorText.translate("&cTeleport> &e" + Bukkit.getOnlinePlayers().size() + " &6players were teleported to your Location."));
	 }

}
