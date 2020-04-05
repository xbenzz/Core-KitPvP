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
import me.core.Punishment.Punishment;
import me.core.Punishment.Type;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;

public class UnIPBanCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
		    	sender.sendMessage(ChatColor.RED + "Usage: /unipban <IP>");
		        return false;
		    }
		      
			String ip = args[0];
			if (ip.contains(".")) {
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
					Punishment p = new Punishment(ip, Type.IP);
					ArrayList<UUID> staffers = Utils.getAllAdmins();
					
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						if (p.isActive() && p.getID() != 0) {
							p.playerRemoveIPBan("CONSOLE");
							for (UUID uid : staffers) {
								OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
								Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> &4CONSOLE &6has removed an IPBan on &e" + ip));
					    	}
						} else {
							sender.sendMessage(ColorText.translate("&cPunish> &6IP is not banned!"));
						}
					});
				});	
			} else {
				sender.sendMessage(ColorText.translate("Error: Invalid IP"));
			}
		    return true;
		}
		
		Player player = (Player) sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return false; 
		}   
	      
	    if (args.length == 0) {
	    	player.sendMessage(ChatColor.RED + "Usage: /unipban <IP>");
	        return false;
	    }
	      
	    String ip = args[0];
		if (ip.contains(".")) {
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				Punishment p = new Punishment(ip, Type.IP);
				ArrayList<UUID> staffers = Utils.getAllAdmins();
				
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {   
					if (p.isActive() && p.getID() != 0) {
						p.playerRemoveIPBan(player.getUniqueId().toString());
						for (UUID uid : staffers) {
							OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + player.getName() + " &6has removed an IPBan on &e" + ip));
						}
					} else {
						player.sendMessage(ColorText.translate("&cPunish> &6IP is not banned!"));
					}
				});
			});	
		} else {
			player.sendMessage(ColorText.translate("&cError: Invalid IP"));
		}
	     return false;
	}

}