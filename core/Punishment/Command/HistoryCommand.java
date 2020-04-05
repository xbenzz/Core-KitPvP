package me.core.Punishment.Command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Punishment.HistoryPage;
import me.core.Punishment.Punishment;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class HistoryCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  { 
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
		if (!profile.getRank().isAboveOrEqual(Rank.HELPER)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
    
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName>"));
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			HistoryPage page = new HistoryPage();
    	
			Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
				List<Punishment> list = new Punishment(target.getUniqueId()).getHistory();
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					player.openInventory(page.openHistory(player, target.getUniqueId().toString(), list));
				});
			});
		}
		return true;
	}
}
