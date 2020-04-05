package me.core.Manager;

import java.util.List;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Punishment.Punishment;

public class PunishmentManager {
	
	public static void checkPunishments() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Kit.getPlugin(), () -> {
			Punishment p = new Punishment();
			List<Punishment> a2 = p.getAllActive();
				for (Punishment a : a2) {
					if (!a.isPermanent()) {
						if (Long.valueOf(a.getExpire()) - System.currentTimeMillis() <= 0L) {
							a.remove();
						}
					}
				}
		}, 0L, 6000L);
	}

}
