package me.core.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Database.Database;

public class Report {
	
	private int ID;
	private UUID player;
	private UUID reporter;
	private String reason;
	private String date;
	
	public Report() {
	}
	
	public Report(int ID) {
		this.ID = ID;
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM reports WHERE ID = ?");
			statement.setInt(1, ID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				player = UUID.fromString(rs.getString("player"));
				reporter = UUID.fromString(rs.getString("reporter"));
				reason = rs.getString("reason");
				date = rs.getString("date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Report(int ID, UUID player, UUID reporter, String reason, String date) {
		this.ID = ID;
		this.player = player;
		this.reporter = reporter;
		this.reason = reason;
		this.date = date;
	}
	
	public ArrayList<Report> getReports() {
		ArrayList<Report> rp = new ArrayList<Report>();
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM reports");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				rp.add(new Report(rs.getInt("ID"), UUID.fromString(rs.getString("player")), UUID.fromString(rs.getString("reporter")), rs.getString("reason"), rs.getString("date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rp;
	}
	
	public void delete() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {     				
				PreparedStatement statement = Database.getConnection().prepareStatement("DELETE FROM reports WHERE ID = ?");
				statement.setInt(1, ID);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	public int getID() {
		return ID;
	}

	public UUID getPlayer() {
		return player;
	}
	
	public UUID getReporter() {
		return reporter;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getDate() {
		return date;
	}
	
}

