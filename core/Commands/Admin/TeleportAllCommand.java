package me.core.Commands.Admin;

import org.bukkit.Bukkit;
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

public class TeleportAllCommand implements CommandExecutor {
 
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
		if (Bukkit.getServer().getOnlinePlayers().size() <= 1) {
			player.sendMessage(ColorText.translate("&cServer> &6There aren't enough players online."));
		} else {
			TeleportManager.teleportAllPlayers(player);
		}
		return true;
	}
}
