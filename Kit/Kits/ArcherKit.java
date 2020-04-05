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

public class ArcherKit extends Kits {
	
	public ArcherKit() {
		super("Archer", 50, false, 0);
		this.setArmor(getArmor());
		this.setItems(getItems());
		this.setIcon(getIcon());
		this.setPotions(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 1000000, 0)));
	}
	
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.BOW, 1);
		ItemMeta hMeta = icon.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&6Archer Kit"));
		icon.setItemMeta(hMeta);
		return icon;
	}

	public ItemStack[] getArmor() {
		ItemStack[] stack = new ItemStack[4];
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemMeta hMeta = helmet.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&6Archer Helmet"));
		hMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		helmet.setItemMeta(hMeta);
		stack[3] = helmet;
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemMeta cMeta = chestplate.getItemMeta();
		cMeta.setDisplayName(ColorText.translate("&6Archer Chestplate"));
		cMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		chestplate.setItemMeta(cMeta);
		stack[2] = chestplate;
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemMeta lMeta = leggings.getItemMeta();
		lMeta.setDisplayName(ColorText.translate("&6Archer Leggings"));
		lMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		leggings.setItemMeta(lMeta);
		stack[1] = leggings;
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		ItemMeta bMeta = boots.getItemMeta();
		bMeta.setDisplayName(ColorText.translate("&6Archer Boots"));
		bMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		boots.setItemMeta(bMeta);
		stack[0] = boots;
		
		return stack;
	}
	
	public ItemStack[] getItems() {
		ItemStack[] stack = new ItemStack[4];
		
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta sMeta = bow.getItemMeta();
		sMeta.setDisplayName(ColorText.translate("&6Archer Bow"));
		sMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
		sMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		sMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		bow.setItemMeta(sMeta);
	    stack[0] = bow;
	    
	    ItemStack arrow = new ItemStack(Material.ARROW, 1);
	    stack[1] = arrow;
	    
	    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 5);
	    stack[2] = apple;
	    
	    ItemStack healing = new ItemStack(Material.POTION, 10, (short)16389);
	    PotionMeta pMeta = (PotionMeta) healing.getItemMeta();
	    pMeta.setMainEffect(PotionEffectType.HEAL);
	  	healing.setItemMeta(pMeta);
	  	stack[3] = healing;
		
		return stack;
	}

}
