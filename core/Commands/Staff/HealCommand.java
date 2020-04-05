package me.core.Commands.Staff;

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

public class HealCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile user = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!user.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return true;
		}
    
		if (args.length < 1) {
			if (player.getHealth() == player.getMaxHealth()) {
				player.sendMessage(ColorText.translate("&cHeal> &6You already have full health! &7(&e" + player.getMaxHealth() + "&7)"));
				return true;
			}
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setFireTicks(0);
			player.getActivePotionEffects().clear();
			player.sendMessage(ColorText.translate("&cHeal> &6You have been fully healed."));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (!Utils.isOnline(player, target)) {
				Utils.PLAYER_NOT_FOUND(player, args[0]);
				return true;
			}
			if (target.getHealth() == target.getMaxHealth()) {
				player.sendMessage(ColorText.translate("&cHeal> &e" + target.getName() + " &6already has full health! &7(&e" + player.getMaxHealth() + "&7)"));
				return true;
			}
			target.setHealth(20);
			target.setFoodLevel(20);
			target.setFireTicks(0);
			target.getActivePotionEffects().clear();
			player.sendMessage(ColorText.translate("&cHeal> &e" + target.getName() + " &6has been fully healed."));
		}
		return true;
	}
}