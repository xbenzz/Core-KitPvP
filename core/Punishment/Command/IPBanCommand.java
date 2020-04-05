package me.core.Punishment.Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class IPBanCommand implements CommandExecutor {
	
	  ColorText msg = new ColorText();
	  Date now = new Date();
	  SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	  String date = format.format(now);

	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		  if (!(sender instanceof Player)) {
			  if (args.length < 2) {
				  sender.sendMessage(ColorText.translate("Usage: /ipban <IP> <reason>"));
		          return false;
		      }

		      String ip = args[0];
		      
			  String banReason = "";
			  for (int i = 1; i < args.length; i++) {
				  banReason = banReason + args[i] + " ";
			  }
			  final String r = banReason;
			  
			  if (ip.contains(".")) {
				  Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
					  Punishment p = new Punishment(ip, Type.IP);
					  if (p.isActive() && p.getID() != 0) {
						  sender.sendMessage(ColorText.translate("&cPunish> &e" + ip + " &6is already banned!"));
						  return;
					  }
					  ArrayList<UUID> staffers = Utils.getAllAdmins();
			      
					  Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						  Punishment ban = new Punishment(null, r, Type.IP, Type.IPBAN, date, ip, true);
						  ban.execute();
			          
						  for (UUID uid : staffers) {
							  OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							  Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> &4CONSOLE &6has issued an IPBan on &e" + ip));
						  }
						  
						  for (Player po13 : Bukkit.getOnlinePlayers()) {
							  if (po13.getAddress().getAddress().getHostAddress().equals(ip)) {
								  po13.kickPlayer(msg.ipbanMessage(r));
							  }	
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
	      
	      if (args.length < 2) {
	          player.sendMessage(ColorText.translate("&cUsage: /ipban <IP> <reason>"));
	          return false;
	      }

	      String ip = args[0];
	      
		  String banReason = "";
		  for (int i = 1; i < args.length; i++) {
			  banReason = banReason + args[i] + " ";
		  }
		  final String r = banReason;
		  
		  if (ip.contains(".")) {
			  Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				  Punishment p = new Punishment(ip, Type.IP);
				  if (p.isActive() && p.getID() != 0) {
					  sender.sendMessage(ColorText.translate("&cPunish> &e" + ip + " &6is already banned!"));
					  return;
				  }
				  ArrayList<UUID> staffers = Utils.getAllAdmins();
		      
				  Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					  Punishment ban = new Punishment(player.getUniqueId().toString(), r, Type.IP, Type.IPBAN, date, ip, true);
					  ban.execute();
					  
					  for (UUID uid : staffers) {
						  OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
						  Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + player.getName() + " &6has issued an IPBan on &e" + ip));
					  }
					  
					  for (Player po13 : Bukkit.getOnlinePlayers()) {
						  if (po13.getAddress().getAddress().getHostAddress().equals(ip)) {
							  po13.kickPlayer(msg.ipbanMessage(r));
						  }	
					  }	
				  });
			  });	
		  } else {
			  player.sendMessage(ColorText.translate("&cError: Invalid IP"));
		  }
	      return false;
	}
}
