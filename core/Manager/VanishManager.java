package me.core.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.HotbarUtils;
import net.md_5.bungee.api.ChatColor;

public class VanishManager {
	
	private static List<UUID> vanishList = new ArrayList<UUID>();
  
	public static boolean isVanished(Player player) {
		return vanishList.contains(player.getUniqueId());
	}
  
	public static List<UUID> getVanished() {
		return vanishList;
	}
  
	public static void setVanished(Player player, boolean s) {
		if (s) {
			vanishList.add(player.getUniqueId());
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				Profile profile = Kit.getProfileManager().getUser(pl.getUniqueId()).get();
				Profile profile2 = Kit.getProfileManager().getUser(player.getUniqueId()).get();
				
				if (!profile.getRank().isAboveOrEqual(Rank.HELPER) && profile2.getRank().isAboveOrEqual(Rank.HELPER)) { 
					pl.hidePlayer(player);
				} else if (!profile.getRank().isAboveOrEqual(Rank.MODERATOR) && profile2.getRank().isAboveOrEqual(Rank.MODERATOR)) {
					pl.hidePlayer(player);
				} else if (!profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD) && profile2.getRank().isAboveOrEqual(Rank.MODERATOR)) {
					pl.hidePlayer(player);
				} else if (!profile.getRank().isAboveOrEqual(Rank.ADMIN) && profile2.getRank().isAboveOrEqual(Rank.ADMIN)) {
					pl.hidePlayer(player);
				} else if (!profile.getRank().isAboveOrEqual(Rank.HELPER) && profile2.getRank().isAboveOrEqual(Rank.YT)) {
					pl.hidePlayer(player);
				}
			}
			player.sendMessage(ColorText.translate("&cVanish> &6Vanish Mode is now &eEnabled"));
		} else {
			vanishList.remove(player.getUniqueId());
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				online.showPlayer(player);
			}
			player.sendMessage(ColorText.translate("&cVanish> &6Vanish Mode is now &eDisabled"));
		}
    
		Bukkit.getScheduler().runTaskTimerAsynchronously(Kit.getPlugin(), () -> {
			if (vanishList.contains(player.getUniqueId())) {
        	   HotbarUtils.sendHotBarMessage(player, ChatColor.translateAlternateColorCodes('&', "&fYou are currently &c&lVANISHED&f!"));
			}   
		}, 20L, 20L);
	}

}
