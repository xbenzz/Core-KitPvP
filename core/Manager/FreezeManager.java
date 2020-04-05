package me.core.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.core.Utilities.ColorText;

public class FreezeManager {
	
	private static List<UUID> freezeList = new ArrayList<UUID>();
  
	public static boolean isFrozen(Player player) {
		return freezeList.contains(player.getUniqueId());
	}
  
	public static void setFreeze(Player player, boolean s) {
		if (s) {
			freezeList.add(player.getUniqueId());
			player.openInventory(getFreezeInventory());
			player.sendMessage(ColorText.translate("&cFreeze> &6You have been &efrozen&6!"));
		} else {
			freezeList.remove(player.getUniqueId());
			player.closeInventory();
			player.sendMessage(ColorText.translate("&cFreeze> &6You have been &eunfrozen&6!"));
		}
	}
  
	public static Inventory getFreezeInventory() {
		Inventory inventory = Bukkit.createInventory(null, 9, "Freeze Inventory");
    
		ItemStack pvp = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta pvpim = pvp.getItemMeta();
		pvpim.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You are Frozen!");
		ArrayList<String> pvpar = new ArrayList<String>();
		pvpar.add(ChatColor.translateAlternateColorCodes('&', " "));
		pvpar.add(ChatColor.translateAlternateColorCodes('&', "&cYou are frozen for a Screenshare!"));
		pvpar.add(ChatColor.translateAlternateColorCodes('&', "&c> Join our Discord: &ehttps://discord.gg/unBkXur ")); 
		pvpar.add(ChatColor.translateAlternateColorCodes('&', "&c> Enter the Support Waiting Room")); 
		pvpar.add(ChatColor.translateAlternateColorCodes('&', " "));
		pvpar.add(ChatColor.translateAlternateColorCodes('&', "&cYou have 5 minutes! &c&lLogout = Ban"));
		pvpim.setLore(pvpar);
		pvp.setItemMeta(pvpim);
   	
		inventory.setItem(4, pvp);
		return inventory;
	}
  
}
