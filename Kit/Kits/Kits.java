package me.Kit.Kits;

import java.util.Collection;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kits {
	
	private String name;
	private ItemStack icon;
	private int cost;
	private ItemStack[] items;
	private ItemStack[] armor;
	private Collection<PotionEffect> effects;
	private boolean limit;
	private long time;

	public Kits(String name, int cost, boolean limit, long time) {
		this.name = name;
		this.cost = cost;
		this.limit = limit;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	
	public ItemStack[] getArmor() {
		return armor;
	}
	
	public Collection<PotionEffect> getEffects() {
		return effects;
	}
	
	public int getCost() {
		return cost;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setIcon(ItemStack i) {
		this.icon = i;
	}
	
	public void setItems(ItemStack[] i) {
		this.items = i;
	}
	
	public void setArmor(ItemStack[] i) {
		this.armor = i;
	}
	
	public void setPotions(Collection<PotionEffect> i) {
		this.effects = i;
	}
	
	public void setTime(long t) {
		this.time = t;
	}
	

}
