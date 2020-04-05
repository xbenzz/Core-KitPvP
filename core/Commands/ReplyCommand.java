package me.core.Commands;

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

public class ReplyCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
	    if (args.length == 0) {
			player.sendMessage(ColorText.translate("&cUsage: /reply <message>"));
	        return true;
	    }
	    if (!MessageCommand.reply.containsKey(player.getUniqueId())) {
	    	player.sendMessage(ColorText.translate("&cServer> &6You have not messaged anyone recently."));
	    	return true;
	    }
	    
	    Player target = Bukkit.getPlayer((UUID)MessageCommand.reply.get(player.getUniqueId()));
	    if (!Utils.isOnline(player, target)) {
	    	player.sendMessage(ColorText.translate("&cServer> &6That player is no longer online."));
	    	return true;
	    }
	    Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
	    if (player.getName().equalsIgnoreCase(target.getName())) {
	    	player.sendMessage(ColorText.translate("&cMessage> &6You cannot message yourself."));
	    }  else if (!t.hasMessages()) {
	    	player.sendMessage(ColorText.translate("&cMessage> " + t.getRank().getColor() + target.getName() + " &6has messages turned off."));
		} else if (VanishManager.isVanished(target.getPlayer())) {
	    	player.sendMessage(ColorText.translate("&cServer> &6That player is no longer online."));
		} else {
			MessageCommand.reply.remove(target.getUniqueId());
			MessageCommand.reply.remove(player.getUniqueId());
			MessageCommand.reply.put(target.getUniqueId(), player.getUniqueId());
			MessageCommand.reply.put(player.getUniqueId(), target.getUniqueId());
		    String reason = "";
		    for (int i = 0; i < args.length; i++) {
		    	reason = reason + args[i] + " ";
		    }
			player.sendMessage(ColorText.translate("&c&lTO: &d(" + t.getRank().getColor() + target.getName() + "&d)&8: &e" + reason));
			target.sendMessage(ColorText.translate("&c&lFROM: &d(" + profile.getRank().getColor() + player.getName() + "&d)&8: &e" + reason));
		}
		return false;
	}

}
