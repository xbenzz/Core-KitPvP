package me.core.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class GamemodeCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  {
		if (!(sender instanceof Player)){
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
			player.sendMessage(ColorText.translate("&cUsage: /" + label + " <mode> <playerName>"));
		} else if ((args[0].equalsIgnoreCase("survival")) || (args[0].equalsIgnoreCase("s")) || (args[0].equalsIgnoreCase("0"))) {
			if (args.length != 2)  {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(ColorText.translate("&cServer> &6Your gamemode was updated to &eSurvival"));
			} else {
				Player target = Bukkit.getPlayer(args[1]);
				if (!Utils.isOnline(player, target)) {
					Utils.PLAYER_NOT_FOUND(player, args[1]);
					return true;
				}
				Profile tp = Kit.getProfileManager().getUser(target.getUniqueId()).get();
				target.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(ColorText.translate("&cServer> &6Set gamemode of " + tp.getRank().getColor() + target.getName() + " &6to &eSurvival"));
			}
		} else if ((args[0].equalsIgnoreCase("creative")) || (args[0].equalsIgnoreCase("c")) || (args[0].equalsIgnoreCase("1")))  {
			if (args.length != 2) {
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(ColorText.translate("&cServer> &6Your gamemode was updated to &eCreative"));
			} else {
				Player target = Bukkit.getPlayer(args[1]);
				if (!Utils.isOnline(player, target)) {
					Utils.PLAYER_NOT_FOUND(player, args[1]);
					return true;
				}
				Profile tp = Kit.getProfileManager().getUser(target.getUniqueId()).get();
				target.setGameMode(GameMode.CREATIVE);
				player.sendMessage(ColorText.translate("&cServer> &6Set gamemode of " + tp.getRank().getColor() + target.getName() + " &6to &eCreative."));
			}
		} else if ((args[0].equalsIgnoreCase("adventure")) || (args[0].equalsIgnoreCase("a")) || (args[0].equalsIgnoreCase("2"))) {
			if (args.length != 2) {
				player.setGameMode(GameMode.ADVENTURE);
				player.sendMessage(ColorText.translate("&cServer> &6Your gamemode was updated to &eAdventure"));
			} else {
				Player target = Bukkit.getPlayer(args[1]);
				if (!Utils.isOnline(player, target)) {
					Utils.PLAYER_NOT_FOUND(player, args[1]);
					return true;
				}
				Profile tp = Kit.getProfileManager().getUser(target.getUniqueId()).get();
				target.setGameMode(GameMode.ADVENTURE);
				player.sendMessage(ColorText.translate("&cServer> &6Set gamemode of " + tp.getRank().getColor() + target.getName() + " &6to &eAdventure."));
			}
		}
		else {
			player.sendMessage(ColorText.translate("&cServer> &6Gamemode sub-commands &e'" + args[0] + "' &6not found."));
		}
	return true;
	}
}
