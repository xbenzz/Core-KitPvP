package me.Kit.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Kit.Kits.Kits;
import me.Kit.PvPBot.CitizensNPC;
import me.Kit.PvPBot.CitizensNPC.Difficulty;

public class BotManager {
	
	public static HashMap<UUID, CitizensNPC> inMatch = new HashMap<UUID, CitizensNPC>();
	private Player p;
	
	public BotManager(Player p) {
		this.p = p;
	}
	
	public void startBot(Kits kit, Difficulty dif) {
		List<UUID> players = new ArrayList<UUID>();
		players.add(p.getUniqueId());
		
		Random rand = new Random();
		int nums = rand.nextInt(111);
		CitizensNPC n = new CitizensNPC("Ben_" + nums, "xBenz", new Location(p.getWorld(), -140.0, 5.0, -129.0));
		
		n.startCombatTask(players, dif, kit);
		KitManager.giveKit(p, kit);
		p.teleport(new Location(p.getWorld(), 50.0, 67.0, 435.0));
		
		for (Player o : Bukkit.getOnlinePlayers()) {
			o.hidePlayer(p);
			if (!o.getName().contains("Ben_"))
				p.hidePlayer(o);
		}	
	}
	
	public static void add(Player p, CitizensNPC npc) {
		inMatch.put(p.getUniqueId(), npc);
	}
	
	public static void remove(Player p) {
		if (inMatch.containsKey(p.getUniqueId())) {
			inMatch.remove(p.getUniqueId());
		}
	}
	
	public static HashMap<UUID, CitizensNPC> getMatches() {
		return inMatch;
	}

	
}
