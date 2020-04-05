package me.core.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;
import me.core.Utilities.Cooldowns;
import me.core.Utilities.Utils;

public class RequestCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /request <text>"));
		} else {
			if (Cooldowns.isOnCooldown("request_delay", player)) {
				player.sendMessage(ColorText.translate("&cRequest> &6Please, wait &e" + Utils.formatLong(Cooldowns.getCooldownLong("request_delay", player)) + " &6seconds before using request."));
				return true;
			}
			
			String message = "";
			for (int m = 0; m < args.length; m++) {
				message = message + args[m] + " ";
			}
			final String mg = message;
			
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				for (UUID online : Utils.getAllStaff()) {
					OfflinePlayer p = Bukkit.getOfflinePlayer(online);
					Utils.globalMessage(p.getName(), ColorText.translate("&7[&c&lHELP&7] &7(&3" + Bukkit.getServerName() + "&7) " + profile.getRank().getColor() + player.getName() + "&8: &e" + mg));
				}
			});
			
			player.sendMessage(ColorText.translate("&cRequest> &6Your message has been sent to online staff."));
			Cooldowns.addCooldown("request_delay", player, 60);
		}
		return true;
	}	
}
