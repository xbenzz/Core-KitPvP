package me.core.Commands.Admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.AltManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;

public class IPCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		   
		}
		Player player = (Player) sender;
		Profile staff = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!staff.getRank().isAboveOrEqual(Rank.ADMIN)) {
	      sender.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	      return false; 
	    }    
	    if (args.length == 0) {
	    	player.sendMessage(ChatColor.RED + "Usage: /iplookup <ip/player>");
	    	return false;
	    }
	    String ip = args[0];
	    if (ip.contains(".")) {
	    	AltManager.getIPHistory(ip, player);
	    } else {
	    	AltManager.getAltHistory(ip, player);
	    }
		return false;
	}
}
