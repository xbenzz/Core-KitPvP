package me.core.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class EnderchestCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		    sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
		    return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
		
	    if (args.length < 1) {
	    	if (kit.getLevel() < 10) {
	    		player.sendMessage(ColorText.translate("&cPerks> &6You need to be &eLevel 10 &6to unlock this."));
		        return false;
		    }
		    if (player.getWorld().getName().equalsIgnoreCase("KitPvP")) {
		        player.sendMessage(ColorText.translate("&cPerks> &6This command is not avaliable in PvP."));
		        return false;
		    }
		    player.openInventory(player.getEnderChest());
	    } else { 
	    	Player target = Bukkit.getPlayer(args[0]);
	    	if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
	    		player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	    		return false;
	    	}
	    	if (!Utils.isOnline(player, target)) {
	    		Utils.PLAYER_NOT_FOUND(player, args[0]);
	    		return false;
	    	}
		    Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
		    player.sendMessage(ColorText.translate("&cPerks> &6Opening enderchest of " + t.getRank().getColor() + target.getName() + "&6."));
		    player.openInventory(target.getEnderChest());
		    return true;
	    }
		return false;
	}
}
