package me.Kit.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.Kit.Kit;
import me.Kit.Kits.ArcherKit;
import me.Kit.Kits.KnightKit;
import me.Kit.Kits.StarterKit;
import me.Kit.Manager.BotManager;
import me.Kit.Manager.GUIs;
import me.Kit.PvPBot.CitizensNPC;
import me.Kit.PvPBot.CitizensNPC.Difficulty;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class BotListener implements Listener {
	
	@EventHandler
	public void onKit(InventoryClickEvent e) {
		if (e.getInventory().getName().contains("Bot Kits: ")) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			}
			e.setCancelled(true);
			Player player = (Player) e.getWhoClicked();
			
			BotManager man = new BotManager(player);
			Difficulty mode = Difficulty.valueOf(ChatColor.stripColor(e.getInventory().getName().split(" ")[2].toUpperCase()));
			
			if (e.getSlot() == 10) {
				man.startBot(new StarterKit(), mode);
			} else if (e.getSlot() == 12) {
				man.startBot(new KnightKit(), mode);
			} else if (e.getSlot() == 14) {
				man.startBot(new ArcherKit(), mode);
			}
		}
	}

	
	@EventHandler
	public void onDuel(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("Bot Duels")) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta() == null) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			}
			e.setCancelled(true);
			Player player = (Player) e.getWhoClicked();
			Profile profile = Kit.getProfileManager().getUser(player.getUniqueId()).get();
			
			BotManager man = new BotManager(player);
			GUIs g = new GUIs();
			Difficulty mode = Difficulty.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[1].toUpperCase()));
			
			
			if (e.getSlot() == 10 || e.getSlot() == 11) {
				if (profile.getRank().isAboveOrEqual(Rank.MVP)) {
					player.openInventory(g.botKits(player, String.valueOf(mode)));
				} else {
					man.startBot(new KnightKit(), mode);
				}
			} else if (e.getSlot() > 11) {
				if (profile.getRank().isAboveOrEqual(Rank.MVP)) {
					player.openInventory(g.botKits(player, String.valueOf(mode)));
				} else if (profile.getRank().isAboveOrEqual(Rank.ELITE)) {
					man.startBot(new KnightKit(), mode);
				} else {
					player.sendMessage(ColorText.translate("&cBot> &6You need &bElite &6rank to use Hard or Hacker mode."));
				}
			}
		}
	}
	
	@EventHandler
	public void onDeathEvent(PlayerDeathEvent event) {
		Player victim = (Player) event.getEntity();
		
		System.out.println("I have ran #4 ");
		System.out.println(BotManager.getMatches().containsKey(victim.getUniqueId()));
		if (BotManager.getMatches().containsKey(victim.getUniqueId())) {
			System.out.println("I have ran #5");
			CitizensNPC task = BotManager.getMatches().get(victim.getUniqueId());
			if (task.combatTask != null) {
				System.out.println("I have ran #6");
				task.combatTask.cancel();
				task.destroy();
				BotManager.remove(victim);
			    Utils.respawn(victim);
				return;
			}
		}
	}

}
