package me.core.Commands.Staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Report.Report;
import me.core.Report.ReportLog;
import me.core.Report.ReportsHandler;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ReportsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
	    Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    ReportsHandler r = new ReportsHandler();
	    if (!user.getRank().isAboveOrEqual(Rank.HELPER)) {
	        sender.sendMessage(Utils.NO_PERMISSION);
	        return true;
	    }
	    if (args.length == 0) {
	    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	    		List<Report> reports = new Report().getReports();
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					player.openInventory(r.openReports(player, reports));
				});
	    	});
	    } else if (args[0].equalsIgnoreCase("find")) {
	    	if (args.length < 2) {
		        sender.sendMessage(ColorText.translate("&cUsage: /reports find <player>"));
		        return true;
	    	}
	    	OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
    		Profile tProfile = new Profile(target.getUniqueId(), false);
	    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	    		if (!tProfile.isCreated()) {
	    			Utils.PLAYER_NOT_FOUND(player, args[1]);
	    			return;
	    		}
	    		
	    		ReportLog log = new ReportLog(target.getUniqueId());
	    		ArrayList<ReportLog> logs = log.getReportLogs();
	    		
		    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
		    		if (logs.isEmpty()) {
		    			sender.sendMessage(ColorText.translate("&cReports> &e" + args[1] + " &6has no report logs!"));
		    		} else {
		    			sender.sendMessage(ColorText.translate("&cReports> &e" + args[1] + " &6Report Logs:"));
		    			for (ReportLog e : logs) {
		    				TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8* &c" + Bukkit.getOfflinePlayer(e.getPlayer()).getName() + " &6reported by &e" + Bukkit.getOfflinePlayer(e.getReporter()).getName() + " &8- "));
			    		
		    				TextComponent click = new TextComponent("[Click for Logs]");
		    				click.setColor(ChatColor.GOLD);
		    				String link = "http://vendettapvp.org/reports/" + e.getLog() + ".txt";
		    				click.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, link ) );
		    				click.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorText.translate("&7Click Here" )).create()));
		    				msg.addExtra(click);
		    				player.spigot().sendMessage(msg);
		    				player.sendMessage(" ");
		    			}
		    		}
		    	});
	    	});
	  }
	  return true;
	}
}
