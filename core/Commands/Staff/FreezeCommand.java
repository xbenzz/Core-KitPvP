package me.core.Commands.Staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.FreezeManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class FreezeCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!user.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ColorText.translate("&cUsage: /freeze <playerName>"));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(sender, target)) {
				Utils.PLAYER_NOT_FOUND(sender, args[0]);
				return true;
			}
			
			Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
			if (target.getName().equalsIgnoreCase(player.getName())) {
				sender.sendMessage(ColorText.translate("&cFreeze> &6You cannot freeze to yourself."));
				return true;
			}
			if ((t.getRank().isAboveOrEqual(Rank.HELPER)) && (!user.getRank().isAboveOrEqual(Rank.SENIOR_MOD)))  {
				sender.sendMessage(ColorText.translate("&cFreeze> &6You cannot freeze staff members."));
				return true;
			}
			FreezeManager.setFreeze(target, !FreezeManager.isFrozen(target));
      
			for (Profile o : Kit.getProfileManager().getUsersAboveOrERank(Rank.MODERATOR)) {
				Player po = Bukkit.getPlayer(o.getUUID());
				po.sendMessage(ColorText.translate("&cFreeze> " + t.getRank().getColor() + target.getName() + " &6has been " + (FreezeManager.isFrozen(target) ? "&efrozen " + user.getRank().getColor() + player.getName() : "&eunfrozen " + "&6by " + user.getRank().getColor() + player.getName())));
			}
		}
		return true;
	}
}
