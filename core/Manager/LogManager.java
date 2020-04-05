package me.core.Manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Profile.Profile;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class LogManager {
	
	public static void addNote(UUID uuid, String note) {
		Date date = new Date();
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO notes(player, note, date) VALUES (?,?,?)");
				ps.setString(1, uuid.toString());
				ps.setString(2, note);
				ps.setString(3, date.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void getLink(UUID uuid, Player p) {
		OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			Profile targetProfile = new Profile(uuid, false);
			if (!targetProfile.isCreated()) {
				Utils.PLAYER_NOT_FOUND(p, target.getName());
				return;
			}
				
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM notes WHERE player = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					p.sendMessage(ColorText.translate("&cNotes> &e" + target.getName() + " &6has no logs!"));
					return;
				} else {
					TextComponent chat = new TextComponent(ColorText.translate("&cLogs> &6Click here to view &e" + target.getName() + " &6anticheat logs!"));
					chat.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://vendettapvp.org/logs/?player=" + target.getUniqueId() ) );
					chat.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click to open link" ).create()));
					p.spigot().sendMessage(chat);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		});
	}
	
}
