package me.core.Utilities;

import org.bukkit.ChatColor;

import me.Kit.Kit;

public class ColorText {
	
  public static String translate(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
	public String ipbanMessage(String reason) {
	    String kicked = ChatColor.RED + "Your IP is permanently suspended from &d&lVendettaPvP" + ChatColor.RED + "!";
	    String kicked2 = ChatColor.GRAY + "* Reason: " + ChatColor.YELLOW + reason;
	    String kicked5 = " ";
	    String kicked6 = ChatColor.DARK_GREEN + "Appeal at " + ChatColor.GREEN + Kit.getPlugin().getConfig().getString("Appeal-Link");
	    String s = kicked + "\n" + kicked2 + "\n" + kicked5 + "\n" + kicked6;
		return translate(s);
	}
	
	public String banMessage(String reason, String time) {
	    String kicked = ChatColor.RED + "Your account is suspended from &d&lVendettaPvP" + ChatColor.RED + "!";
	    String kicked2 = ChatColor.GRAY + "* Reason: " + ChatColor.YELLOW + reason;
	    String kicked3 = ChatColor.GRAY + "* Duration: " + ChatColor.YELLOW + time;
	    String kicked5 = " ";
	    String kicked6 = ChatColor.DARK_GREEN + "Unfairly banned? Appeal at " + ChatColor.GREEN + Kit.getPlugin().getConfig().getString("Appeal-Link");
	    String s = kicked + "\n" + kicked2 + "\n" + kicked3 + "\n" + kicked5 + "\n" + kicked6;
		return translate(s);
	}
	
	public String kickMessage(String reason) { 
	    String kickeds = ChatColor.RED + "You were kicked from the network!";
	    String kickeds2 = ChatColor.YELLOW + reason;
	    String s = kickeds + "\n" + kickeds2;
		return s;
	}
	
	public String muteMessage(String reason, String time) {
		  String kick = "&cPunish> &6You are muted because &e" + reason + "&6for &a" + time + "&6.";
	      return translate(kick);
	}
	
	public String helpMessage() {
	    String kicked = "&4&l]&c&m------------&4&l[ &d&lVendettaPvP &4&l]&c&m------------&4&l[";
	    String kicked2 = "  &eWebsite: &fwww.VendettaPvP.org";
	    String kicked3 = "  &eDiscord: &fhttps://discord.gg/unBkXur";
	    String kicked5 = "  &eStore: &fstore.VendettaPvP.org";
	    String kicked6 = "&4&l]&c&m---------------------------------------&4&l[";
	    String s = kicked + "\n" + kicked2 + "\n" + kicked3 + "\n" + kicked5 + "\n" + kicked6;
		return translate(s);
	}
  
}
