package me.core.Rank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class RankCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 2) {
				sender.sendMessage(ColorText.translate("&cUsage: /rank <player> <rank>"));
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
	    		Profile targetprofile = new Profile(target.getUniqueId(), false);
	    		String rank = args[1];
		    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    		if (!targetprofile.isCreated()) {
		    			sender.sendMessage(ColorText.translate("&cError: That player does not exist"));
		    			return;
		    		}
		    			
			    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
			    		Rank playerRank = Rank.getRankOrDefault(rank);
			    		if (playerRank == null) {
			    			sender.sendMessage(ColorText.translate("&cInvalid Rank!"));
			    			return;
			    		}
			    		targetprofile.setSQLRank(playerRank);
		    			if (target.isOnline()) {
		    				target.getPlayer().kickPlayer(ColorText.translate("&cRank> &6Your rank was updated to " + playerRank.getColor() + playerRank.getName()));
		    			}
		    			sender.sendMessage(ColorText.translate("&cRank> &6Set &e" + args[0] + " &6rank to " + playerRank.getColor() + playerRank.getName()));
			    	});
		    	});
		    return true;
			}
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
			player.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage(ColorText.translate("&cUsage: /rank <player> <rank>"));
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
    		Profile targetprofile = new Profile(target.getUniqueId(), false);
    		String rank = args[1];
	    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	    		if (!targetprofile.isCreated()) {
	    			player.sendMessage(ColorText.translate("&cError: That player does not exist"));
	    			return;
	    		}
	    		
		    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
		    		Rank playerRank = Rank.getRankOrDefault(rank);
		    		if (playerRank == null || playerRank == Rank.ADMIN || playerRank == Rank.OWNER) {
		    			sender.sendMessage(ColorText.translate("&cInvalid Rank!"));
		    			return;
		    		}
		    		targetprofile.setSQLRank(playerRank);
		    		if (target.isOnline()) { 
		    			target.getPlayer().kickPlayer(ColorText.translate("&cRank> &6Your rank was updated to " + playerRank.getColor() + playerRank.getName()));
		    		}
		    		player.sendMessage(ColorText.translate("&cRank> &6Set &e" + args[0] + " &6rank to " + playerRank.getColor() + playerRank.getName()));
		    	});
	    	});
		}
		return true;
	}
  
}
