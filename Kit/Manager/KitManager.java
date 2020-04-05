package me.Kit.Manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Kit.Kit;
import me.Kit.Kits.ArcherKit;
import me.Kit.Kits.EliteKit;
import me.Kit.Kits.Kits;
import me.Kit.Kits.KnightKit;
import me.Kit.Kits.MVPKit;
import me.Kit.Kits.StarterKit;
import me.Kit.Kits.VIPKit;

public class KitManager { 
	
	public static ArrayList<ItemStack> getFilter() {
		ArrayList<ItemStack> filter = new ArrayList<ItemStack>();
		filter.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		filter.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		filter.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		filter.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		filter.add(new ItemStack(Material.STONE_SWORD, 1));
		return filter;
	}
	
	public static ArrayList<Kits> getAllKits() {
		ArrayList<Kits> allKits = new ArrayList<Kits>();
		allKits.add(new StarterKit());
		allKits.add(new KnightKit());
		allKits.add(new ArcherKit());
		allKits.add(new VIPKit());
		allKits.add(new EliteKit());
		allKits.add(new MVPKit());
		return allKits;
	}
	
	public static void kitOnJoin(Player p) {
		Kits kit = new StarterKit();
		if (p.getInventory().getContents().length == 0) {
		    p.getInventory().setArmorContents(kit.getArmor());
		    p.getInventory().setContents(kit.getItems());
		}
		p.addPotionEffects(kit.getEffects());
	}
	
	public static void giveKit(Player p, Kits kit) {
		p.getInventory().setArmorContents(kit.getArmor());
		p.getInventory().setContents(kit.getItems());
		p.addPotionEffects(kit.getEffects());
	}
	
	public static void putKit(Player p, Kits kit) {
		p.getInventory().addItem(kit.getArmor());
		p.getInventory().addItem(kit.getItems());
		p.addPotionEffects(kit.getEffects());
	}
	
	public static void saveOnRun() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Kit.getPlugin(), () -> {
			Kit.getKitManager().runningSave();
		}, 0L, 36000L);
	}
	
}
