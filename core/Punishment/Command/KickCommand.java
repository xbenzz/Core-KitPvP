package me.core.Punishment.Command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class KickCommand implements CommandExecutor {
	
	ColorText msg = new ColorText();
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 2) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <reason> "));
			} else {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					Utils.PLAYER_NOT_FOUND(sender, args[0]);
					return false;
				}
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			    	ArrayList<UUID> staffers = Utils.getAllStaff();
					
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						Profile profile = Kit.getProfileManager().getUser(target.getUniqueId()).get();
						String reason = "";
						for (int i = 1; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						final String r = reason;
					
						for (UUID uid : staffers) {
							OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + target.getName() + " &6was kicked by &4CONSOLE"));
						}
						
						if (target.isOnline()) {
							target.getPlayer().kickPlayer(msg.kickMessage(r));
						} else {
							Utils.kickPlayer(target.getName(), msg.kickMessage(r));
						}
					});
				});
			}
			return true;
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
		if (!profile.getRank().isAboveOrEqual(Rank.HELPER)) {
			player.sendMessage(Utils.NO_PERMISSION);
			return true;
		}	
		
		if (args.length < 2) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <reason>"));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Utils.PLAYER_NOT_FOUND(sender, args[0]);
				return false;
			}
			
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    	ArrayList<UUID> staffers = Utils.getAllStaff();
			
  	  			Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
  					String reason = "";
  					for (int i = 1; i < args.length; i++) {
  						reason = reason + args[i] + " ";
  					}
  					final String r = reason;
  				
  					if ((t.getRank().isAboveOrEqual(Rank.HELPER) && (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)))) {
  						sender.sendMessage(ColorText.translate("&cPunish> &6You may not kick other staff members!"));
  						return;
  					}
  				
  	  	  			for (UUID uid : staffers) {
  	  	  				OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
  	  	  				Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + t.getRank().getColor() + target.getName() + " &6was kicked by " + profile.getRank().getColor() + player.getName()));
  	  	  			}
  	  	  			
  	  				if (target.isOnline()) {
  	  					target.getPlayer().kickPlayer(msg.kickMessage(r));
  	  				} else {
  	  					Utils.kickPlayer(target.getName(), msg.kickMessage(r));
  	  				}
  	  			});
			});
		}
		return true;
	}
}
