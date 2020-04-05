package me.core.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.primitives.Ints;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class GiveCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
		   sender.sendMessage("You must be a player to excute this command");
		   return false;
		   
		}
		Player player = (Player) sender;
		Profile staff = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    if (!staff.getRank().isAboveOrEqual(Rank.ADMIN)) {
	      sender.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
	      return false; 
	    }    

	    if (args.length < 1) {
	    	player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <itemName> <amount>"));
	    } else if (args.length == 3) {
	    	Player target = Bukkit.getPlayer(args[0]);
	    	if (!Utils.isOnline(player, target)) {
	    		Utils.PLAYER_NOT_FOUND(player, args[0]);
	    		return true;
	    	}
	    	
	    	Profile t = Kit.getProfileManager().getUser(target.getUniqueId()).get();
	    	Material material = Material.getMaterial(args[1].toUpperCase());
	      	if (material == null) {
	      		player.sendMessage(ColorText.translate("&cServer> &6Item not found."));
	      		return true;
	      	}
	      	Integer amount = Ints.tryParse(args[2]);
	      	if (amount == null) {
	      		player.sendMessage(ColorText.translate("&cServer> &e" + args[0] + " &6is not a valid number."));
	      		return true;
	      	}
	      	if (amount.intValue() <= 0) {
	      		player.sendMessage(ColorText.translate("&cServer> &6The items amount must be positive"));
	      		return true;
	      	}
	      	target.getInventory().addItem(new ItemStack[] { new ItemStack(material, amount.intValue()) });
	      	player.sendMessage(ColorText.translate("&cServer> &6You gave " + t.getRank().getColor() + target.getName() + " &e" + amount + "&6x of &e" + material + '.'));
	    	} else {
	    		player.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <itemName> <amount>"));
	    	}
	 return true;
	}
}
