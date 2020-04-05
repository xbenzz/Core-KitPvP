package me.core.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.core.Utilities.ColorText;

public class InvListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
	    if (event.getInventory().getName().startsWith(ColorText.translate("&6Inspecting: &f"))) {
	      event.setCancelled(true);
	    }
	    
		if (event.getInventory().getName().equalsIgnoreCase("Punishment History")) {
			event.setCancelled(true);
		}
		  
		if (event.getInventory().getName().contains("Statistics")) {   
			event.setCancelled(true);
		}
	}
	
}
