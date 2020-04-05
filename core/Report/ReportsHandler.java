package me.core.Report;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.Kit.Kit;
import me.core.Utilities.ColorText;

public class ReportsHandler implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("Reports")) {
			Player player = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			}
			e.setCancelled(true);
			if (e.isLeftClick()) {
				String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
				String date = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[1]);
				int ID = Integer.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(3).split(" ")[1]));
				
				OfflinePlayer t = Bukkit.getOfflinePlayer(name);
	    		ReportLog log = new ReportLog(ID, t.getUniqueId(), date);
		    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    		String link = log.getLogLink();
				
			    	Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
			    		String msg = ColorText.translate("&cReports> &e" + name + " &6Logs: http://www.vendettapvp.org/reports/" + link + ".txt");
			    		player.sendMessage(msg);
			    		player.closeInventory();
			    	});
		    	});
			}
			if (e.isRightClick()) {
				int ID = Integer.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(3).split(" ")[1]));
		    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
		    		Report rp = new Report(ID);
		    		
		    		Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
			    		player.sendMessage(ColorText.translate("&cReports> &6You have closed the report on &e" + Bukkit.getOfflinePlayer(rp.getPlayer()).getName()));
			    		rp.delete();
			    		player.closeInventory();
		    		});
		    	});
			}
		}
	}
	
	  public Inventory openReports(Player p, List<Report> reports) {
		   Inventory inv = Bukkit.createInventory(p, 54, "Reports");
		   int slot = 0;
		   
		   for (Report r : reports) {
			   if (slot > 44) {
				   break;
			   }
			   
			   	OfflinePlayer offender = Bukkit.getOfflinePlayer(r.getPlayer());
			   	OfflinePlayer reporter = Bukkit.getOfflinePlayer(r.getReporter());
			   
			    ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
			    SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
			    titleMeta.setOwner(offender.getName());
			    titleMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + offender.getName());
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.YELLOW + "Date: " + ChatColor.WHITE + r.getDate());
				lore.add(ChatColor.YELLOW + "Reported By: " + ChatColor.WHITE + reporter.getName());
				lore.add(ChatColor.YELLOW + "Reason: " + ChatColor.WHITE + r.getReason());
				lore.add(ChatColor.YELLOW + "ID: " + ChatColor.RED + r.getID());
				lore.add(" ");
				lore.add(ChatColor.WHITE + "Left click to get chat logs");
				lore.add(ChatColor.WHITE + "Right click to close report");
				titleMeta.setLore(lore);
			    title.setItemMeta(titleMeta);
		   
			    inv.setItem(slot, title);
			    slot++;
		   }
		   return inv;
	  }
}
