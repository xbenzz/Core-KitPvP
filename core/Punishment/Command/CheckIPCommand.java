package me.core.Punishment.Command;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Punishment.HistoryPage;
import me.core.Punishment.Punishment;
import me.core.Punishment.Type;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;

public class CheckIPCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		}
		
		Player player = (Player) sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
	        sender.sendMessage(Utils.NO_PERMISSION);
	        return false; 
	     }     
	     
	    if (args.length == 0) {
	    	player.sendMessage(ChatColor.RED + "Usage: /checkip <ip>");
	    	return false;
	    }
	      
	    String ip = args[0];
	    if (ip.contains(".")) {
	    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	    		HistoryPage page = new HistoryPage();
	    		List<Punishment> list = new Punishment(ip, Type.IP).getIPHistory();
	    		Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
	    			player.openInventory(page.openIPHistory(player, ip, list));
	    		});
	    	});
	    } else {
			player.sendMessage(ColorText.translate("&cError: Invalid IP"));
		}
	    return false;
	}

}