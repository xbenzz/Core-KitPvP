package me.Kit.Commands;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.primitives.Ints;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class CoinsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 3) {
				sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <player> <set/add> <amount>"));
			} else {
			    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			    Optional<Profile> tProfile = Kit.getProfileManager().getUser(target.getUniqueId());
			    Optional<KitPlayer> kit = Kit.getKitManager().getUser(target.getUniqueId());
			    
			    if (tProfile.isPresent() && kit.isPresent()) {
		    		Integer level = Ints.tryParse(args[2]);
		    		if (level == null) {
		    			sender.sendMessage(ColorText.translate("&cStats> &e" + args[1] + " &6is not a valid number."));
		    			return false;
		    		}
		    		String type = args[1];
		    		if (type.equalsIgnoreCase("set")) {
		    			kit.get().setCoins(level);
		    			sender.sendMessage(ColorText.translate("&cStats> &6Set " + tProfile.get().getRank().getColor() + target.getName() + " &6coins to &2" + level));
		    		} else if (type.equalsIgnoreCase("add")) {
		    			kit.get().setCoins(kit.get().getCoins() + level);
		    			sender.sendMessage(ColorText.translate("&cStats> &6Added &2" + level + " &6coins to " + tProfile.get().getRank().getColor() + target.getName()));
		    		} else {
		    			sender.sendMessage(ColorText.translate("&cStats> &6Invalid Type: Set | Add"));
		    		}
			    } else {
			    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			    		KitPlayer k = new KitPlayer(target.getUniqueId(), true);
			    		if (!k.isCreated()) {
			    			Utils.PLAYER_NOT_FOUND(sender, args[0]);
			    			return;
			    		}
			    		
				    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
				    		Integer level = Ints.tryParse(args[2]);
				    		if (level == null) {
				    			sender.sendMessage(ColorText.translate("&cStats> &e" + args[1] + " &6is not a valid number."));
				    			return;
				    		}
				    		String type = args[1];
				    		if (type.equalsIgnoreCase("set")) {
				    			kit.get().setSQLCoins(level);
				    			sender.sendMessage(ColorText.translate("&cStats> &6Set &e" + target.getName() + " &6coins to &2" + level));
				    		} else if (type.equalsIgnoreCase("add")) {
				    			kit.get().setSQLCoins(kit.get().getCoins() + level);
				    			sender.sendMessage(ColorText.translate("&cStats> &6Added &2" + level + " &6coins to &e" + target.getName()));
				    		} else {
				    			sender.sendMessage(ColorText.translate("&cStats> &6Invalid Type: Set | Add"));
				    		}
				    	});
			    	});
			    	return true;
			    }
			}
		}
		    
		Player player = (Player)sender; 
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
		    player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
		    return true;
		}

	    if (args.length < 3) {
	    	player.sendMessage(ColorText.translate("&cUsage: /" + label + " <player> <set/add> <amount>"));
	    } else {
		    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		    Optional<Profile> tProfile = Kit.getProfileManager().getUser(target.getUniqueId());
		    Optional<KitPlayer> kit = Kit.getKitManager().getUser(target.getUniqueId());
		    
		    if (tProfile.isPresent() && kit.isPresent()) {
	    		Integer level = Ints.tryParse(args[2]);
	    		if (level == null) {
	    			sender.sendMessage(ColorText.translate("&cStats> &e" + args[1] + " &6is not a valid number."));
	    			return false;
	    		}
	    		String type = args[1];
	    		if (type.equalsIgnoreCase("set")) {
	    			kit.get().setCoins(level);
	    			sender.sendMessage(ColorText.translate("&cStats> &6Set " + tProfile.get().getRank().getColor() + target.getName() + " &6coins to &2" + level));
	    		} else if (type.equalsIgnoreCase("add")) {
	    			kit.get().setCoins(kit.get().getCoins() + level);
	    			sender.sendMessage(ColorText.translate("&cStats> &6Added &2" + level + " &6coins to " + tProfile.get().getRank().getColor() + target.getName()));
	    		} else {
	    			sender.sendMessage(ColorText.translate("&cStats> &6Invalid Type: Set | Add"));
	    		}
		    } else {
		    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    		KitPlayer k = new KitPlayer(target.getUniqueId(), true);
		    		if (!k.isCreated()) {
		    			Utils.PLAYER_NOT_FOUND(sender, args[0]);
		    			return;
		    		}
		    		
			    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
			    		Integer level = Ints.tryParse(args[2]);
			    		if (level == null) {
			    			sender.sendMessage(ColorText.translate("&cStats> &e" + args[1] + " &6is not a valid number."));
			    			return;
			    		}
			    		String type = args[1];
			    		if (type.equalsIgnoreCase("set")) {
			    			kit.get().setSQLCoins(level);
			    			sender.sendMessage(ColorText.translate("&cStats> &6Set &e" + target.getName() + " &6coins to &2" + level));
			    		} else if (type.equalsIgnoreCase("add")) {
			    			kit.get().setSQLCoins(kit.get().getCoins() + level);
			    			sender.sendMessage(ColorText.translate("&cStats> &6Added &2" + level + " &6coins to &e" + target.getName()));
			    		} else {
			    			sender.sendMessage(ColorText.translate("&cStats> &6Invalid Type: Set | Add"));
			    		}
			    	});
		    	});
		    	return true;
		    }
	    }
	    return true;
	}
}
