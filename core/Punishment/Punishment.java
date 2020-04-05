package me.core.Punishment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Manager.AltManager;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import me.core.Utilities.ColorText;

public class Punishment {
	
	private int id;
	private UUID player;
	private String punisher;
	private String reason;
	private long expire;
	private long time;
	private Type type;
	private Type category;
	private String date;
	private String ip;
	private boolean ipban;
	private boolean active;
	private boolean removed;
	private String removedBy;
	
	public Punishment() {
	}
	
	public Punishment(UUID player) {
		this.player = player;
	}

	public Punishment(int id, UUID player, String punisher, String reason, long expire, long time, Type type, Type category, String date, String ip, boolean ipban, boolean active, boolean removed, String removedBy) {
		this.id = id;
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.time = time;
		this.type = type;
		this.category = category;
		this.date = date;
		this.ip = ip;
		this.ipban = ipban;
		this.active = active;
		this.removed = removed;
		this.removedBy = removedBy;
	}
	
	public Punishment(@Nullable String punisher, @Nullable String reason, Type type, Type category, String date, @Nullable String ip, boolean ipban) {
		this.punisher = punisher;
		this.reason = reason;
		this.expire = -1L;
		this.type = type;
		this.category = category;
		this.date = date;
		this.ip = ip;
		this.ipban = ipban;
	}
	
	public Punishment(UUID player, @Nullable String punisher, @Nullable String reason, long expire, long time, Type type, Type category, String date, boolean active) {
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.time = time;
		this.expire = expire;
		this.type = type;
		this.category = category;
		this.date = date;
		this.active = active;
	}
	
	public Punishment(UUID player, @Nullable String punisher, @Nullable String reason, long expire, Type type, Type category, String date, @Nullable String ip, boolean ipban) {
		this.player = player;
		this.punisher = punisher;
		this.reason = reason;
		this.expire = expire;
		this.type = type;
		this.category = category;
		this.date = date;
		this.ip = ip;
		this.ipban = ipban;
	}
	
