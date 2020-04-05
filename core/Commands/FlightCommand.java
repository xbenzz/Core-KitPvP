package me.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class FlightCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
    
		if (!profile.getRank().isAboveOrEqual(Rank.VIP)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return false;
		}
    
		if (args.length < 1) {
			if (player.getWorld().getName().equalsIgnoreCase("KitPvP") && !profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
				player.sendMessage(ColorText.translate("&cPerks> &6This command is not avaliable in PvP."));
				return false;
			}
			boolean newFlight = !player.getAllowFlight();
			player.setAllowFlight(newFlight);
			if (newFlight)
				player.setFlying(true);
			String set = newFlight ? "Enabled" : "Disabled";
			player.sendMessage(ColorText.translate("&cPerks> &6Flight Mode is now &e" + set));
		} else {
			if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
				player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(player, target)) {
				Utils.PLAYER_NOT_FOUND(player, args[0]);
				return false;
			}
 
			Profile tp = Kit.getProfileManager().getUser(target.getUniqueId()).get();   
			boolean newFlight = !target.getAllowFlight();
			target.setAllowFlight(newFlight);
			if (newFlight) {
				target.setFlying(true);
			}
			String set = newFlight ? "Enabled" : "Disabled";
			player.sendMessage(ColorText.translate("&cPerks> &6Flight Mode of " + tp.getRank().getColor() + target.getName() + " &6is now &e" + set));
			target.sendMessage(ColorText.translate("&cPerks> &6Flight Mode is now &e" + set));
		}
   		return true;
	}
}
