package me.core.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import me.core.Database.Database;

public class ReportLog {
	
	private int ID;
	private UUID player;
	private UUID reporter;
	private String reason;
	private String date;
	private String log;
	
	public ReportLog(UUID player) {
		this.player = player;
	}
	
	public ReportLog(int ID, UUID player, String date) {
		this.ID = ID;
		this.player = player;
		this.date = date;
	}
	
	public ReportLog(int ID, UUID player, UUID reporter, String reason, String date, String log) {
		this.ID = ID;
		this.player = player;
		this.reporter = reporter;
		this.reason = reason;
		this.date = date;
		this.log = log;
	}
	
	public ArrayList<ReportLog> getReportLogs() {
		ArrayList<ReportLog> rp = new ArrayList<ReportLog>();
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM reportsLogs WHERE player = ?");
			statement.setString(1, player.toString());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				rp.add(new ReportLog(rs.getInt("ID"), UUID.fromString(rs.getString("player")), UUID.fromString(rs.getString("reporter")), rs.getString("reason"), rs.getString("date"), rs.getString("log")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rp;
	}
	
	public String getLogLink() {
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM reportsLogs WHERE player = ? AND date = ? AND ID = ?");
			statement.setString(1, player.toString());
			statement.setString(2, date);
			statement.setInt(3, ID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return rs.getString("log");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "null";
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
	
	public String getLog() {
		return log;
	}
}