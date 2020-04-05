package me.Kit.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Utilities.ColorText;
import me.core.Utilities.Utils;

public class KitPlayer {
	
	private UUID uuid;
	private int level;
	private int kills;
	private int deaths;
	private int high;
	private int current;
	private int gaps;
	private int exp;
	private int sword;
	private int arrows;
	private int coins;
	
	public KitPlayer(UUID uuid, boolean lookup) {
		if (lookup) {
			this.uuid = uuid;
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM kitData WHERE uuid = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					kills = rs.getInt("kills");
					level = rs.getInt("level");
					deaths = rs.getInt("deaths");
					high = rs.getInt("high");
					current = rs.getInt("current");
					gaps = rs.getInt("gaps");
					exp = rs.getInt("exp");
					sword = rs.getInt("sword");
					arrows = rs.getInt("arrows");
					coins = rs.getInt("coins");
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
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM kitData WHERE uuid = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void create() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO kitData(uuid, level, exp, kills, deaths, high, current, gaps, sword, arrows, coins) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, uuid.toString());
				ps.setInt(2, 1);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				ps.setInt(6, 0);
				ps.setInt(7, 0);
				ps.setInt(8, 0);
				ps.setInt(9, 0);
				ps.setInt(10, 0);
				ps.setInt(11, 0);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void addSQLKill() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET kills = ? WHERE uuid = ?");
				ps.setInt(1, kills + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});  
	 }
	
	public void addSQLDeath() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET deaths = ? WHERE uuid = ?");
				ps.setInt(1, deaths + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void addSQLXP() {
		int rand = (int) (Math.random() * ((8 - 5) + 1 )) + 5;
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET exp = ? WHERE uuid = ?");
				ps.setInt(1, exp + rand);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void addSQLGap() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET gaps = ? WHERE uuid = ?");
				ps.setInt(1, gaps + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void addSQLSword() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET sword = ? WHERE uuid = ?");
				ps.setInt(1, sword + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void addSQLArrow() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET arrows = ? WHERE uuid = ?");
				ps.setInt(1, arrows + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void levelupSQL() {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET level = ? WHERE uuid = ?");
				ps.setInt(1, level + 1);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		Player p = Bukkit.getPlayer(uuid);
		Utils.sendTitle(p, ColorText.translate("&c&lLEVEL UP"), ColorText.translate("&e" + level + " &7-> &a" + (level + 1)));
    	p.sendMessage(ColorText.translate("&cLevel> &6&lLEVEL UP! &6You are now &e&nLevel " + (level + 1) + " &6."));
    	p.sendMessage(ColorText.translate("&cRewards> &6You have received &e" + ((level + 1) * 10) + " Coins&6."));
    	setCoins(getCoins() + ((level + 1) * 10));
	}
	
	public void setSQLLevel(int level) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET level = ? WHERE uuid = ?");
				ps.setInt(1, level);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setSQLCoins(int coins) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET coins = ? WHERE uuid = ?");
				ps.setInt(1, coins);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setSQLKills(int kills) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET kills = ? WHERE uuid = ?");
				ps.setInt(1, kills);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setSQLHighStreak(int high) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET high = ? WHERE uuid = ?");
				ps.setInt(1, high);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setSQLCurrentStreak(int cur) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET current = ? WHERE uuid = ?");
				ps.setInt(1, cur);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void setSQLExp(int exp) {
		Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET exp = ? WHERE uuid = ?");
				ps.setInt(1, exp);
				ps.setString(2, uuid.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public int getKillsNeeded() {
	     int kills = 0;
	     int j = 10;
	     for (int i = 1; i < level + 1; i++) {
	    	 kills = i * j;
	    	 j++;
	     }
	     return kills;
	}
	
	
	public int getExpNeeded() {
    	int exp = (int) Math.floor((Math.pow(level, 1.4) * 100));
		return exp;
	}
	
	public String toString() {
		return "KitPlayer(UUID:" + uuid + ", Kills:" + kills + ", Deaths:" + deaths + ", Level:" + level + ")";
	}
	
	public void setKills(int k) {
		this.kills = k;
	}
	
	public void setDeaths(int d) {
		this.deaths = d;
	}
	
	public void setHighStreak(int high) {
		this.high = high;
	}
	
	public void setCurrentStreak(int cur) {
		this.current = cur;
	}
	
	public void setGaps(int g) {
		this.gaps = g;
	}
	
	public void setLevel(int l) {
		this.level = l;
	}
	
	public void setExp(int ex) {
		this.exp = ex;
	}
	
	public void setSword(int s) {
		this.sword = s;
	}

	public void setArrows(int a) {
		this.arrows = a;
	}
	
	public void setCoins(int coin) {
		this.coins = coin;
	}
	
	public void addKill() {
		this.kills += 1;
	}
	
	public void addDeath() {
		this.deaths += 1;
	}
	
	public void levelup() {
		Player p = Bukkit.getPlayer(uuid);
		Utils.sendTitle(p, ColorText.translate("&c&lLEVEL UP"), ColorText.translate("&e" + level + " &7-> &a" + (level + 1)));
    	p.sendMessage(ColorText.translate("&cLevel> &6&lLEVEL UP! &6You are now &e&nLevel " + (level + 1) + " &6."));
    	p.sendMessage(ColorText.translate("&cRewards> &6You have received &e" + ((level + 1) * 10) + " Coins&6."));
    	setCoins(coins + ((level + 1) * 10));
    	setLevel(level + 1);
	}
	
	public void addExp() {
		int rand = (int) (Math.random() * ((8 - 5) + 1 )) + 5;
		this.exp += rand;
	}
	
	public void addSword() {
		this.sword += 1;
	}
	
	public void addArrow() {
		this.arrows += 1;
	}
	
	public void addGap() {
		this.gaps += 1;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public int getHighStreak() {
		return high;
	}
	
	public int getStreak() {
		return current;
	}
	
	public int getGaps() {
		return gaps;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExp() {
		return exp;
	}	
	
	public int getSword() {
		return sword;
	}
	
	public int getArrows() {
		return arrows;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void save() {
		Kit.getKitManager().saveUser(this);
	}
	
	public void load() {
		Kit.getKitManager().loadUser(uuid);
	}

}
