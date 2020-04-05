package me.Kit.Manager;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.Kit.Kits.Kits;
import me.core.Utilities.ColorText;

public class GUIs {
	
	public Inventory botKits(Player p, String mode) {
		Inventory inv = Bukkit.createInventory(p, 27, "Bot Kits: " + mode); 
		
		int i = 10;
		for (Kits k : KitManager.getAllKits()) {
			ItemStack item = k.getIcon();
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(meta);
	
			inv.setItem(i, item);
			i +=2;
		}
		
		return inv;
	}
	
	public Inventory kits(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "Kits");
		
		int i = 10;
		for (Kits k : KitManager.getAllKits()) {
			ItemStack item = k.getIcon();
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			if (k.getCost() > 0)
				lore.add(ColorText.translate("&ePrice: &f" + k.getCost()));
			if (k.getTime() > 0) 
				lore.add(ColorText.translate("&eLimit: &f10 Minutes"));
			meta.setLore(lore);
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(meta);
			
			inv.setItem(i, item);
			i +=2;
		}
		return inv;
	}
	
	public Inventory botGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "Bot Duels"); 
		
	    ItemStack easy = new ItemStack(Material.WOOD_SWORD);
	    ItemMeta easyim = easy.getItemMeta();
	    easyim.setDisplayName(ColorText.translate("&eMode: &fEasy"));
	    easyim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    easyim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    easy.setItemMeta(easyim);
	    
	    ItemStack normal = new ItemStack(Material.GOLD_SWORD);
	    ItemMeta normalim = normal.getItemMeta();
	    normalim.setDisplayName(ColorText.translate("&eMode: &fNormal"));
	    normalim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    normalim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    normal.setItemMeta(normalim);
	    
	    ItemStack hard = new ItemStack(Material.IRON_SWORD);
	    ItemMeta hardim = hard.getItemMeta();
	    hardim.setDisplayName(ColorText.translate("&eMode: &fHard"));
	    hardim.setLore(Arrays.asList(ColorText.translate("&eRank Required: &bElite")));
	    hardim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    hardim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    hard.setItemMeta(hardim);
	    
	    ItemStack hacker = new ItemStack(Material.DIAMOND_SWORD);
	    ItemMeta hackerim = hacker.getItemMeta();
	    hackerim.setDisplayName(ColorText.translate("&eMode: &fHacker"));
	    hackerim.setLore(Arrays.asList(ColorText.translate("&eRank Required: &bElite")));
	    hackerim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    hackerim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    hacker.setItemMeta(hackerim);
	    
	    ItemStack info = new ItemStack(Material.PAPER);
	    ItemMeta infoim = info.getItemMeta();
	    infoim.setDisplayName(ChatColor.GOLD + "Information");
	    infoim.setLore(Arrays.asList(
	    		ColorText.translate("&8* &fYou can fight a virtual bot without it effecting your Statistics"), 
	    		ColorText.translate("&8* &fIf you have &dMVP &frank, you can select a custom kit.")));
	    infoim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    infoim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    info.setItemMeta(infoim);
		
	    inv.setItem(10, easy);
	    inv.setItem(12, normal);
	    inv.setItem(14, hard);
	    inv.setItem(16, hacker);
	    inv.setItem(4, info);
		return inv;
	}
	
	public Inventory shop(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "Shop");
		
	    ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
	    ItemMeta ironSwordim = ironSword.getItemMeta();
	    ironSwordim.setDisplayName(ChatColor.RED + "Iron Sword");
	    ironSwordim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f20")));
	    ironSwordim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    ironSwordim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    ironSword.setItemMeta(ironSwordim);
	    
	    ItemStack Helmet = new ItemStack(Material.IRON_HELMET);
	    ItemMeta Helmetim = Helmet.getItemMeta();
	    Helmetim.setDisplayName(ChatColor.RED + "Iron Helmet");
	    Helmetim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f30")));
	    Helmetim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    Helmetim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    Helmet.setItemMeta(Helmetim);
	    
	    ItemStack ironChest = new ItemStack(Material.IRON_CHESTPLATE);
	    ItemMeta ironChestim = ironChest.getItemMeta();
	    ironChestim.setDisplayName(ChatColor.RED + "Iron Chestplate");
	    ironChestim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f60")));
	    ironChestim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    ironChestim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    ironChest.setItemMeta(ironChestim);
	    
	    ItemStack ironLeg = new ItemStack(Material.IRON_LEGGINGS);
	    ItemMeta ironLegim = ironLeg.getItemMeta();
	    ironLegim.setDisplayName(ChatColor.RED + "Iron Leggings");
	    ironLegim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f50")));
	    ironLegim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    ironLegim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    ironLeg.setItemMeta(ironLegim);
	    
	    ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
	    ItemMeta ironBootsim = ironBoots.getItemMeta();
	    ironBootsim.setDisplayName(ChatColor.RED + "Iron Boots");
	    ironBootsim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f30")));
	    ironBootsim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    ironBootsim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    ironBoots.setItemMeta(ironBootsim);
	    
	    
	    ItemStack diaSword = new ItemStack(Material.DIAMOND_SWORD);
	    ItemMeta diaSwordim = diaSword.getItemMeta();
	    diaSwordim.setDisplayName(ChatColor.RED + "Diamond Sword");
	    diaSwordim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f40")));
	    diaSwordim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    diaSwordim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    diaSword.setItemMeta(diaSwordim);
	    
	    ItemStack diaHelmet = new ItemStack(Material.DIAMOND_HELMET);
	    ItemMeta diaHelmetim = diaHelmet.getItemMeta();
	    diaHelmetim.setDisplayName(ChatColor.RED + "Diamond Helmet");
	    diaHelmetim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f60")));
	    diaHelmetim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    diaHelmetim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    diaHelmet.setItemMeta(diaHelmetim);
	    
	    ItemStack diaChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
	    ItemMeta diaChestim = diaChest.getItemMeta();
	    diaChestim.setDisplayName(ChatColor.RED + "Diamond Chestplate");
	    diaChestim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f120")));
	    diaChestim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    diaChestim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    diaChest.setItemMeta(diaChestim);
	    
	    ItemStack diaLeg = new ItemStack(Material.DIAMOND_LEGGINGS);
	    ItemMeta diaLegim = diaLeg.getItemMeta();
	    diaLegim.setDisplayName(ChatColor.RED + "Diamond Leggings");
	    diaLegim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f100")));
	    diaLegim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    diaLegim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    diaLeg.setItemMeta(diaLegim);
	    
	    ItemStack diaBoots = new ItemStack(Material.DIAMOND_BOOTS);
	    ItemMeta diaBootsim = diaBoots.getItemMeta();
	    diaBootsim.setDisplayName(ChatColor.RED + "Diamond Boots");
	    diaBootsim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f60")));
	    diaBootsim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    diaBootsim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    diaBoots.setItemMeta(diaBootsim);
	    
	    ItemStack Bow = new ItemStack(Material.BOW);
	    ItemMeta Bowim = Bow.getItemMeta();
	    Bowim.setDisplayName(ChatColor.RED + "Bow");
	    Bowim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f10")));
	    Bowim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    Bowim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    Bow.setItemMeta(Bowim);
	    
	    ItemStack arrow = new ItemStack(Material.ARROW, 64);
	    ItemMeta arrowim = arrow.getItemMeta();
	    arrowim.setDisplayName(ChatColor.RED + "Arrows");
	    arrowim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f5")));
	    arrowim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    arrowim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    arrow.setItemMeta(arrowim);
	    
	    ItemStack gap = new ItemStack(Material.GOLDEN_APPLE, 1);
	    ItemMeta gapim = gap.getItemMeta();
	    gapim.setDisplayName(ChatColor.RED + "Golden Apple");
	    gapim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f10")));
	    gapim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    gapim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    gap.setItemMeta(gapim);
	    
	    ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1, (short)1);
	    ItemMeta gappleim = gapple.getItemMeta();
	    gappleim.setDisplayName(ChatColor.RED + "Golden Apple");
	    gappleim.setLore(Arrays.asList(ColorText.translate("&eCoins: &f25")));
	    gappleim.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    gappleim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    gapple.setItemMeta(gappleim);
	    
	    inv.setItem(3, Helmet);
	    inv.setItem(4, diaHelmet);
	    inv.setItem(6, Bow);
	    inv.setItem(8, gap);
	    
	    inv.setItem(12, ironChest);
	    inv.setItem(13, diaChest); 
	    inv.setItem(15, arrow);
	    inv.setItem(17, gapple);
	    
	    inv.setItem(21, ironLeg);
	    inv.setItem(22, diaLeg);
	    inv.setItem(24, ironSword);
	    
	    inv.setItem(30, ironBoots);
	    inv.setItem(31, diaBoots);
	    inv.setItem(33, diaSword);
	    return inv; 
	}
	

}
