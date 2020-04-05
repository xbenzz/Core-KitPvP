package me.core.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;

public class TogglePrivateMessagesCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		boolean PM = profile.hasMessages();
		
		if (PM) {
			player.sendMessage(ColorText.translate("&cSettings> &6Private Messages are now &eDisabled"));
			profile.setMessages(false);
		} else {
			player.sendMessage(ColorText.translate("&cSettings> &6Private Messages are now &eEnabled"));
			profile.setMessages(true);
		}
		return false;
	}
}
