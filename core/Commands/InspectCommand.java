package me.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.InspectManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class InspectCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!user.getRank().isAboveOrEqual(Rank.ELITE)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return true;
		}
    
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName>"));
		} else if (user.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(player, target)) {
				Utils.PLAYER_NOT_FOUND(player, args[0]);
				return true;
			}
			InspectManager.openInspectionMenu(player, target);
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(player, target)) {
				Utils.PLAYER_NOT_FOUND(player, args[0]);
				return true;
			}
			InspectManager.openRegularMenu(player, target);
		}
		return true;
	}
}
