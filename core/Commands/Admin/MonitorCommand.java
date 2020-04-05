package me.core.Commands.Admin;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class MonitorCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
		    return true;
		}
		    
	   Player player = (Player)sender;
	   Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	   if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
		   player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
		   return true;
	   }	
		    
		    OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		    Runtime runtime = Runtime.getRuntime();
		    double mb = 1048576.0D; 
		    DecimalFormat format = new DecimalFormat("###.##");
		    
		    player.sendMessage(ColorText.translate("&7&m---------------------------------------"));
		    player.sendMessage(ColorText.translate("&6&lTime Stats"));
		    player.sendMessage(ColorText.translate("  &eCurrent Server Time: &7" + new Date().toString()));
		    player.sendMessage("");
		    player.sendMessage(ColorText.translate("&6&lMemory Stats"));
		    player.sendMessage(ColorText.translate("  &eMax Memory: &7" + runtime.maxMemory() / mb + " MB"));
		    player.sendMessage(ColorText.translate("  &eFree Memory: &7" + format.format(runtime.freeMemory() / mb) + " MB &f(" + format.format((runtime.freeMemory() / (double) runtime.maxMemory()) * 100.0) + "%)"));
		    player.sendMessage(ColorText.translate("  &eUsed Memory: &7" + format.format((runtime.totalMemory() - runtime.freeMemory()) / mb) + " MB"));
		    player.sendMessage("");
		    player.sendMessage(ColorText.translate("&6&lDisk Stats"));
		    player.sendMessage(ColorText.translate("  &eSystem Load: &7" + Double.parseDouble(format.format(osBean.getSystemLoadAverage() * 100))));
		    player.sendMessage(ColorText.translate("  &eActive Threads: &7" + Thread.activeCount()));
		    player.sendMessage(ColorText.translate("&7&m---------------------------------------"));
		    return true;
	  }

}
