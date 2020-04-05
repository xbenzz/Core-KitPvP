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

public class BanCommand implements CommandExecutor {
	
	ColorText msg = new ColorText();
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	String date = format.format(now);
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 2) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <reason>"));
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
					Profile profile = target.isOnline() ? Kit.getProfileManager().getUser(target.getUniqueId()).get() : new Profile(target.getUniqueId(), true);
					Punishment p = new Punishment(target.getUniqueId());
					if (p.isPunished(Type.BAN)) {
						sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is already banned."));
						return;
					}
					
			    	ArrayList<UUID> staffers = Utils.getAllStaff();
				
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						if ((!target.hasPlayedBefore()) && (!target.isOnline())) {
							sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6has never played before, but will be banned."));
						}
						
						String reason = "";
						for (int i = 1; i < args.length; i++) {
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
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
		
		if (args.length < 2) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <reason>"));
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				Profile t = target.isOnline() ? Kit.getProfileManager().getUser(target.getUniqueId()).get() : new Profile(target.getUniqueId(), true);
				Punishment p = new Punishment(target.getUniqueId());
			
				if (p.isPunished(Type.BAN)) {
					sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is already banned."));
					return;
				}
			    
		    	ArrayList<UUID> staffers = Utils.getAllStaff();
			    
			    Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					if ((!target.hasPlayedBefore()) && (!target.isOnline())) {
						player.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6has never played before, but will be banned."));
					}
					
					
					if ((t.getRank().isAboveOrEqual(Rank.HELPER) && (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)))) {
						sender.sendMessage(ColorText.translate("&cPunish> &6You may not ban other staff members!"));
						return;
					}
					
					String reason = "";
					for (int i = 1; i < args.length; i++) {
						reason = reason + args[i] + " ";
					}
				    final String r = reason;
				    
				    Punishment punish = new Punishment(target.getUniqueId(), player.getUniqueId().toString(), reason, -1L, Type.BAN, Type.PERM_BAN, date, null, false);
				    punish.execute();
				    
				    for (UUID uid : staffers) {
				    	OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
				    	Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + t.getRank().getColor() + target.getName() + " &6was permanently banned by " + profile.getRank().getColor() + player.getName()));
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
	}	
}
