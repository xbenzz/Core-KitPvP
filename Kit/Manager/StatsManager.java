package me.Kit.Manager;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;
import me.core.Utilities.FormatNumber;

public class StatsManager {
	
	public static Inventory statsMenuSelf(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "Statistics");
		Profile profile = Kit.getProfileManager().getUser(p.getUniqueId()).get();
		KitPlayer kit = Kit.getKitManager().getUser(p.getUniqueId()).get();
		DecimalFormat form = new DecimalFormat("0.0");
		
		ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
		titleMeta.setOwner(p.getName());
		titleMeta.setDisplayName(profile.getRank().getColor() + profile.getRank().getPrefix() + p.getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ColorText.translate("&eLevel: &f" + kit.getLevel()));
		lore.add(ColorText.translate("&eEXP: &f" + kit.getExp()));
		lore.add(ColorText.translate("&eCoins: &f" + kit.getCoins()));
		lore.add(ColorText.translate("&eTime In Game: &a" + FormatNumber.MakeStr(profile.getTime(), 1)));
		lore.add(ColorText.translate(" "));
		lore.add(ColorText.translate("&eLevel Progress: &a" + form.format(((double) kit.getKills() / ((kit.getKillsNeeded() + kit.getKills()))) * 100) + "%" + " &7(&e" + (kit.getKillsNeeded() - kit.getKills()) + " Kills Needed&7)"));
		String prog = "||||||||||||||||||||||||||||||||||||||||||||||||||";
		String n = "";
		int i;
		for (i = 0; i < Math.round((((double)  kit.getKills() / (double) ((kit.getKillsNeeded() + kit.getKills()))) * 100) / 2.0); i++) {
			n += "&a" + prog.charAt(i);
		}
		n += "&8" + prog.substring(i);
		lore.add(ColorText.translate(n));
		titleMeta.setLore(lore);
		title.setItemMeta(titleMeta);
		
		
	    ItemStack cos = new ItemStack(Material.DIAMOND_SWORD);
	    ItemMeta cosM = cos.getItemMeta();
	    cosM.setDisplayName(ColorText.translate("&cCombat Stats"));
		ArrayList<String> coslore = new ArrayList<String>();
		coslore.add(ColorText.translate("&eKills: &f" + kit.getKills()));
		coslore.add(ColorText.translate("&eDeaths: &f" + kit.getDeaths()));
		coslore.add(ColorText.translate("&eSword Hits: &f" + kit.getSword()));
		coslore.add(ColorText.translate("&eArrows Shot: &f" + kit.getArrows()));
		cosM.setLore(coslore);
	    cos.setItemMeta(cosM);
	    
	    ItemStack oth = new ItemStack(Material.GOLDEN_APPLE);
	    ItemMeta othM = oth.getItemMeta();
	    othM.setDisplayName(ColorText.translate("&cOther Stats"));
		ArrayList<String> othlore = new ArrayList<String>();
		othlore.add(ColorText.translate("&eCurrent Streak: &f" + kit.getStreak()));
		othlore.add(ColorText.translate("&eHighest Streak: &f" + kit.getHighStreak()));
		othlore.add(ColorText.translate("&eKDR: &f" + Math.round((double)kit.getKills() / kit.getDeaths())));
		othlore.add(ColorText.translate(" "));
		othlore.add(ColorText.translate("&eGapples Consumed: &f" + kit.getGaps()));
		othM.setLore(othlore);
	    oth.setItemMeta(othM);
		
	    inv.setItem(11, cos);
		inv.setItem(13, title);
		inv.setItem(15, oth);
		return inv;
	}
	
	public static Inventory statsMenuOther(Player p, OfflinePlayer target, Profile t, KitPlayer kit) {
		Inventory inv = Bukkit.createInventory(p, 27, "Statistics");
		DecimalFormat form = new DecimalFormat("0.0");
		
		ItemStack title = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta titleMeta = (SkullMeta)title.getItemMeta();
		titleMeta.setOwner(target.getName());
		titleMeta.setDisplayName(t.getRank().getColor() + t.getRank().getPrefix() + target.getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ColorText.translate("&eLevel: &f" + kit.getLevel()));
		lore.add(ColorText.translate("&eEXP: &f" + kit.getExp()));
		lore.add(ColorText.translate("&eCoins: &f" + kit.getCoins()));
		lore.add(ColorText.translate(" "));
		lore.add(ColorText.translate("&eLevel Progress: &a" + form.format(((double) kit.getKills() / kit.getKillsNeeded()) * 100) + "%" + " &7(&e" + (kit.getKillsNeeded() - kit.getKills()) + " Kills Needed&7)"));
		String prog = "||||||||||||||||||||||||||||||||||||||||||||||||||";
		String n = "";
		int i;
		for (i = 0; i < Math.round((((double) kit.getKills() / kit.getKillsNeeded()) * 100) / 2.0); i++) {
			n += "&a" + prog.charAt(i);
		}
		n += "&8" + prog.substring(i);
		lore.add(ColorText.translate(n));
		titleMeta.setLore(lore);
		title.setItemMeta(titleMeta);
		
		
	    ItemStack cos = new ItemStack(Material.DIAMOND_SWORD);
	    ItemMeta cosM = cos.getItemMeta();
	    cosM.setDisplayName(ColorText.translate("&cCombat Stats"));
		ArrayList<String> coslore = new ArrayList<String>();
		coslore.add(ColorText.translate("&eKills: &f" + kit.getKills()));
		coslore.add(ColorText.translate("&eDeaths: &f" + kit.getDeaths()));
		coslore.add(ColorText.translate("&eSword Hits: &f" + kit.getSword()));
		coslore.add(ColorText.translate("&eArrows Shot: &f" + kit.getArrows()));
		cosM.setLore(coslore);
	    cos.setItemMeta(cosM);
	    
	    ItemStack oth = new ItemStack(Material.GOLDEN_APPLE);
	    ItemMeta othM = oth.getItemMeta();
	    othM.setDisplayName(ColorText.translate("&cOther Stats"));
		ArrayList<String> othlore = new ArrayList<String>();
		othlore.add(ColorText.translate("&eCurrent Streak: &f" + kit.getStreak()));
		othlore.add(ColorText.translate("&eHighest Streak: &f" + kit.getHighStreak()));
		othlore.add(ColorText.translate("&eKDR: &f" + Math.round((double)kit.getKills() / kit.getDeaths())));
		othlore.add(ColorText.translate(" "));
		othlore.add(ColorText.translate("&eGapples Consumed: &f" + kit.getGaps()));
		othM.setLore(othlore);
	    oth.setItemMeta(othM);
		
	    inv.setItem(11, cos);
		inv.setItem(13, title);
		inv.setItem(15, oth);
		return inv;
	}

}
