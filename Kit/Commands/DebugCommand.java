package me.Kit.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Rank.RankListener;
import me.core.Utilities.Utils;

public class DebugCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		}
		
		Player player = (Player) sender;
		Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!p.getRank().isAboveOrEqual(Rank.OWNER) ) {
			player.sendMessage(Utils.NO_PERMISSION);
			return false;
		}
	    
	    player.sendMessage(" ");
	    player.sendMessage("Size of Perms Cache: " + RankListener.perms.size());
	    for (UUID name : RankListener.perms.keySet()) {
            String key = Bukkit.getOfflinePlayer(name).getName();
            player.sendMessage(key);  
	    } 	
	    player.sendMessage(" ");
	    player.sendMessage("Size of User Cache: " + Kit.getProfileManager().getUsers().size());
	    for (Profile e : Kit.getProfileManager().getUsers()) {
	    	player.sendMessage(e.getUsername() + " " + e.getRank());
	    }
	    player.sendMessage(" ");
	    player.sendMessage("Size of Kit Cache: " + Kit.getKitManager().getUsers().size());
	    for (KitPlayer e : Kit.getKitManager().getUsers()) {
	    	player.sendMessage(e.getUUID() + " " + e.getLevel());
	    }
	    player.sendMessage(" ");
		return false;
	}

}
