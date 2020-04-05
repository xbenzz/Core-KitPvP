package me.core.Commands.Staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Handler.ChatHandler;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class MuteChatCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!user.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return true;
		}
		ChatHandler.setChatToggled(!ChatHandler.isMuted());
		Bukkit.broadcastMessage(ColorText.translate("&cServer> &6Global Chat has now been " + (ChatHandler.isMuted() ? "&cDisabled&6." : "&aEnabled&6.")));
		return true;
	}
}
