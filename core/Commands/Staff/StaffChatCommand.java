package me.core.Commands.Staff;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class StaffChatCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
	    Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!profile.getRank().isAboveOrEqual(Rank.HELPER)) {
	        sender.sendMessage(Utils.NO_PERMISSION);
	        return true;
	    }
	    if (args.length == 0) {
		   sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /sc <message>"));
	      return true;
	    }
	      
	    String message = "";
	    for (int i = 0; i < args.length; i++) {
	       message = message + args[i] + " ";
	    }
	    final String msg = message;
	     
	    Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	    	ArrayList<UUID> staffers = Utils.getAllStaff();
	    	
		    Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
		    	for (UUID po : staffers) {
		    		OfflinePlayer off = Bukkit.getOfflinePlayer(po);
		    		Utils.globalMessage(off.getName(), ColorText.translate("&7[&c&lSTAFF&7] &7(&a" + Bukkit.getServerName() + "&7) " + profile.getRank().getColor() + player.getName() + ChatColor.GRAY + ": " + ChatColor.YELLOW + "" + msg));
		    	}
		    });
		    
	    });
	 
		return false;
	}

}
