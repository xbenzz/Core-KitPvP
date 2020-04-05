package me.core.Handler;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.ChatColor;

public class ScoreHandler {
	
	public static void setSB(Player player) {
        KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        
		Objective health = board.registerNewObjective(ColorText.translate("&c"), "health");
		health.setDisplayName(ColorText.translate("&c❤"));
		health.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
        Objective obj = board.registerNewObjective("noflicker", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§d§l VendettaPvP §f(Kit)");
        
        Score blank = obj.getScore("  ");
        blank.setScore(12);	
        
        Team rank = board.registerNewTeam("Name");
        rank.addEntry(ChatColor.AQUA.toString());
        rank.setPrefix("§eName:§f ");
        rank.setSuffix("§f" + player.getName());
        obj.getScore(ChatColor.AQUA.toString()).setScore(11); 
        
        Team name = board.registerNewTeam("Kills");
        name.addEntry(ChatColor.BLACK.toString());
        name.setPrefix("§eKills:§f ");
        name.setSuffix("§f" + kit.getKills());
        obj.getScore(ChatColor.BLACK.toString()).setScore(10);
        
        Team death = board.registerNewTeam("Deaths");
        death.addEntry(ChatColor.DARK_RED.toString());
        death.setPrefix("§eDeaths:§f ");
        death.setSuffix("§f" + kit.getDeaths());
        obj.getScore(ChatColor.DARK_RED.toString()).setScore(9);
        
        Score blan = obj.getScore("   ");
        blan.setScore(8);	 
        
        Team exp = board.registerNewTeam("Level");
        exp.addEntry(ChatColor.BOLD.toString());
        exp.setPrefix("§eLevel:§f ");
        exp.setSuffix("§f" + kit.getLevel());
        obj.getScore(ChatColor.BOLD.toString()).setScore(7);
        
        Team coins = board.registerNewTeam("Money");
        coins.addEntry(ChatColor.GREEN.toString());
        coins.setPrefix("§eCoins:§f ");
        coins.setSuffix("§f" + kit.getCoins());
        obj.getScore(ChatColor.GREEN.toString()).setScore(6);
        
        Score blan2 = obj.getScore("    ");
        blan2.setScore(5);	
        
        Team lob = board.registerNewTeam("Progress");
        lob.addEntry(ChatColor.DARK_AQUA.toString());
        lob.setPrefix("§eKills Ne§f");
        lob.setSuffix("§eded:§f " + (kit.getKillsNeeded() - kit.getKills()));
        obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(4);
        
        Team pl = board.registerNewTeam("Streak");
        pl.addEntry(ChatColor.DARK_BLUE.toString());
        pl.setPrefix("§eCurrent S§f");
        pl.setSuffix("§etreak:§f " + kit.getStreak());
        obj.getScore(ChatColor.DARK_BLUE.toString()).setScore(3);
        
        Score bla = obj.getScore(" ");
        bla.setScore(2);
        
        Team link = board.registerNewTeam("Link");
        link.addEntry(ChatColor.DARK_GRAY.toString());
        link.setPrefix("§bwww.Vendet");
        link.setSuffix("§btaPvP.org");
        obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(1);
		 
	    player.setScoreboard(board);
	    
		Bukkit.getScheduler().runTaskTimer(Kit.getPlugin(), () -> {
    	        Utils.getGlobal("ALL");
    	        Optional<KitPlayer> opK = Kit.getKitManager().getUser(player.getUniqueId());
    	        if (!opK.isPresent()) {
    	        	return;
    	        }
    	        KitPlayer kits = Kit.getKitManager().getUser(player.getUniqueId()).get();
        		board.getTeam("Streak").setSuffix(ChatColor.YELLOW.toString() + "treak: " + ChatColor.WHITE + kits.getStreak());
        		board.getTeam("Kills").setSuffix(ChatColor.WHITE.toString() + kits.getKills());
        		board.getTeam("Deaths").setSuffix(ChatColor.WHITE.toString() + kits.getDeaths());
        		board.getTeam("Level").setSuffix(ChatColor.WHITE.toString() + kits.getLevel());
        		board.getTeam("Money").setSuffix(ChatColor.WHITE.toString() + kits.getCoins());
        		board.getTeam("Progress").setSuffix(ChatColor.YELLOW.toString() + "eded: " + ChatColor.WHITE + (kits.getKillsNeeded() - kits.getKills()));
    	        String header = ColorText.translate("  &eYou are playing on &d&lVendettaPvP&e!");
    	        String footer = ColorText.translate("  &cThere are &e" + Utils.getCount() + " &cPlayers online!");
        		for (Player on : Bukkit.getOnlinePlayers()) {
        			Utils.sendTablist(on, header, footer);
        		}
		}, 1200L, 1200L);
	}
	
	

}
