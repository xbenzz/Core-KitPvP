package me.core.Commands.Admin;

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

public class BroadcastCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
			
		Player p = (Player) sender;
		Profile profile = Kit.getProfileManager().getUser(p.getUniqueId()).get();
		
	    if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
	    	sender.sendMessage(Utils.NO_PERMISSION);
	    	return false;
	    }
	    if (args.length < 1) {
	    	sender.sendMessage(ColorText.translate("&cUsage: /broadcast <text>"));
	    	return false;
	    }
	    
	    String message = "";
	    for (int i = 0; i < args.length; i++) {
	    	message = message + args[i] + " ";
	    }
	    
	    for (Player pl : Bukkit.getOnlinePlayers()) {
	    	pl.sendMessage(ColorText.translate("&cBroadcast> &e" + message));
	    }
	    return false;
	}
}
