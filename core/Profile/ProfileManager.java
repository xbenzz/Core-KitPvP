package me.core.Profile;

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
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import me.Kit.Kit;
import me.core.Database.Database;
import me.core.Punishment.Punishment;
import me.core.Punishment.Type;
import me.core.Rank.Rank;

public class ProfileManager {
	
    private Set<Profile> users = new HashSet<>();
    public Set<Punishment> mutes = new HashSet<>();
    
    public Set<Profile> getUsers() {
        return Collections.unmodifiableSet(users);
    }
    
    public Set<Punishment> getMutes() {
    	return mutes;
    }
    
    public Set<Profile> getUsersByRank(Rank rank) {
        return users.stream().filter(it -> it.getRank() == rank).collect(Collectors.toSet());
    }
    
    public Set<Profile> getUsersAboveOrERank(Rank rank) {
        return users.stream().filter(it -> it.getRank().getId() >= rank.getId()).collect(Collectors.toSet());
    }
    
    public Optional<Punishment> getMute(UUID uuid) {
    	return mutes.stream().filter(Objects::nonNull).filter((it -> it.getPlayer().toString().equalsIgnoreCase(uuid.toString()))).findFirst();
    }

    public Optional<Profile> getUser(UUID uuid) {
        return users.parallelStream().filter(Objects::nonNull).filter((user -> user.getUUID().toString().equalsIgnoreCase(uuid.toString()))).findFirst();
    }

    public Optional<Profile> getUser(String name) {
        return users.parallelStream().filter(Objects::nonNull).filter((user -> user.getUsername().equalsIgnoreCase(name))).findFirst();
    }
    
    public void saveUsers() {
        new CopyOnWriteArrayList<>(getUsers()).parallelStream().filter(Objects::nonNull).forEach(this::saveAllUser);
    }
    
    public Optional<Profile> loadUser(UUID id) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID = ?");
            statement.setString(1, id.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
            	retrieveMutes(id);
            	Profile user = new Profile(id, false);
                user.setUsername(set.getString("Username"));
                user.setRank(Rank.valueOf(set.getString("Rank")));
                user.setMessages(set.getBoolean("TogglePM"));
                users.add(user);
                set.close();
                statement.close();
                return Optional.of(user);
            } else {
            	Profile user = new Profile(id, false);
            	user.createProfile();
                user.setUsername("");
                user.setRank(Rank.DEFAULT);
                user.setMessages(true);
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
    
    public void saveAllUser(Profile user) {
    	if (getUser(user.getUUID()).isPresent()) {
	        try {
	            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE playerData SET TogglePM = ? WHERE UUID = ?");
	            statement.setBoolean(1, user.hasMessages());
	            statement.setString(2, user.getUUID().toString());
	            statement.executeUpdate();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    		users.remove(user);
    		mutes.remove(getMute(user.getUUID()).get());
        }
    }
    
    public void saveUser(Profile user) {
    	if (getUser(user.getUUID()).isPresent()) {
    		updateUser(user);
    		users.remove(user);
    		mutes.remove(getMute(user.getUUID()).get());
        }
    }
    
    private void retrieveMutes(UUID uuid) {
    	Punishment mute = new Punishment(uuid, Type.MUTE);
    	mutes.add(mute);
    }
    
    private void updateUser(Profile user) {
    	Bukkit.getScheduler().runTaskAsynchronously(Kit.getPlugin(), () -> {
	        try {
	            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE playerData SET TogglePM = ? WHERE UUID = ?");
	            statement.setBoolean(1, user.hasMessages());
	            statement.setString(2, user.getUUID().toString());
	            statement.executeUpdate();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	});
    }
    
    
}
