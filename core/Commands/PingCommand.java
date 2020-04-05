package me.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.core.Manager.VanishManager;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class PingCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cPing> &6Your ping is &e" + Utils.getPing(player) + "ms "));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(player, target) || VanishManager.isVanished(target)) {
				Utils.PLAYER_NOT_FOUND(player, args[0]);
				return true;
			}
			if (target.equals(player)) {
				player.sendMessage(ColorText.translate("&cPing> &6Your ping is &e" + Utils.getPing(player) + "ms "));
				return true;
			}
			player.sendMessage(ColorText.translate("&cPing> &e" + target.getName() + "'s &6Ping: &e" + Utils.getPing(target) + "ms "));
		}
		return true;
	}
}
