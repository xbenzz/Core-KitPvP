package me.core.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.Kit.Kit;
import me.core.Handler.ScoreHandler;

public class ScoreListener implements Listener {
	
	@EventHandler
	public void setSB(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Kit.getPlugin(), () -> {
			ScoreHandler.setSB(player);
		}, 30L);
	}

}
