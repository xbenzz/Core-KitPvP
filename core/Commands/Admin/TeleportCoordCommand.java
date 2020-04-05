package me.core.Commands.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.TeleportManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class TeleportCoordCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return true;
		}
    
		if (args.length < 3) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <x> <y> <z>"));
		} else if (args.length == 3) {
			try {
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				TeleportManager.teleportCoords(player, x, y, z);
			} catch (NumberFormatException ex) {
				player.sendMessage(ColorText.translate("&cTeleport> &6Invalid coords."));
			}
		} else {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <x> <y> <z>"));
		}
		return true;
	}
}