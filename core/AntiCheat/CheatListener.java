package me.core.AntiCheat;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.core.Manager.LogManager;
import me.rerere.matrix.api.events.PlayerViolationEvent;

public class CheatListener implements Listener {
	
	@EventHandler
	public void onCheat(PlayerViolationEvent e) {
		String date = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(new Date());
		Player p = e.getPlayer();
		int v = e.getViolations();
		String hack = fixType(e.getHackType().name());
		String format = "<" + date + "> (" + Bukkit.getServerName() + ") - " + hack + " [" + v + " Violations]";
		LogManager.addNote(p.getUniqueId(), format);
	}
	
	public static String fixType(String type) {
		String fixed = "";
		if (type.indexOf(" ") == -1) {
			String n = type.toLowerCase();
			fixed += String.valueOf(n.charAt(0)).toUpperCase();
			for (int i = 1; i < n.length(); i++) {
				fixed += n.charAt(i);
			}
		} else {
			String n = type.toLowerCase();
			fixed += String.valueOf(n.charAt(0)).toUpperCase();
			for (int i = 1; i < n.indexOf(" "); i++) {
				fixed += n.charAt(i);
			}
			fixed += " ";
			char letter = n.charAt(n.indexOf(" ") + 1);
			fixed += String.valueOf(letter).toUpperCase();
			for (int i = n.indexOf(" ") + 2; i < n.length(); i++) {
				fixed += n.charAt(i);
			}
		}
		return fixed;
	}

}