	public Punishment(String ip, Type type) {
		this.ip = ip;
		this.type = type;
		try {
			PreparedStatement stat = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE ip = ? AND type = ? AND active = '1'");
			stat.setString(1, ip);
			stat.setString(2, type.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
				punisher = rs.getString("punisher");
				reason = rs.getString("reason");
				category = Type.valueOf(rs.getString("category"));
				expire = rs.getLong("expire");
				time = rs.getLong("time");
				date = rs.getString("date");
				ipban = rs.getBoolean("ipban");
				active = rs.getBoolean("active");
				removed = rs.getBoolean("removed");
				removedBy = rs.getString("removedby");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Punishment(UUID player, Type type) {
		this.player = player;
		this.type = type;
		try {
			PreparedStatement stat = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ? AND type = ? AND active = '1'");
			stat.setString(1, player.toString());
			stat.setString(2, type.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				id = rs.getInt("ID");
				punisher = rs.getString("punisher");
				reason = rs.getString("reason");
				category = Type.valueOf(rs.getString("category"));
				expire = rs.getLong("expire");
				time = rs.getLong("time");
				date = rs.getString("date");
				ip = rs.getString("ip");
				ipban = rs.getBoolean("ipban");
				active = rs.getBoolean("active");
				removed = rs.getBoolean("removed");
				removedBy = rs.getString("removedby");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() {
		final String p = (player == null) ? "" : player.toString();
		final String punishee = (punisher == null) || (punisher.equalsIgnoreCase("")) ? "CONSOLE" : punisher;
		final String banreason = (reason == null) || (reason.equalsIgnoreCase("")) ? "N/A" : reason;
		
	    long duration = 0;
		if (expire == -1) {
			duration = -1;
		} else {
			duration = System.currentTimeMillis() + expire;
		}
		
		final long end = duration;
		if (type == Type.MUTE) { 
			Optional<Profile> op =  Kit.getProfileManager().getUser(player);
			if (op.isPresent())
				Kit.getProfileManager().getMutes().add(new Punishment(player, punisher, banreason, end, expire, type, category, date, true));
		}
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO punishments(player, punisher, reason, expire, time, type, category, date, ip, ipban, active, removed, removedby) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, p);
				ps.setString(2, punishee);
				ps.setString(3, banreason);
				ps.setLong(4, end);
				ps.setLong(5, expire);
				ps.setString(6, type.toString());
				ps.setString(7, category.toString());
				ps.setString(8, date);
				ps.setString(9, ip);
				ps.setBoolean(10, ipban);
				ps.setBoolean(11, true);
				ps.setBoolean(12, false);
				ps.setString(13, null);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (player != null) {
				boolean evasion = AltManager.checkEvasion(player);
				
				Bukkit.getScheduler().runTask(Kit.getPlugin(), () -> {
					OfflinePlayer pla = Bukkit.getOfflinePlayer(player);
					if (evasion) {
						for (Profile pro : Kit.getProfileManager().getUsersAboveOrERank(Rank.MODERATOR)) {
							Player o = Bukkit.getPlayer(pro.getUUID());
							o.sendMessage(ColorText.translate("&cAlt Manager> &e" + pla.getName() + " &6might be ban or mute evading!"));
						}
					}
				});
			}
		});
	}
	
	
	public List<Punishment> getHistory() {
		List<Punishment> punish = new ArrayList<Punishment>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				if (Type.valueOf(rs.getString("type")) != Type.IP)
					punish.add(new Punishment(rs.getInt("ID"), UUID.fromString(rs.getString("player")), rs.getString("punisher"), rs.getString("reason"), Long.valueOf(rs.getLong("expire")), Long.valueOf(rs.getLong("time")), Type.valueOf(rs.getString("type")), Type.valueOf(rs.getString("category")), rs.getString("date"), rs.getString("ip"), rs.getBoolean("ipban"), rs.getBoolean("active"), rs.getBoolean("removed"), rs.getString("removedby")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punish;
	}
	
	public List<Punishment> getIPHistory() {
		List<Punishment> punish = new ArrayList<Punishment>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE ip = ? AND type = ?");
			ps.setString(1, ip);
			ps.setString(2, type.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				punish.add(new Punishment(rs.getInt("ID"), null, rs.getString("punisher"), rs.getString("reason"), Long.valueOf(rs.getLong("expire")), Long.valueOf(rs.getLong("time")), Type.valueOf(rs.getString("type")), Type.valueOf(rs.getString("category")), rs.getString("date"), rs.getString("ip"), rs.getBoolean("ipban"), rs.getBoolean("active"), rs.getBoolean("removed"), rs.getString("removedby")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punish;
	}
	
	public List<Punishment> getAllActive() {
		List<Punishment> punish = new ArrayList<Punishment>();
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE active = '1'");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				if (Type.valueOf(rs.getString("type")) != Type.IP)
					punish.add(new Punishment(rs.getInt("ID"), UUID.fromString(rs.getString("player")), rs.getString("punisher"), rs.getString("reason"), Long.valueOf(rs.getLong("expire")), Long.valueOf(rs.getLong("time")), Type.valueOf(rs.getString("type")), Type.valueOf(rs.getString("category")), rs.getString("date"), rs.getString("ip"), rs.getBoolean("ipban"), rs.getBoolean("active"), rs.getBoolean("removed"), rs.getString("removedby")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punish;
	}
	
	public void remove() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE punishments SET active = '0' WHERE player = ? AND active = '1' AND type = ?");
				ps.setString(1, player.toString());
				ps.setString(2, type.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		Punishment p = Kit.getProfileManager().getMute(player).get();
		Kit.getProfileManager().getMutes().remove(p);
	}
	
	public void removeIPBan() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE punishments SET active = '0' WHERE ip = ? AND active = '1' AND type = ?");
				ps.setString(1, ip);
				ps.setString(2, type.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void clearAll() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM punishments WHERE player = ?");
                   ps.setString(1, player.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void clear(int ID) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("DELETE FROM punishments WHERE ID = ?");
                ps.setInt(1, ID);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public boolean isPunished(Type type) {
		try {
		    PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM punishments WHERE player = ? AND type = ? AND active = '1'");
		    ps.setString(1, player.toString());
		    ps.setString(2, type.toString());
		    ResultSet rs = ps.executeQuery();
		    return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void playerRemove(String by) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE punishments SET active = '0', removed = '1', removedby = ? WHERE player = ? AND active = '1' AND type = ?");
				ps.setString(1, by);
				ps.setString(2, player.toString());
				ps.setString(3, type.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void playerRemoveIPBan(String by) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE punishments SET active = '0', removed = '1', removedby = ? WHERE ip = ? AND active = '1' AND type = ?");
				ps.setString(1, by);
				ps.setString(2, ip);
				ps.setString(3, type.toString());
				ps.executeUpdate();
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
 
	public String getPunisher() {
		return punisher;
	}

	public String getReason() {
		return reason;
	}

	public long getExpire() {
		return expire;
	}
	
	public long getDuration() {
		return time;
	}

	public Type getType() {
		return type;
	}
	
	public Type getCategory() {
		return category;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getIP() {
		return ip;
	}

	public boolean isPermanent() {
		if (expire == -1L)
			return true;
		else
			return false;
	}
	
	public boolean isIPBan() {
		return ipban;
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public String removedBy() {
		return removedBy;
	}
	
}
