package me.core.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;

public class RenameCommand implements CommandExecutor {
  
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ColorText.translate("&cYou must be player to execute this commands."));
			return true;
		}
		
		Player player = (Player)sender;
		Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
		if (!profile.getRank().isAboveOrEqual(Rank.ELITE))  {
			player.sendMessage(ColorText.translate(Utils.NO_PERMISSION));
			return true;
		}
		
		if (args.length < 1) {
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <name>"));
			player.sendMessage(ColorText.translate("&cOnly &dMVP &cplayers can use colors in rename!"));
		} else {
			if ((player.getItemInHand() == null) || (player.getItemInHand().getType() == Material.AIR)) {
				player.sendMessage(ColorText.translate("&cYou must have something in your hand."));
				return true;
			}
			
			String name = "";
			for (int r = 0; r < args.length; r++) {
				name = name + args[r] + " ";
			}
			
			String n = "";
			if (profile.getRank().isAboveOrEqual(Rank.MVP)) {
				n = ColorText.translate(name);
			} else {
				n = ColorText.translate(name);
				n = ChatColor.stripColor(n);
				n = ColorText.translate("&d" + n);
			}
			
			ItemStack item = player.getItemInHand();
			ItemMeta meta = player.getItemInHand().getItemMeta();
			player.sendMessage(ColorText.translate("&cPerk> &6Your item has been renamed to " + n));
			meta.setDisplayName(n);
			item.setItemMeta(meta);
		}
		return true;
	}
}
