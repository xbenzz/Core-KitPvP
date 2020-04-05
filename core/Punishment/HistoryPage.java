package me.core.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.core.Utilities.FormatNumber;

public class HistoryPage {
	
	public Inventory openIPHistory(Player p, String ip, List<Punishment> list) {

		Inventory inv = Bukkit.createInventory(p, 54, "Punishment History");
    
		ItemStack title = new ItemStack(Material.STRING, 1);
		ItemMeta titleMeta = title.getItemMeta();
		titleMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + ip);
		title.setItemMeta(titleMeta);
		inv.setItem(4, title);
   
		int slot = 9;
		String staffmember = "";
    
		for (Punishment pun : list) {
			if (slot > 44) {
				break;
			}
	   
			ItemStack userhistory = new ItemStack(pun.getCategory().mat());
			ItemMeta userHistoryim = userhistory.getItemMeta();
   
			ArrayList<String> userhistoryar = new ArrayList<String>();
			userHistoryim.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + pun.getCategory().value());
			userhistoryar.add(ChatColor.YELLOW + "Punishment Type: " + ChatColor.WHITE + pun.getCategory().desc());
			userhistoryar.add(ChatColor.YELLOW + "Reason: " + ChatColor.WHITE + pun.getReason());
			
			if (!pun.getPunisher().equalsIgnoreCase("CONSOLE")) {
				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(pun.getPunisher()));
				staffmember = staff.getName();
			} else {
				staffmember = "CONSOLE";
			}
			
			userhistoryar.add(ChatColor.YELLOW + "Staff: " + ChatColor.WHITE + staffmember);
			userhistoryar.add(ChatColor.YELLOW + "Date: " + ChatColor.WHITE + pun.getDate());
			
			String duration;
			if (pun.getDuration() == -1) {
				duration = "Permanent";
			} else {
				duration = FormatNumber.MakeStr(pun.getDuration(), 1);
			}
			
			userhistoryar.add(ChatColor.YELLOW + "Duration: " + ChatColor.WHITE + duration);
			userhistoryar.add(" ");
			userhistoryar.add(ChatColor.YELLOW + "ID: " + ChatColor.RED + pun.getID());
			
			if (pun.isActive()) {
				userHistoryim.addEnchant(Enchantment.DURABILITY, 1, true);
				userHistoryim.setLore(userhistoryar);
				userhistory.setItemMeta(userHistoryim);
			}
			
			if (pun.isRemoved()) {
				String removedby;
				if (pun.removedBy().equalsIgnoreCase("CONSOLE")) {
					removedby = "CONSOLE";
				} else {
					OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(pun.removedBy())); 
					removedby = of.getName();
				}
				
				userhistoryar.add(ChatColor.YELLOW + "Removed By: " + ChatColor.RED + removedby);
				userHistoryim.setLore(userhistoryar);
				userhistory.setItemMeta(userHistoryim);
			}
			
			userHistoryim.addItemFlags(ItemFlag.HIDE_DESTROYS);
			userHistoryim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			userHistoryim.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			userHistoryim.setLore(userhistoryar);
			userhistory.setItemMeta(userHistoryim);
   
			inv.setItem(slot, userhistory);
			slot++;
    	
		}
		return inv;
  	}
	
	public Inventory openHistory(Player p, String target, List<Punishment> list) {
		UUID uid = UUID.fromString(target);
		OfflinePlayer t = Bukkit.getOfflinePlayer(uid);

		Inventory inv = Bukkit.createInventory(p, 54, "Punishment History");
    
		ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
		titleMeta.setOwner(t.getName());
		titleMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + t.getName());
		title.setItemMeta(titleMeta);
		inv.setItem(4, title);
   
		int slot = 9;
		String staffmember = "";
    
		for (Punishment pun : list) {
			if (slot > 44) {
				break;
			}
	   
			ItemStack userhistory = new ItemStack(pun.getCategory().mat());
			ItemMeta userHistoryim = userhistory.getItemMeta();
   
			ArrayList<String> userhistoryar = new ArrayList<String>();
			userHistoryim.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + pun.getCategory().value());
			userhistoryar.add(ChatColor.YELLOW + "Punishment Type: " + ChatColor.WHITE + pun.getCategory().desc());
			
			if (pun.getType() == Type.BAN || pun.getType() == Type.MUTE || pun.getType() == Type.IP)
				userhistoryar.add(ChatColor.YELLOW + "Reason: " + ChatColor.WHITE + pun.getReason());
			
			if (!pun.getPunisher().equalsIgnoreCase("CONSOLE")) {
				OfflinePlayer staff = Bukkit.getOfflinePlayer(UUID.fromString(pun.getPunisher()));
				staffmember = staff.getName();
			} else {
				staffmember = "CONSOLE";
			}
			
			userhistoryar.add(ChatColor.YELLOW + "Staff: " + ChatColor.WHITE + staffmember);
			userhistoryar.add(ChatColor.YELLOW + "Date: " + ChatColor.WHITE + pun.getDate());
			
			String duration;
			if (pun.getDuration() == -1) {
				duration = "Permanent";
			} else {
				duration = FormatNumber.MakeStr(pun.getDuration(), 1);
			}
			
			userhistoryar.add(ChatColor.YELLOW + "Duration: " + ChatColor.WHITE + duration);
			userhistoryar.add(" ");
			userhistoryar.add(ChatColor.YELLOW + "ID: " + ChatColor.RED + pun.getID());
			
			if (pun.isActive()) {
				userHistoryim.addEnchant(Enchantment.DURABILITY, 1, true);
				userHistoryim.setLore(userhistoryar);
				userhistory.setItemMeta(userHistoryim);
			}
			
			if (pun.isRemoved()) {
				String removedby;
				if (pun.removedBy().equalsIgnoreCase("CONSOLE")) {
					removedby = "CONSOLE";
				} else {
					OfflinePlayer of = Bukkit.getOfflinePlayer(UUID.fromString(pun.removedBy())); 
					removedby = of.getName();
				}
				
				userhistoryar.add(ChatColor.YELLOW + "Removed By: " + ChatColor.RED + removedby);
				userHistoryim.setLore(userhistoryar);
				userhistory.setItemMeta(userHistoryim);
			}
			
			userHistoryim.addItemFlags(ItemFlag.HIDE_DESTROYS);
			userHistoryim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			userHistoryim.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			userHistoryim.setLore(userhistoryar);
			userhistory.setItemMeta(userHistoryim);
   
			inv.setItem(slot, userhistory);
			slot++;
    	
		}
		return inv;
  	}
}