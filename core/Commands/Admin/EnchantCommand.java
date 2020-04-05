package me.core.Commands.Admin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.primitives.Ints;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class EnchantCommand implements CommandExecutor {
	
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

	    if (args.length < 2) {
	    	player.sendMessage(ColorText.translate("&cUsage: /" + label + " <enchantment> <level>"));
	    } else {
	    	Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
	    	if (enchantment == null) {
	    		player.sendMessage(ColorText.translate("&cEnchantment> &6Enchantment &e" + args[0] + " &6not found."));
	    		return true;
	    	}	
	    	ItemStack stack = player.getItemInHand();
	    	if ((stack == null) || (stack.getType() == Material.AIR)) {
	    		player.sendMessage(ColorText.translate("&cEnchantment> &6You must be holding an item."));
	    		return true;
	    	}
	    	Integer level = Ints.tryParse(args[1]);
	    	if (level == null) {
	    		player.sendMessage(ColorText.translate("&cEnchantment> &e" + args[1] + " &6is not a valid number."));
	    		return true;
	    	}
	    	stack.addUnsafeEnchantment(enchantment, level.intValue());
	    	player.sendMessage(ColorText.translate("&cEnchantment> &6Applied &e" + enchantment.getName() + " &6at level &e" + level));
	    }
		return true;
	}

}
