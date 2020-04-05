package me.core.Commands.Staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Manager.VanishManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class VanishCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
    
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.YT)) {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return false;
		}
		if (player.getWorld().getName().equalsIgnoreCase("KitPvP") && !profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			player.sendMessage(ColorText.translate("&cPerks> &6This command is not avaliable in PvP."));
			return false;
		}
		VanishManager.setVanished(player, !VanishManager.isVanished(player));
		return true;
	}
}
