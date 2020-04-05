package me.core.Commands.Admin;

import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class RepairCommand implements CommandExecutor {
	
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
    
		if (args.length < 1) {
			if ((player.getItemInHand() == null) || (player.getItemInHand().getType() == Material.AIR)) {
				player.sendMessage(ColorText.translate("&cRepair> &6You must have something in your hand."));
				return true;
			}
			player.getItemInHand().setDurability((short)0);
			player.sendMessage(ColorText.translate("&cRepair> &6Repaired held item."));
		} else if (args[0].equalsIgnoreCase("hand")) {
			if (args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				if (!Utils.isOnline(player, target)) {
					Utils.PLAYER_NOT_FOUND(player, args[1]);
					return true;
				}
				
				if ((target.getItemInHand() == null) || (target.getItemInHand().getType() == Material.AIR)) {
					player.sendMessage(ColorText.translate("&cRepair> &e" + target.getName() + " &6must have something in their hand."));
					return true;
				}
				target.getItemInHand().setDurability((short)0);
				player.sendMessage(ColorText.translate("&cRepair> &6Repaired held item of &e" + target.getName()));
			} else {
				if ((player.getItemInHand() == null) || (player.getItemInHand().getType() == Material.AIR)) {
					player.sendMessage(ColorText.translate("&cRepair> &6You must have anything in your hand."));
					return true;
				}
				player.getItemInHand().setDurability((short)0);
				player.sendMessage(ColorText.translate("&cRepair> &6Repaired held item."));
			}	
	   } else if (args[0].equalsIgnoreCase("all")) {
		   HashSet<ItemStack> repairInventory = new HashSet<ItemStack>();
		   if (args.length == 2) {
			   Player target = Bukkit.getPlayer(args[1]);
			   if (!Utils.isOnline(player, target)) {
				   Utils.PLAYER_NOT_FOUND(player, args[1]);
				   return true;
			   }
			   repairInventory.addAll(Arrays.asList(target.getInventory().getContents()));
			   repairInventory.addAll(Arrays.asList(target.getInventory().getArmorContents()));
			   for (ItemStack stack : repairInventory) {
				   if (stack != null) {
					   if (stack.getType() != Material.AIR) {
						   stack.setDurability((short)0);
					   }
				   }
			   }
			   player.sendMessage(ColorText.translate("&cRepair> &6Repaired inventory of &e" + target.getName()));
		   } else {
			   repairInventory.addAll(Arrays.asList(player.getInventory().getContents()));
			   repairInventory.addAll(Arrays.asList(player.getInventory().getArmorContents()));
			   for (ItemStack stack : repairInventory) {
				   if (stack != null) {
					   if (stack.getType() != Material.AIR) {
						   stack.setDurability((short)0);
					   }
				   }
			   }
			   player.sendMessage(ColorText.translate("&cRepair> &6Repaired inventory of &e" + player.getName()));
		   }
	  } else {
		  player.sendMessage(ColorText.translate("&cRepair> &6Repair sub-commands '&e" + args[0] + "&6' not found."));
   		}
		return true;
	}
}
