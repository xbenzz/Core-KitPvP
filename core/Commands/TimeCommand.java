package me.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.FormatNumber;
import me.core.Utilities.Utils;

public class TimeCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
		    return true;
		}
		    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
		if (args.length < 1) {
		    Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    	long time = profile.getTime();
		    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
		    		player.sendMessage(ColorText.translate("&cTime> &6Your playtime time is &e" + FormatNumber.MakeStr(time, 1)));
		    	});
		    });
		} else {
			if (!profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
				player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
		        return true;
		    }
		      
		    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
	    	Profile t = new Profile(target.getUniqueId(), false);
		    Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    	if (!t.isCreated()) {
		    		Utils.PLAYER_NOT_FOUND(player, args[0]);
		    		return;
		    	}
		    	long time = t.getTime();
		    	
		    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
		    		player.sendMessage(ColorText.translate("&cTime> " + t.getRank().getColor() + target.getName() + " &6has a playtime of &e" + FormatNumber.MakeStr(time, 1)));
		    	});
			});
		}
		return true;
	}
}
