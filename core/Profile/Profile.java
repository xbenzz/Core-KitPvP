package me.core.Profile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Rank.Rank;

public class Profile {
	
	private UUID uuid;
	private String username;
	private Rank rank;
	private boolean PM;
  
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	
	public Profile(UUID uuid, boolean lookup) {
		if (lookup) {
			this.uuid = uuid;
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					this.username = rs.getString("Username");
					this.PM = rs.getBoolean("TogglePM");
					this.rank = Rank.getRankOrDefault(rs.getString("Rank"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			this.uuid = uuid;
		}
	}

	public boolean isCreated() {
		try {
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID = ?");
			statement.setString(1, uuid.toString());
			ResultSet r = statement.executeQuery();
			return r.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
  
	public boolean hasIP(String ip) {
		try {		
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerIP WHERE UUID = ? AND Address = ?");
			statement.setString(1, uuid.toString());
			statement.setString(2, ip);
			ResultSet r = statement.executeQuery();
			return r.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
  
	public void logIP(String ip) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {		
				PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO playerIP(UUID, Address, Date) VALUES (?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, ip);
				statement.setString(3, format.format(now));
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
  
	public void createProfile() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO playerData(UUID, Username, TogglePM, Rank) VALUES (?,?,?,?)");
			    statement.setString(1, String.valueOf(uuid));
			    statement.setString(2, "");
			    statement.setBoolean(3, true);
			    statement.setString(4, Rank.DEFAULT.toString());
			    statement.executeUpdate();
			 } catch (SQLException e) {
			    e.printStackTrace();
			 }
		});
	}
  
	public void setSQLUsername(String s) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE playerData SET Username = ? WHERE UUID = ?");
				ps.setString(1, s);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
  
	public void setSQLMessages(boolean s) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE playerData SET TogglePM = ? WHERE UUID = ?");
				ps.setBoolean(1, s);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
  	}
  
	public void setSQLRank(Rank rank) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE playerData SET Rank = ? WHERE UUID = ?");
				ps.setString(1, rank.toString());
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
  
	public long getTime() {
		try {
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerTime WHERE UUID = ?");
			statement.setString(1, uuid.toString());
			ResultSet rs = statement.executeQuery();
			rs.next();
			return rs.getLong("time");
		} catch (SQLException e) {
			return 0;
		}
	}
  
	public void addLog(String server, String message, String date, String type) {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {     
				  PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO chat(server, player, message, date, time, type) VALUES (?,?,?,?,?,?)");
				  statement.setString(1, server);
				  statement.setString(2, uuid.toString());
				  statement.setString(3, message);
				  statement.setString(4, date);
				  statement.setString(5, format.format(d));
				  statement.setString(6, type);
				  statement.executeUpdate();
			  } catch (SQLException e) {
				  e.printStackTrace();
			  }
		});
	}
	

	@Override
	public String toString() {
		return "Profile(Name:" + username + ", Rank:" + rank + ", TogglePM:" + PM + ")";
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	public void setMessages(boolean pm) {
		this.PM = pm;
	}
	
	public UUID getUUID () {
		return uuid;
	}
  
	public String getUsername() {
		return username;
	}
  
	public boolean hasMessages() {
		return PM;
	}
  
	public Rank getRank() {
		return rank;
	}
	
    public void save() {
        Kit.getProfileManager().saveUser(this);
    }

    public void load() {
    	Kit.getProfileManager().loadUser(uuid);
    }
  
	public boolean isDonator() {
		return (getRank().isAboveOrEqual(Rank.VIP)) && (getRank().isBelowOrEqual(Rank.MVP));
	}
  
	public boolean isMedia() {
		return getRank().isAboveOrEqual(Rank.YT);
	}
  
}

