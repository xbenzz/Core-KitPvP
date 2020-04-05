package me.Kit.Manager;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Utilities.HotbarUtils;
import net.md_5.bungee.api.ChatColor;

public class CombatManager {
	
	private static HashMap<UUID, Long> tagged = new HashMap<UUID, Long>();
	
	public static boolean isTagged(Player p) {
		return tagged.containsKey(p.getUniqueId());
	}
	
	public static void unTag(Player p) {
		if (tagged.containsKey(p.getUniqueId())) {
			tagged.remove(p.getUniqueId());
		}
	}
	
	public static void tag(Player attacker, Player victim) {
		long time = System.currentTimeMillis() + 10000;
		if (isTagged(attacker)) {
			tagged.replace(attacker.getUniqueId(), time);
		} else {
			tagged.put(attacker.getUniqueId(), time);
		}
		
		if (isTagged(victim)) {
			tagged.replace(victim.getUniqueId(), time);
		} else {
			tagged.put(victim.getUniqueId(), time);
		}
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Kit.getPlugin(), () -> {
			if (tagged.containsKey(attacker.getUniqueId())) {
				long t = ((tagged.get(attacker.getUniqueId()) - System.currentTimeMillis()) / 1000);
				HotbarUtils.sendHotBarMessage(attacker, ChatColor.translateAlternateColorCodes('&', "&6Combat Tag: &c" + t + " Seconds"));
			}  
			
			if (tagged.containsKey(victim.getUniqueId())) {
				long t = ((tagged.get(victim.getUniqueId()) - System.currentTimeMillis()) / 1000);
				HotbarUtils.sendHotBarMessage(victim, ChatColor.translateAlternateColorCodes('&', "&6Combat Tag: &c" + t + " Seconds"));
			}   
		}, 20L, 20L);
	}
	
	public static void checkTags() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Kit.getPlugin(), () -> {
			if (tagged.size() >= 1) {
				for (UUID u : tagged.keySet()) {
					if (tagged.get(u) - System.currentTimeMillis() <= 0L) {
						tagged.remove(u);
					}
				}
			}
		}, 20L, 20L);
	}

}
