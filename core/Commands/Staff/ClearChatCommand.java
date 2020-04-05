package me.core.Commands.Staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class ClearChatCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!user.getRank().isAboveOrEqual(Rank.MODERATOR)) {
	    	player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	    	return true;
	    }
	    
        for (Player online : Bukkit.getOnlinePlayers()) {
        	Profile t = Kit.getProfileManager().getUser(online.getUniqueId()).get();
	        if (!t.getRank().isAboveOrEqual(Rank.HELPER)) {
	        	online.sendMessage(new String[120]);
	        }
	    }
	    Bukkit.broadcastMessage(ColorText.translate("&cChat> &6Chat has been cleared by " + user.getRank().getColor() + player.getName()));
		return true;
	}
}
  
  