package me.core.Commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.VanishManager;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class MessageCommand implements CommandExecutor {
	
	public static HashMap<UUID, UUID> reply = new HashMap<UUID, UUID>();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
	    if (args.length < 2) {
			player.sendMessage(ColorText.translate("&cUsage: /msg <player> <message>"));
	        return true;
	    }
	    
	    Player target = Bukkit.getPlayer(args[0]);
	    if (!Utils.isOnline(player, target)) {
	        Utils.PLAYER_NOT_FOUND(player, args[0]);
	    	return true;
	    }

	    Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
	    if (player.getName().equalsIgnoreCase(target.getName())) {
	    	player.sendMessage(ColorText.translate("&cMessage> &6You cannot message yourself."));
	    } else if (!t.hasMessages()) {
			player.sendMessage(ColorText.translate("&cMessage> " + t.getRank().getColor() + target.getName() + " &6has messages turned off."));
		} else if (VanishManager.isVanished(target.getPlayer())) {
	        Utils.PLAYER_NOT_FOUND(player, args[0]);
		} else {
			reply.remove(target.getUniqueId());
			reply.remove(player.getUniqueId());
			reply.put(target.getUniqueId(), player.getUniqueId());
			reply.put(player.getUniqueId(), target.getUniqueId());
		    String reason = "";
		    for (int i = 1; i < args.length; i++) {
		    	reason = reason + args[i] + " ";
		    }
			player.sendMessage(ColorText.translate("&c&lTO: &d(" + t.getRank().getColor() + target.getName() + "&d)&8: &e" + reason));
			target.sendMessage(ColorText.translate("&c&lFROM: &d(" + profile.getRank().getColor() + player.getName() + "&d)&8: &e" + reason));
		}
		return false;
	}
	
}
