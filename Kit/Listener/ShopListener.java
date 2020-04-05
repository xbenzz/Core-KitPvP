package me.Kit.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.Kit.Kit;
import me.Kit.User.KitPlayer;
import me.core.Utilities.ColorText;

public class ShopListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals("Shop")) {
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
			KitPlayer p = Kit.getKitManager().getUser(player.getUniqueId()).get();
			
			ItemStack item = e.getCurrentItem();
			int price = Integer.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split("f")[1]));
			
			if (p.getCoins() < price) {
				player.sendMessage(ColorText.translate("&cShop> &6You need at least &e" + price + " Coins &6to purchase this."));
				player.closeInventory();
			} else {
				p.setCoins(p.getCoins() - price);
				player.getInventory().addItem(new ItemStack(item.getType(), item.getAmount()));
				player.sendMessage(ColorText.translate("&cShop> &6Successfully purchased &e" + item.getAmount() + "x &7- " + item.getItemMeta().getDisplayName() + "&6."));
				player.closeInventory();
			}
		}
	}

}
