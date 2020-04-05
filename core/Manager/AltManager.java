package me.core.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Punishment.Punishment;
import me.core.Punishment.Type;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class AltManager {
	
	public static ChatColor getColor(UUID uuid) {
		Punishment p = new Punishment(uuid);
		if (p.isPunished(Type.MUTE) && p.isPunished(Type.BAN)) {
			return ChatColor.LIGHT_PURPLE;
		} else if (p.isPunished(Type.BAN)) {
			return ChatColor.RED;
		} else if (p.isPunished(Type.MUTE)) {
			return ChatColor.YELLOW;
		} else {
			return ChatColor.GREEN;
		}
	}
	
	public static ChatColor getColor(String ip) {
		Punishment ban = new Punishment(ip, Type.IP);
		if (ban.isActive() && ban.getID() != 0) {
			return ChatColor.RED;
		} else {
			return ChatColor.GREEN;
		}
	}
	
	public static boolean checkEvasion(UUID uuid) {
		ArrayList<String> IPs = new ArrayList<String>();
		ArrayList<UUID> IGNs = new ArrayList<UUID>();
		int count = 0;
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerIP WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					IPs.add(rs.getString("Address")); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		
			String ip = IPs.get(0);
		
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerIP WHERE Address = ?");
				ps.setString(1, ip);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					IGNs.add(UUID.fromString(rs.getString("UUID"))); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			for (UUID e : IGNs) {
				if (getColor(e) != ChatColor.GREEN) {
					count++;
				}
			}
			
		return count > 1;		
	}
	
	public static void getIPHistory(String IP, Player admin) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerIP WHERE Address = ?");
				ps.setString(1, IP);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUser> &6No accounts found!"));
				} else {
					admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUser> &6Accounts linked to &e" + IP + "&6:"));
					do {
						UUID p = UUID.fromString(rs.getString("UUID"));
						String date = rs.getString("Date");
						ChatColor color = getColor(UUID.fromString(rs.getString("UUID")));
	              
						String kicked2 = ChatColor.DARK_GRAY + "* " + color + Bukkit.getOfflinePlayer(p).getName() + ChatColor.GRAY + " - " + ChatColor.AQUA + date;
						TextComponent message = new TextComponent(kicked2);
						message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://namemc.com/profile/" + p ) );
	              
						admin.spigot().sendMessage(message);
					} while (rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
    }
	
	public static void getAltHistory(String user, Player admin) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(user);
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerIP WHERE UUID = ?");
				ps.setString(1, player.getUniqueId().toString());
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUser> &6No accounts found!"));
				} else {
					admin.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUser> &6IP's of &e" + player.getName() + "&6:"));
					do {
						String ip = rs.getString("Address");
						String date = rs.getString("Date");
						ChatColor color = getColor(ip);
	              
						String kicked2 = ChatColor.DARK_GRAY + "* " + color + ip + ChatColor.GRAY + " - " + ChatColor.AQUA + date;
						TextComponent message = new TextComponent(kicked2);
	            
						admin.spigot().sendMessage(message);
					} while (rs.next());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		});
	}

}
