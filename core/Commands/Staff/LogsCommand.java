package me.core.Commands.Staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.LogManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class LogsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
	      sender.sendMessage("Only players can issue this command");
	      return true;
	    }
	    
	    Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    
	    if (!user.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
	      player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	      return true;
	    }
	      
	    if (args.length == 0) {
	      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /playerlogs <user>"));
	      return false;
	    }

	     OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);      
	     LogManager.getLink(target.getUniqueId(), player);
		 return false;
	  }

}
