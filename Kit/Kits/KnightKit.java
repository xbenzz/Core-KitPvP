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

public class KnightKit extends Kits {
	
	public KnightKit() {
		super("Knight", 100, false, 0);
		this.setArmor(getArmor());
		this.setItems(getItems());
		this.setIcon(getIcon());
		this.setPotions(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 1000000, 0)));
	}
	
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta hMeta = icon.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&bKnight Kit"));
		icon.setItemMeta(hMeta);
		return icon;
	}

	public ItemStack[] getArmor() {
		ItemStack[] stack = new ItemStack[4];
		
		ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
		ItemMeta hMeta = helmet.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&bKnight Helmet"));
		hMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		helmet.setItemMeta(hMeta);
		stack[3] = helmet;
		
		ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemMeta cMeta = chestplate.getItemMeta();
		cMeta.setDisplayName(ColorText.translate("&bKnight Chestplate"));
		cMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		chestplate.setItemMeta(cMeta);
		stack[2] = chestplate;
		
		ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
		ItemMeta lMeta = leggings.getItemMeta();
		lMeta.setDisplayName(ColorText.translate("&bKnight Leggings"));
		lMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		leggings.setItemMeta(lMeta);
		stack[1] = leggings;
		
		ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
		ItemMeta bMeta = boots.getItemMeta();
		bMeta.setDisplayName(ColorText.translate("&bKnight Boots"));
		bMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		boots.setItemMeta(bMeta);
		stack[0] = boots;
		
		return stack;
	}
	
	public ItemStack[] getItems() {
		ItemStack[] stack = new ItemStack[4];
		
		ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta sMeta = sword.getItemMeta();
		sMeta.setDisplayName(ColorText.translate("&bKnight Sword"));
		sMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		sMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		sMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		sword.setItemMeta(sMeta);
	    stack[0] = sword;
	    
	    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 5);
	    stack[1] = apple;
	    
	    ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1, (short)1);
	    stack[2] = gapple;
	    
	    ItemStack healing = new ItemStack(Material.POTION, 10, (short)16389);
	    PotionMeta pMeta = (PotionMeta) healing.getItemMeta();
	    pMeta.setMainEffect(PotionEffectType.HEAL);
	  	healing.setItemMeta(pMeta);
	  	stack[3] = healing;
		
		return stack;
	}
	
	
}
