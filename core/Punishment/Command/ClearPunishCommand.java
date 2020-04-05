package me.core.Punishment.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Punishment.Punishment;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class ClearPunishCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		
		if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
			sender.sendMessage(Utils.NO_PERMISSION);
			return true;
		}
		
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <ID>"));
		} else {
			Integer ID = Integer.valueOf(args[0]);
			Punishment p = new Punishment();
			p.clear(ID);
			player.sendMessage(ColorText.translate("&cPunish> &6Punishment ID: &e" + ID + " &6was cleared!"));
		}
		return true;
	}
}
