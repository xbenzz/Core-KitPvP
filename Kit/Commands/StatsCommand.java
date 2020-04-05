package me.Kit.Commands;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.Kit.Manager.StatsManager;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Utilities.Utils;

public class StatsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		}
		
		Player player = (Player) sender;
		
	    if (args.length == 0) {
	    	player.openInventory(StatsManager.statsMenuSelf(player));
	        return true;
	    } else {
	    	OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		    Optional<Profile> tProfile = Kit.getProfileManager().getUser(target.getUniqueId());
		    Optional<KitPlayer> kit = Kit.getKitManager().getUser(target.getUniqueId());
		    
		    if (tProfile.isPresent() && kit.isPresent()) {
	    		player.openInventory(StatsManager.statsMenuOther(player, target, tProfile.get(), kit.get()));
		    } else {
		    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    		KitPlayer t = new KitPlayer(target.getUniqueId(), true);
		    		Profile p = new Profile(target.getUniqueId(), true);
		    		if (!t.isCreated()) {
		    			Utils.PLAYER_NOT_FOUND(player, args[0]);
		    			return;
		    		}
			    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
			    		player.openInventory(StatsManager.statsMenuOther(player, target, p, t));
			    	});
		    	});
		    }
	    }
		return false;
	}

}
