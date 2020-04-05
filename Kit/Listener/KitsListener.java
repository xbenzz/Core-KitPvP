package me.Kit.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.Kit.Kit;
import me.Kit.Kits.ArcherKit;
import me.Kit.Kits.Kits;
import me.Kit.Kits.KnightKit;
import me.Kit.Kits.StarterKit;
import me.Kit.Kits.VIPKit;
import me.Kit.User.KitPlayer;
import me.core.Utilities.ColorText;
import me.core.Utilities.Cooldowns;
import me.core.Utilities.Utils;

public class KitsListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("Kits")) {
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
			KitPlayer kit = Kit.getKitManager().getUser(player.getUniqueId()).get();
			
			if (e.getSlot() == 10) {
				Kits k = new StarterKit();
				player.getInventory().addItem(k.getArmor());
				player.getInventory().addItem(k.getItems());
				player.addPotionEffects(k.getEffects());
				player.closeInventory();
			} else if (e.getSlot() == 12) {
				Kits k = new KnightKit();
				if (kit.getCoins() < k.getCost()) {
					player.sendMessage(ColorText.translate("&cKits> &6You do not have enough coins."));
					player.closeInventory();
				} else {
					player.getInventory().addItem(k.getArmor());
					player.getInventory().addItem(k.getItems());
					player.addPotionEffects(k.getEffects());
					kit.setCoins(kit.getCoins() - k.getCost());
					player.closeInventory();
				}
			} else if (e.getSlot() == 14) {
				Kits k = new ArcherKit();
				if (kit.getCoins() < k.getCost()) {
					player.sendMessage(ColorText.translate("&cKits> &6You do not have enough coins."));
					player.closeInventory();
				} else {
					player.getInventory().addItem(k.getArmor());
					player.getInventory().addItem(k.getItems());
					player.addPotionEffects(k.getEffects());
					kit.setCoins(kit.getCoins() - k.getCost());
					player.closeInventory();
				}
			} else if (e.getSlot() == 16) {
				Kits k = new VIPKit();
				if (kit.getCoins() < k.getCost()) {
					player.sendMessage(ColorText.translate("&cKits> &6You do not have enough coins."));
					player.closeInventory();
				} else if (Cooldowns.isOnCooldown("vip_kit", player)) {
					player.sendMessage(ColorText.translate("&cKits> &6You must wait " + Utils.formatLong(Cooldowns.getCooldownLong("vip_kit", player))));
					player.closeInventory();
				} else {
					player.getInventory().addItem(k.getArmor());
					player.getInventory().addItem(k.getItems());
					player.addPotionEffects(k.getEffects());
					Cooldowns.addCooldown("vip_kit", player, 600);
					kit.setCoins(kit.getCoins() - k.getCost());
					player.closeInventory();
				}
			}
		}
	}

}
