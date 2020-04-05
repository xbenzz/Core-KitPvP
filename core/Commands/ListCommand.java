package me.core.Commands;

import com.google.common.base.Joiner;

import me.Kit.Kit;
import me.core.Manager.VanishManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(" ");
		sender.sendMessage(ColorText.translate("&cOwner&7, &cAdmin&7, &5SrMod&7, &3Mod&7, &2Helper&7, &9Builder&7, &6YT&7, &dMVP&7, &bElite&7, &aVIP&7, &7User"));
		List<String> list = new ArrayList<String>();
		if (!list.isEmpty()) {
			list.clear();
		}
		for (Player online : Bukkit.getServer().getOnlinePlayers()) {
			Profile pro = Kit.getProfileManager().getUser(online.getUniqueId()).get();
			Rank rank = pro.getRank();
			if (!VanishManager.isVanished(online)) {
				list.add(rank.getColor() + online.getName());
			}
		}	
		if (!list.isEmpty()) {
			sender.sendMessage(ColorText.translate("&f(" + list.size() + '/' + Bukkit.getServer().getMaxPlayers() + "): " + Joiner.on("&7, &r").join(list)));
		}
		sender.sendMessage(" ");
		return true;
	}
	
}
