package me.core.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.Kit.Kit;
import me.core.Handler.TabHandler;

public class TabListener implements Listener {
	
	@EventHandler
	public void setTab(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Kit.getPlugin(), () -> {
			TabHandler.setTab(player);
			TabHandler.updateTab();
		}, 20L);
	}
		
	
}
