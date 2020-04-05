package me.Kit.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Database.Database;

public class KitPlayerManager {
	
    private Set<KitPlayer> users = new HashSet<>();
    
    public Set<KitPlayer> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public Optional<KitPlayer> getUser(UUID uuid) {
        return users.parallelStream().filter(Objects::nonNull).filter((user -> user.getUUID().toString().equalsIgnoreCase(uuid.toString()))).findFirst();
    }
    
    public void saveUsers() {
        new CopyOnWriteArrayList<>(getUsers()).parallelStream().filter(Objects::nonNull).forEach(this::saveAll);
    }
    
    public void runningSave() {
        new CopyOnWriteArrayList<>(getUsers()).parallelStream().filter(Objects::nonNull).forEach(this::updateUser);
    }
    
    public Optional<KitPlayer> loadUser(UUID id) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM kitData WHERE UUID = ?");
            statement.setString(1, id.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
            	KitPlayer user = new KitPlayer(id, false);
                user.setLevel(set.getInt("level"));
                user.setExp(set.getInt("exp"));
                user.setKills(set.getInt("kills"));
                user.setDeaths(set.getInt("deaths"));
                user.setHighStreak(set.getInt("high"));
                user.setCurrentStreak(set.getInt("current"));
                user.setGaps(set.getInt("gaps"));
                user.setSword(set.getInt("sword"));
                user.setArrows(set.getInt("arrows"));
                user.setCoins(set.getInt("coins"));
                users.add(user);
                set.close();
                statement.close();
                return Optional.of(user);
            } else {
            	KitPlayer user = new KitPlayer(id, false);
            	user.create();
                user.setLevel(1);
                user.setExp(0);
                user.setKills(0);
                user.setDeaths(0);
                user.setHighStreak(0);
                user.setCurrentStreak(0);
                user.setGaps(0);
                user.setSword(0);
                user.setArrows(0);
                user.setCoins(0);
                users.add(user);
                set.close();
                statement.close();
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating new SQL information: " + id.toString());
            return Optional.empty();
        }
    }
    
    public void saveUser(KitPlayer user) {
    	if (getUser(user.getUUID()).isPresent()) {
    		updateUser(user);
            users.remove(user);
    	}
    }
    
    private void saveAll(KitPlayer user) {
    	if (getUser(user.getUUID()).isPresent()) {
		    try {
		    	PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET level=?, exp=?, kills=?, deaths=?, high=?, current=?, gaps=?, sword=?, arrows=?, coins=? WHERE UUID = ?");
				ps.setInt(1, user.getLevel());
				ps.setInt(2, user.getExp());
				ps.setInt(3, user.getKills());
				ps.setInt(4, user.getDeaths());
				ps.setInt(5, user.getHighStreak());
				ps.setInt(6, user.getStreak());
				ps.setInt(7, user.getGaps());
				ps.setInt(8, user.getSword());
				ps.setInt(9, user.getArrows());
				ps.setInt(10, user.getCoins());
				ps.setString(11, user.getUUID().toString());
		        ps.executeUpdate();
		        ps.close();
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
	        users.remove(user);
    	}
	        
    }
    
    private void updateUser(KitPlayer user) {
    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	        try {
	            PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE kitData SET level=?, exp=?, kills=?, deaths=?, high=?, current=?, gaps=?, sword=?, arrows=?, coins=? WHERE UUID = ?");
				ps.setInt(1, user.getLevel());
				ps.setInt(2, user.getExp());
				ps.setInt(3, user.getKills());
				ps.setInt(4, user.getDeaths());
				ps.setInt(5, user.getHighStreak());
				ps.setInt(6, user.getStreak());
				ps.setInt(7, user.getGaps());
				ps.setInt(8, user.getSword());
				ps.setInt(9, user.getArrows());
				ps.setInt(10, user.getCoins());
				ps.setString(11, user.getUUID().toString());
	            ps.executeUpdate();
	            ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	});
    }

}
