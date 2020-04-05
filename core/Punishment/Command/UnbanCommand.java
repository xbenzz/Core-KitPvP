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
import me.core.AntiCheat.AC;
import me.core.Profile.Profile;
import me.core.Punishment.Punishment;
import me.core.Punishment.Type;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class UnbanCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 1) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName>"));
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				
				Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
					Profile profile = new Profile(target.getUniqueId(), true);
					AC a = new AC(target.getUniqueId());
					Punishment pun = new Punishment(target.getUniqueId(), Type.BAN);
					ArrayList<UUID> staffers = Utils.getAllStaff();
					
					Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
						if (pun.isRemoved() || !pun.isActive()) {
							sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is not banned."));
							return;
						}
						pun.playerRemove("CONSOLE");
						
						if (a.isActive()) {
							a.remove();
						}
						for (UUID uid : staffers) {
							OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
							Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + profile.getRank().getColor() + target.getName() + " &6was unbanned by &4CONSOLE"));
						}
					});
				});
			}
			return true;
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
		
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName>"));
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				Profile t = new Profile(target.getUniqueId(), true);
				AC a = new AC(target.getUniqueId());
				Punishment pun = new Punishment(target.getUniqueId(), Type.BAN);
				ArrayList<UUID> staffers = Utils.getAllStaff();
				
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					if (pun.isRemoved() || !pun.isActive()) {
						sender.sendMessage(ColorText.translate("&cPunish> &e" + target.getName() + " &6is not banned."));
						return;
					}
					pun.playerRemove(player.getUniqueId().toString());
				
					if (a.isActive()) {
						a.remove();
					}
				
					for (UUID uid : staffers) {
						OfflinePlayer off = Bukkit.getOfflinePlayer(uid);
						Utils.globalMessage(off.getName(), ColorText.translate("&cPunish> " + t.getRank().getColor() + target.getName() + " &6was unbanned by " + profile.getRank().getColor() + player.getName()));
					}
				});
			});
		}
		return true;
	}
}
