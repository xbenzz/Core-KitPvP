package me.core.Handler;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import net.md_5.bungee.api.ChatColor;

public class TabHandler {
	
	public static void removeEmptyTeams() {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Set<Team> teams = board.getTeams();
		for (Team e : teams) {
			if (e.getSize() < 1 || e.getEntries().size() < 1) {
				e.unregister();
			}
		}
	}
	
	public static void setTab(Player player) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = online.getScoreboard();
			if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
				scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			}
		    
	    	KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
  			Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
			int level = Integer.MAX_VALUE - kit.getLevel();   	
	    	
	         if (profile.getRank().isAboveOrEqual(Rank.OWNER)) {
	 	    	Team owner = scoreboard.getTeam(level + "AA");
	 	    	if (owner == null) {
	 	    		 owner = scoreboard.registerNewTeam(level + "AA");
	 	    		 owner.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &c"));
	 	    	}
	 	    	
	        	if (!owner.hasEntry(player.getName())) {
	        		owner.addEntry(player.getName());
	        		online.setScoreboard(scoreboard);
	        	}
	         } else if (profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
	 	    	Team admin = scoreboard.getTeam(level + "AB");
	 	    	if (admin == null) {
	 	    		 admin = scoreboard.registerNewTeam(level + "AB");
	 	    		 admin.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &c"));
	 	    	}
	 	    	
		        if (!admin.hasEntry(player.getName())) {
		        	admin.addEntry(player.getName());
		        	online.setScoreboard(scoreboard);
		        }
		     } else if (profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
			 	Team srmod = scoreboard.getTeam(level + "AC");
			    if (srmod == null) {
			    	srmod = scoreboard.registerNewTeam(level + "AC");
			    	srmod.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &5"));
			    }
			    	
			    if (!srmod.hasEntry(player.getName())) {
		        	srmod.addEntry(player.getName());
		        	online.setScoreboard(scoreboard);
			    }
		     } else if (profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
			    Team mod = scoreboard.getTeam(level + "AD");
			    if (mod == null) {
			    	 mod = scoreboard.registerNewTeam(level + "AD");
			    	 mod.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &3"));
			    }
			    
			    if (!mod.hasEntry(player.getName())) {
		        	mod.addEntry(player.getName());
		        	online.setScoreboard(scoreboard);
			     }
		     } else if (profile.getRank().isAboveOrEqual(Rank.HELPER)) {
			    Team help = scoreboard.getTeam(level + "AE");
			    if (help == null) {
			    	help = scoreboard.registerNewTeam(level + "AE");
			    	help.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &2"));
			    }
			    
			    if (!help.hasEntry(player.getName())) {
		        	help.addEntry(player.getName());
			        online.setScoreboard(scoreboard);
			     }
		     } else if (profile.getRank().isAboveOrEqual(Rank.BUILDER)) {
			    Team builder = scoreboard.getTeam(level + "BA");
			    if (builder == null) {
			    	 builder = scoreboard.registerNewTeam(level + "BA");
			    	 builder.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &9"));
			    }
			    
			    if (!builder.hasEntry(player.getName())) {
		        	builder.addEntry(player.getName());
		        	online.setScoreboard(scoreboard);
			    }
		     } else if (profile.getRank().isAboveOrEqual(Rank.YT)) {
			    Team yt = scoreboard.getTeam(level + "BC");
			    if (yt == null) {
			    	 yt = scoreboard.registerNewTeam(level + "BC");
			    	 yt.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &6"));
			    }
			    
			    if (!yt.hasEntry(player.getName())) {
		        	yt.addEntry(player.getName());
			       	online.setScoreboard(scoreboard);
			    }
		     } else if (profile.getRank().isAboveOrEqual(Rank.MVP)) {
			    Team mvp = scoreboard.getTeam(level + "CA");
			    if (mvp == null) {
			    	mvp = scoreboard.registerNewTeam(level + "CA");
			    	mvp.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &d"));
			    }
			    
			    if (!mvp.hasEntry(player.getName())) {
			    	mvp.addEntry(player.getName());
			    	online.setScoreboard(scoreboard);
			    }
		     } else if (profile.isDonator()) {
			    Team donator = scoreboard.getTeam(level + "D");
			    if (donator == null) {
			    	 donator = scoreboard.registerNewTeam(level + "D");
			    	 donator.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &a"));
			    }
			    
			    if (!donator.hasEntry(player.getName())) {
			       	donator.addEntry(player.getName());
			       	online.setScoreboard(scoreboard);
			    }
		     } else {
				Team rookie = scoreboard.getTeam(level + "Z");
				if (rookie == null) {
					rookie = scoreboard.registerNewTeam(level + "Z");
					rookie.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] "));
				}
		    	if (!rookie.hasEntry(player.getName())) {
			    	rookie.addEntry(player.getName());
			    	online.setScoreboard(scoreboard);
		    	}
		     }   
		}
	}
	
	public static void updateTab() {
		Bukkit.getScheduler().runTaskTimer(Kit.getPlugin(), () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
    			Optional<KitPlayer> opK = Kit.getKitManager().getUser(player.getUniqueId());
    	        if (!opK.isPresent()) {
    	        	continue;
    	        }
    	    	KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
    			Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	    		for (Player online : Bukkit.getOnlinePlayers()) {
	    			Scoreboard scoreboard = online.getScoreboard();
	    		    
	    			int level = Integer.MAX_VALUE - kit.getLevel();
	    	    	online.setHealth(online.getHealth());
	    	    	
	    	        if (profile.getRank().isAboveOrEqual(Rank.OWNER)) {
		    	    	Team owner = scoreboard.getTeam(level + "AA");
		    	    	if (owner == null) {
		    	    		 owner = scoreboard.registerNewTeam(level + "AA");
		    	    		 owner.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &c"));
		    	    	}
		    	    	
	    	        	if (!owner.hasEntry(player.getName())) {
	    	        		owner.addEntry(player.getName());
	    	        		online.setScoreboard(scoreboard);
	    	        	}
	    	         } else if (profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
	 	    	    	Team admin = scoreboard.getTeam(level + "AB");
		    	    	if (admin == null) {
		    	    		 admin = scoreboard.registerNewTeam(level + "AB");
		    	    		 admin.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &c"));
		    	    	}
		    	    	
	    		        if (!admin.hasEntry(player.getName())) {
	    		        	admin.addEntry(player.getName());
	    		        	online.setScoreboard(scoreboard);
	    		        }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.SENIOR_MOD)) {
	 	    	    	Team srmod = scoreboard.getTeam(level + "AC");
		    	    	if (srmod == null) {
		    	    		 srmod = scoreboard.registerNewTeam(level + "AC");
		    	    		 srmod.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &5"));
		    	    	}
		    	    	
	    			    if (!srmod.hasEntry(player.getName())) {
	    		        	srmod.addEntry(player.getName());
	    		        	online.setScoreboard(scoreboard);
	    			    }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.MODERATOR)) {
	 	    	    	Team mod = scoreboard.getTeam(level + "AD");
		    	    	if (mod == null) {
		    	    		 mod = scoreboard.registerNewTeam(level + "AD");
		    	    		 mod.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &3"));
		    	    	}
		    	    	
	    			  	if (!mod.hasEntry(player.getName())) {
	    		        	mod.addEntry(player.getName());
	    		        	online.setScoreboard(scoreboard);
	    			     }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.HELPER)) {
	 	    	    	Team help = scoreboard.getTeam(level + "AE");
		    	    	if (help == null) {
		    	    		 help = scoreboard.registerNewTeam(level + "AE");
		    	    		 help.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &2"));
		    	    	}
		    	    	
	    			    if (!help.hasEntry(player.getName())) {
	    			    	help.addEntry(player.getName());
	    			    	online.setScoreboard(scoreboard);
	    			     }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.BUILDER)) {
	 	    	    	Team builder = scoreboard.getTeam(level + "BA");
		    	    	if (builder == null) {
		    	    		 builder = scoreboard.registerNewTeam(level + "BA");
		    	    		 builder.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &9"));
		    	    	}
		    	    	
	    			    if (!builder.hasEntry(player.getName())) {
	    		        	builder.addEntry(player.getName());
	    			       	online.setScoreboard(scoreboard);
	    			    }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.YT)) {
	 	    	    	Team yt = scoreboard.getTeam(level + "BC");
		    	    	if (yt == null) {
		    	    		 yt = scoreboard.registerNewTeam(level + "BC");
		    	    		 yt.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &6"));
		    	    	}
		    	    	
	    			        if (!yt.hasEntry(player.getName())) {
	    		        		yt.addEntry(player.getName());
	    			        	online.setScoreboard(scoreboard);
	    			        }
	    		     } else if (profile.getRank().isAboveOrEqual(Rank.MVP)) {
	 	    	    	Team mvp = scoreboard.getTeam(level + "CA");
		    	    	if (mvp == null) {
		    	    		mvp = scoreboard.registerNewTeam(level + "CA");
		    	    		mvp.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &d"));
		    	    	}
		    	    	
	    			        if (!mvp.hasEntry(player.getName())) {
	    		        		mvp.addEntry(player.getName());
	    			        	online.setScoreboard(scoreboard);
	    			        }
	    		     } else if (profile.isDonator()) {
	 	    	    	Team donator = scoreboard.getTeam(level + "D");
		    	    	if (donator == null) {
		    	    		 donator = scoreboard.registerNewTeam(level + "D");
		    	    		 donator.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] &a"));
		    	    	}
		    	    	
	    			        if (!donator.hasEntry(player.getName())) {
	    			        	donator.addEntry(player.getName());
	    			        	online.setScoreboard(scoreboard);
	    			        }
	    		     } else {
	 	    			Team rookie = scoreboard.getTeam(level + "Z");
		    			if (rookie == null) {
		    				rookie = scoreboard.registerNewTeam(level + "Z");
		    				rookie.setPrefix(ChatColor.translateAlternateColorCodes('&', "&7[&2&l" + kit.getLevel() + "&7] "));
		    			}
		    			
	    			        if (!rookie.hasEntry(player.getName())) {
	    			        	rookie.addEntry(player.getName());
	    			        	online.setScoreboard(scoreboard);
	    			        }
	    		     	}   
	    			}
	    		}
	    	}, 20L, 1200L);
		}
	
}

