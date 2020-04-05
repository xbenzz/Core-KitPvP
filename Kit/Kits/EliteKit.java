package me.Kit.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.core.Utilities.ColorText;

public class EliteKit extends Kits {
	
	public EliteKit() {
		super("Elite", 400, true, 600);
		this.setArmor(getArmor());
		this.setItems(getItems());
		this.setIcon(getIcon());
		this.setPotions(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 1000000, 0)));
	}
	
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.EMERALD_ORE, 1);
		ItemMeta hMeta = icon.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&bElite Kit"));
		icon.setItemMeta(hMeta);
		return icon;
	}

	public ItemStack[] getArmor() {
		ItemStack[] stack = new ItemStack[4];
		
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta hMeta = helmet.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&bElite Helmet"));
		hMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		helmet.setItemMeta(hMeta);
		stack[3] = helmet;
		
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta cMeta = chestplate.getItemMeta();
		cMeta.setDisplayName(ColorText.translate("&bElite Chestplate"));
		cMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		chestplate.setItemMeta(cMeta);
		stack[2] = chestplate;
		
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta lMeta = leggings.getItemMeta();
		lMeta.setDisplayName(ColorText.translate("&bElite Leggings"));
		lMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		leggings.setItemMeta(lMeta);
		stack[1] = leggings;
		
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta bMeta = boots.getItemMeta();
		bMeta.setDisplayName(ColorText.translate("&bElite Boots"));
		bMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		boots.setItemMeta(bMeta);
		stack[0] = boots;
		
		return stack;
	}
	
	public ItemStack[] getItems() {
		ItemStack[] stack = new ItemStack[6];
		
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta vMeta = sword.getItemMeta();
		vMeta.setDisplayName(ColorText.translate("&bElite Sword"));
		vMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
		vMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		sword.setItemMeta(vMeta);
	    stack[0] = sword;
		
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta sMeta = bow.getItemMeta();
		sMeta.setDisplayName(ColorText.translate("&bElite Bow"));
		sMeta.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
		sMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		sMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
		sMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		bow.setItemMeta(sMeta);
	    stack[1] = bow;
	    
	    ItemStack arrow = new ItemStack(Material.ARROW, 1);
	    stack[2] = arrow;
	    
	    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 5);
	    stack[3] = apple;
	    
	    ItemStack healing = new ItemStack(Material.POTION, 4, (short)16389);
	    PotionMeta pMeta = (PotionMeta) healing.getItemMeta();
	    pMeta.setMainEffect(PotionEffectType.HEAL);
	  	healing.setItemMeta(pMeta);
	  	stack[4] = healing;
	  	
	    ItemStack stren = new ItemStack(Material.POTION, 4, (short)16393);
	    PotionMeta srMeta = (PotionMeta) stren.getItemMeta();
	    srMeta.setMainEffect(PotionEffectType.INCREASE_DAMAGE);
	  	stren.setItemMeta(srMeta);
	  	stack[5] = stren;
		
		return stack;
	}

}
