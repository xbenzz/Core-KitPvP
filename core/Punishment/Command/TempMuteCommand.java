package me.core.Punishment.Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DurationFormatUtils;
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
import me.core.Utilities.TimeUtils;
import me.core.Utilities.Utils;

public class TempMuteCommand implements CommandExecutor {
	
	ColorText msg = new ColorText();
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	String date = format.format(now);
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 3) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <time> <reason> <-s>"));
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
					Profile profile = target.isOnline() ? Kit.getProfileManager().getUser(target.getUniqueId()).get() : new Profile(target.getUniqueId(), true);
					Punishment p = new Punishment(target.getUniqueId());
					if (p.isPunished(Type.MUTE)) {
						sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is already muted."));
						return;
					}
					ArrayList<UUID> staffers = Utils.getAllStaff();
					
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						if ((!target.hasPlayedBefore()) && (!target.isOnline())) {
							sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6has never played before."));
							return;
						}
						Long time = Long.valueOf(TimeUtils.parse(args[1]));
						if (time == null) {
							sender.sendMessage(ColorText.translate("&cPunish> &e" + args[1] + " &6is not a valid duration."));
							return;
						}
						if (time.longValue() < 0L) {
							sender.sendMessage(ColorText.translate("&cPunish> &6Invalid duration."));
							return;
						}
						String format = DurationFormatUtils.formatDurationWords(time.longValue(), true, true);
						String reason = "";
						for (int i = 2; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						
						Punishment punish = new Punishment(target.getUniqueId(), null, reason, time, Type.MUTE, Type.CHAT, date, null, false);
						punish.execute();
						
						if (target.isOnline()) {
							target.getPlayer().sendMessage(msg.muteMessage(reason, format));
						}
						
						for (UUID uid : staffers) {
							OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + target.getName() + " &6was temporarily muted by &4CONSOLE"));
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
		
		if (args.length < 3) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <time> <reason>"));
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				Profile t = target.isOnline() ? Kit.getProfileManager().getUser(target.getUniqueId()).get() : new Profile(target.getUniqueId(), true);
				Punishment p = new Punishment(target.getUniqueId());
				if (p.isPunished(Type.MUTE)) {
					sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is already muted."));
					return;
				}
				ArrayList<UUID> staffers = Utils.getAllStaff();
				
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					if ((!target.hasPlayedBefore()) && (!target.isOnline())) {
						sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6has never played before."));
						return;
					}
					Long time = Long.valueOf(TimeUtils.parse(args[1]));
					if (time == null) {
						sender.sendMessage(ColorText.translate("&cPunish> &e" + args[1] + " &6is not a valid duration."));
						return;
					}
					if (time.longValue() < 0L) {
						sender.sendMessage(ColorText.translate("&cPunish> &6Invalid duration."));
						return;
					}
					String format = DurationFormatUtils.formatDurationWords(time.longValue(), true, true);
					String reason = "";
					for (int i = 2; i < args.length; i++) {
						reason = reason + args[i] + " ";
					}
					if ((t.getRank().isAboveOrEqual(Rank.HELPER) && (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)))) {
						sender.sendMessage(ColorText.translate("&cPunish> &6You may not mute other staff members!"));
						return;
					}
					
					Punishment punish = new Punishment(target.getUniqueId(), player.getUniqueId().toString(), reason, time, Type.MUTE, Type.CHAT, date, null, false);
					punish.execute();
					
					if (target.isOnline()) {
						target.getPlayer().sendMessage(msg.muteMessage(reason, format));
					}
					for (UUID uid : staffers) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
						Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + t.getRank().getColor() + target.getName() + " &6was temporarily muted by " + profile.getRank().getColor() + player.getName()));
					}
				});
			});
		}
		return true;
	}
}
