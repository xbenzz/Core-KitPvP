package me.core.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import me.Kit.Kit;
import me.core.Utilities.ColorText;
import me.core.Utilities.TimeUtils;

public class InspectManager {
	
	public static void openRegularMenu(final Player player, Player target) {
		final Inventory inventory = Bukkit.getServer().createInventory(null, 54, ColorText.translate("&6Inspecting: &f" + target.getName()));
    
		Bukkit.getScheduler().runTaskTimer(Kit.getPlugin(), () -> {
			PlayerInventory playerInventory = target.getInventory();
        
			ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
			ItemMeta orangeGlassPaneim = orangeGlassPane.getItemMeta();
			orangeGlassPaneim.setDisplayName("");
			orangeGlassPane.setItemMeta(orangeGlassPaneim);
	    
			ItemStack whiteGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta whiteGlassPaneim = whiteGlassPane.getItemMeta();
			whiteGlassPaneim.setDisplayName("");
			whiteGlassPane.setItemMeta(whiteGlassPaneim);
        
			inventory.setContents(playerInventory.getContents());
			inventory.setItem(47, playerInventory.getHelmet());
			inventory.setItem(48, playerInventory.getChestplate());
			inventory.setItem(49, playerInventory.getLeggings());
			inventory.setItem(50, playerInventory.getBoots());
			inventory.setItem(49, playerInventory.getItemInHand());
			for (int i = 36; i <= 44; i++) {
				inventory.setItem(i, whiteGlassPane);
			}
		}, 0L, 5L);
    
		player.openInventory(inventory);
	}
	
	public static void openInspectionMenu(final Player player, Player target) {
		final Inventory inventory = Bukkit.getServer().createInventory(null, 54, ColorText.translate("&6Inspecting: &f" + target.getName()));
    
		Bukkit.getScheduler().runTaskTimer(Kit.getPlugin(), () -> {
			PlayerInventory playerInventory = target.getInventory();
			
			ItemStack speckledMelon = new ItemStack(Material.SPECKLED_MELON, (int)target.getHealth());
			ItemMeta speckledMelonMeta = speckledMelon.getItemMeta();
			speckledMelonMeta.setDisplayName(ColorText.translate("&aHealth"));
			speckledMelon.setItemMeta(speckledMelonMeta);
			
			ItemStack cookedBeef = new ItemStack(Material.COOKED_BEEF, target.getFoodLevel());
			ItemMeta cookedBeefMeta = cookedBeef.getItemMeta();
			cookedBeefMeta.setDisplayName(ColorText.translate("&aHunger"));
			cookedBeef.setItemMeta(cookedBeefMeta);
			
			ItemStack brewingStand = new ItemStack(Material.BREWING_STAND_ITEM, target.getPlayer().getActivePotionEffects().size());
			ItemMeta brewingStandMeta = brewingStand.getItemMeta();
			brewingStandMeta.setDisplayName(ColorText.translate("&aActive Potion Effects"));
			ArrayList<String> brewingStandLore = new ArrayList<String>();
			for (PotionEffect potionEffect : target.getPlayer().getActivePotionEffects()) {
				String effectName = potionEffect.getType().getName();
				int effectLevel = potionEffect.getAmplifier();
				effectLevel++;
				brewingStandLore.add(ColorText.translate("&e" + WordUtils.capitalizeFully(effectName).replace("_", " ") + " " + effectLevel + "&7: &c" + TimeUtils.IntegerCountdown.setFormat(Integer.valueOf(potionEffect.getDuration() / 20))));
			}
			brewingStandMeta.setLore(brewingStandLore);
			brewingStand.setItemMeta(brewingStandMeta);
			
			ItemStack compass = new ItemStack(Material.COMPASS, 1);
			ItemMeta compassMeta = compass.getItemMeta();
			compassMeta.setDisplayName(ColorText.translate("&aPlayer Location"));
			String[] s = {ColorText.translate("&eCoords"), ColorText.translate("  &eX&7: &c" + target.getLocation().getBlockX()), ColorText.translate("  &eY&7: &c" + target.getLocation().getBlockY()), ColorText.translate("  &eZ&7: &c" + target.getLocation().getBlockZ()) };
			compassMeta.setLore(Arrays.asList(s));
			compass.setItemMeta(compassMeta);
        
			ItemStack orangeGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
			ItemMeta orangeGlassPaneim = orangeGlassPane.getItemMeta();
			orangeGlassPaneim.setDisplayName("");
			orangeGlassPane.setItemMeta(orangeGlassPaneim);
	    
			ItemStack whiteGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta whiteGlassPaneim = whiteGlassPane.getItemMeta();
			whiteGlassPaneim.setDisplayName("");
			whiteGlassPane.setItemMeta(whiteGlassPaneim);
        
			inventory.setContents(playerInventory.getContents());
			inventory.setItem(47, playerInventory.getHelmet());
			inventory.setItem(48, playerInventory.getChestplate());
			inventory.setItem(50, playerInventory.getLeggings());
			inventory.setItem(51, playerInventory.getBoots());
			inventory.setItem(49, playerInventory.getItemInHand());
			inventory.setItem(45, speckledMelon);
			inventory.setItem(46, cookedBeef);
			inventory.setItem(52, brewingStand);
			inventory.setItem(53, compass);
			for (int i = 36; i <= 44; i++) {
				inventory.setItem(i, whiteGlassPane);
			}
		}, 0L, 5L);
    
		player.openInventory(inventory);
	}
  
}
