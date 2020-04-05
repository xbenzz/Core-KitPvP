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

public class KillCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to excute this command");
			return false; 
		}
    
	    Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
	    	player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	    	return true;
	    }
	    if (args.length < 1) {
	    	player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName>"));
	    } else {
	    	Player target = Bukkit.getPlayer(args[0]);
	    	if (!Utils.isOnline(sender, target)) {
	    		Utils.PLAYER_NOT_FOUND(sender, args[0]);
	    		return true;
	    	}
	    	
			Profile t = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    	target.setHealth(0);
	   	    player.sendMessage(ColorText.translate("&cServer> &6You killed " + t.getRank().getColor() + target.getName()));
	    }
	    return true;
	}
}
