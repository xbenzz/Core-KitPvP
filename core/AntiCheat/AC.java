package me.core.AntiCheat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Database.Database;

public class AC {
	
	private int id;
	private UUID player;
	private String reason;
	private String date;
	private boolean active;
	
	public AC(int id, UUID player, String reason, String date, boolean active) {
		this.id = id;
		this.player = player;
		this.reason = reason;
		this.date = date;
		this.active = active;
	}
	
	public AC(UUID player, String reason, String date) {
		this.player = player;
		this.reason = reason;
		this.date = date;
	}
	
	public AC(UUID player) {
		this.player = player;
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM anticheat WHERE player = ? AND active = '1'");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt("ID");
				reason = rs.getString("reason");
				date = rs.getString("date");
				active = rs.getBoolean("active");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO anticheat(player, reason, date, active) VALUES (?,?,?,?)");
				ps.setString(1, player.toString());
				ps.setString(2, reason);
				ps.setString(3, date);
				ps.setBoolean(4, true);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void remove() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE anticheat SET active = '0' WHERE player = ?");
				ps.setString(1, player.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	public int getID() {
		return id;
	}
	
	public UUID getPlayer() {
		return player;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getDate() {
		return date;
	}
	
	public boolean isActive() {
		return active;
	}

}
