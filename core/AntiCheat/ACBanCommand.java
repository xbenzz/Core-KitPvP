package me.core.AntiCheat;

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
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class ACBanCommand implements CommandExecutor {
	
	ColorText msg = new ColorText();
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	String date = format.format(now);
	  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 3) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <hack> <reason>"));
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				Punishment p = new Punishment(target.getUniqueId());
				String hack = args[1];
				
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {      	  
					Profile profile = new Profile(target.getUniqueId(), true);
					AC a2 = new AC(target.getUniqueId());
					if (a2.isActive()) {
						return;
					}
					if (p.isPunished(Type.BAN)) {
						return;
					}
					
			    	ArrayList<UUID> staffers = Utils.getAllStaff();
	    	        	
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						AC a = new AC(target.getUniqueId(), hack, date);
						a.execute();
		    	  
						String reason = ""; 	
						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						final String r = reason;
		    	        	
						Punishment punish = new Punishment(target.getUniqueId(), null, reason, -1L, Type.BAN, Type.PERM_BAN, date, null, false);
						punish.execute();
						
						for (UUID uid : staffers) {
							OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + target.getName() + " &6was permanently banned by &4CONSOLE"));
						}
						
						if (target.isOnline()) {
							target.getPlayer().kickPlayer(msg.banMessage(r, "Permanent"));
						} else {
							Utils.kickPlayer(target.getName(), msg.banMessage(r, "Permanent"));
						}
					});
				});
			}
			return true;
		} else {
			sender.sendMessage(Utils.NO_PERMISSION);
	    }
		return true;
	  }
}
