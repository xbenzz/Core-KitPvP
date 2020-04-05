package me.Kit.Kits;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.core.Utilities.ColorText;

public class StarterKit extends Kits {
	
	public StarterKit() {
		super("Starter", 0, false, 0);
		this.setIcon(getIcon());
		this.setArmor(new ItemStack[] {new ItemStack(Material.CHAINMAIL_BOOTS, 1), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), new ItemStack(Material.CHAINMAIL_HELMET, 1)});
		this.setItems(new ItemStack[] {new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.GOLDEN_APPLE, 5), new ItemStack(Material.POTION, 5, (short)16389)});
		this.setPotions(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 1000000, 0)));
	}
	
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta hMeta = icon.getItemMeta();
		hMeta.setDisplayName(ColorText.translate("&7Starter Kit"));
		icon.setItemMeta(hMeta);
		return icon;
	}

}
