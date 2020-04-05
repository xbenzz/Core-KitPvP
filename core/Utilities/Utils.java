package me.core.Utilities;

import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.*;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.Kit.Kit;
import me.Kit.Kits.Kits;
import me.Kit.Kits.StarterKit;
import me.core.Database.Database;
import me.core.Profile.Profile;
import me.core.Rank.Rank;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

import org.bukkit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils implements PluginMessageListener {
	
	public static String PERMISSION;
    public static String NO_PERMISSION;
    public static int PLAYERS;
    
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		try {
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
		    String command = in.readUTF();
		    if (command.equals("PlayerCount")) {
		    	String subchannel = in.readUTF();
		        if (subchannel.equals("ALL")) {
		        	int playercount = in.readInt();
		        	PLAYERS = playercount;
		        }
		    }
		 } catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static int getCount() {
		return PLAYERS;
	}
	
	public static void getGlobal(String server) {
		try {
		     ByteArrayDataOutput out = ByteStreams.newDataOutput();
		     out.writeUTF("PlayerCount");
		     out.writeUTF(server);
		      
		     Bukkit.getServer().sendPluginMessage(Kit.getPlugin(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
		   e.printStackTrace();
		}
	}
    
    public static void globalMessage(String player, String message) {
    	try {
    		ByteArrayDataOutput out = ByteStreams.newDataOutput();
    		out.writeUTF("Message");
    		out.writeUTF(player);
    		out.writeUTF(message);
        
    		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
    		p.sendPluginMessage(Kit.getPlugin(), "BungeeCord", out.toByteArray());
    	} catch (Exception e) {}
    }
    
    
    public static void kickPlayer(String player, String reason) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(player);
        out.writeUTF(reason);
        
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        p.sendPluginMessage(Kit.getPlugin(), "BungeeCord", out.toByteArray());
    }
    
    public static ArrayList<UUID> getAllStaff() {
    	ArrayList<UUID> list = new ArrayList<UUID>();
        try {
        	PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData");
        	ResultSet rs = statement.executeQuery();
        	while (rs.next()) {
        		UUID uuid = UUID.fromString(rs.getString("UUID"));
        		Rank rank = Rank.getRankOrDefault(rs.getString("Rank"));
        		if (rank.isAboveOrEqual(Rank.HELPER)) {
        			if (!list.contains(uuid))
        				list.add(uuid);
        		}	
        	}
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<UUID> getAllAdmins() {
    	ArrayList<UUID> list = new ArrayList<UUID>();
        try {
        	PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData");
        	ResultSet rs = statement.executeQuery();
        	while (rs.next()) {
        		UUID uuid = UUID.fromString(rs.getString("UUID"));
        		Rank rank = Rank.getRankOrDefault(rs.getString("Rank"));
        		if (rank.isAboveOrEqual(Rank.ADMIN)) {
        			if (!list.contains(uuid))
        				list.add(uuid);
        		}	
        	}
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return list;
    }
	
	public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
	}
	
	public static void blockTab() {
		final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
	    manager.addPacketListener(new PacketAdapter(Kit.getPlugin(), new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {

		public void onPacketReceiving(PacketEvent event) {
			  Player player = event.getPlayer();
			  Profile p = Kit.getProfileManager().getUser(player.getUniqueId()).get();
	        if ((event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) 
	        		&& (!p.getRank().isAboveOrEqual(Rank.OWNER)) 
	        		&& (((String)event.getPacket().getStrings().read(0)).startsWith("/"))
	        		&& (((String)event.getPacket().getStrings().read(0)).split(" ").length == 1)) {
	        	
	          event.setCancelled(true);

	          List<String> list = new ArrayList<String>();
	          List<String> extra = new ArrayList<String>();

	          String[] tabList = new String[list.size() + extra.size()];

	          for (int index = 0; index < list.size(); index++) {
	            tabList[index] = ((String)list.get(index));
	          }

	          for (int index = 0; index < extra.size(); index++) {
	            tabList[(index + list.size())] = ('/' + (String)extra.get(index));
	          }
	          PacketContainer tabComplete = manager.createPacket(PacketType.Play.Server.TAB_COMPLETE);
	          tabComplete.getStringArrays().write(0, tabList);
	          
	          	try {
	          		manager.sendServerPacket(event.getPlayer(), tabComplete);
	          	} catch (InvocationTargetException e) {
	          		e.printStackTrace();
	          	}
	        }
	      }
	    });
	}
			  
	public static void sendTablist(Player p, String msg, String msg2) {
		 try {
			Object header = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{'text': '" + msg + "'}" });
			Object footer = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{'text': '" + msg2 + "'}" });
			        
			Object ppoplhf = getNmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[] { getNmsClass("IChatBaseComponent") }).newInstance(new Object[] { header });
			        
			Field f = ppoplhf.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(ppoplhf, footer);
			        
			Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
			Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
			        
			pcon.getClass().getMethod("sendPacket", new Class[] { getNmsClass("Packet") }).invoke(pcon, new Object[] { ppoplhf });
		 } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException|ClassNotFoundException|InstantiationException|NoSuchFieldException e) {
			 e.printStackTrace();
		 }
	 }
	
    public static void sendTitle(Player p, String title, String subtitle) {
        PacketPlayOutTitle playOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(title));
        PacketPlayOutTitle playOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(playOutTitle);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(playOutSubTitle);
    }
	
	public static void respawn(Player player){
		Bukkit.getScheduler().runTaskLater(Kit.getPlugin(), () -> {
			player.spigot().respawn();
			Kits kit = new StarterKit();
			player.getInventory().setArmorContents(kit.getArmor());
			player.getInventory().setContents(kit.getItems());
			player.addPotionEffects(kit.getEffects());
	    }, 3L);
	}
    
    public static boolean isOnline(final CommandSender sender, final Player player) {
        return player != null && (!(sender instanceof Player) || ((Player)sender).canSee(player));
    }
    
    public static void PLAYER_NOT_FOUND(final CommandSender sender, final String player) {
        sender.sendMessage(ColorText.translate("&cServer> &e" + player + " &6not found."));
    }
    
    public static int getPing(final Player player) {
        final CraftPlayer craft = (CraftPlayer)player;
        return craft.getHandle().ping;
    }
    
	public static String formatLong(long startTime) {
		String mins;
		String hours;
		String days;
		String seconds;
		
		startTime = startTime / 1000;
		
		long d = startTime / 86400;
		days = "&c" + String.valueOf(d) + " &cDays&6, ";
		startTime = startTime % 86400;
		
		long h = startTime / 3600;
		hours = "&c" + String.valueOf(h) + " &cHours&6, ";
		startTime = startTime % 3600;
		
		long m = startTime / 60;
		mins = "&c" + String.valueOf(m) + " &cMinutes&6, ";
		startTime = startTime % 60;
		
		long s = startTime;
		seconds = "&c" + String.valueOf(s) + " &cSeconds";
		
		String total = "";
		if (d != 0) {
			total += days;
		} 
		if (h != 0) {
			total += hours;
		}
		if (m != 0) {
			total += mins;
		}
		if (s != 0) {
			total += seconds;
		}
		return ChatColor.translateAlternateColorCodes('&', total);
	}
    
    static {
        Utils.NO_PERMISSION = ColorText.translate("&cPermissions> &6You do not have permission to execute that command.");
    }
}
