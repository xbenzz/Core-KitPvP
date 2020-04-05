package me.Kit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Kit.Manager.GUIs;

public class KitsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only");
		    return true;
		}
		Player player = (Player)sender; 
		player.openInventory(new GUIs().kits(player));
		return true;
	}

}
