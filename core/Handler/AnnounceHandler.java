package me.core.Handler;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Utilities.ColorText;

public class AnnounceHandler {
	
	private static ArrayList<String> messages = new ArrayList<String>();
  
	public static void setUp() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Kit.getPlugin(), new Runnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers().size() < 1) {
					return;
				}
				for (String ann : Kit.getPlugin().getConfig().getStringList("Announcement")) {
					AnnounceHandler.messages.add(ann.replace("%newline%", "\n"));
				}
				if (AnnounceHandler.messages.size() <= 0) {
					return;
				}
				Bukkit.broadcastMessage(ColorText.translate("&7« &7&m---&7&m---&r &7»&r &d&lVendettaPvP Network &7« &7&m---&7&m---&r &7»"));
				Bukkit.broadcastMessage(ColorText.translate(randomAnnouncement()));
				Bukkit.broadcastMessage(ColorText.translate("&7« &7&m--------------------------------&7&m---&r &7»"));
			}
		}, 0L, 2400L);
	}
  
	private static String randomAnnouncement() {
		Random rand = new Random();
		int size = messages.size();
    
		String message = (String)messages.get(rand.nextInt(size));
		return message;
	}
  
}
