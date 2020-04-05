package me.Kit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.Kit.Commands.BotFightCommand;
import me.Kit.Commands.CoinsCommand;
import me.Kit.Commands.DebugCommand;
import me.Kit.Commands.KitsCommand;
import me.Kit.Commands.ShopCommand;
import me.Kit.Commands.StatsCommand;
import me.Kit.Listener.BotListener;
import me.Kit.Listener.DeathListener;
import me.Kit.Listener.KitsListener;
import me.Kit.Listener.ShopListener;
import me.Kit.Manager.CombatManager;
import me.Kit.Manager.KitManager;
import me.Kit.PvPBot.CitizensListener;
import me.Kit.PvPBot.CitizensNPC;
import me.Kit.User.KitPlayerManager;
import me.core.AntiCheat.ACBanCommand;
import me.core.AntiCheat.CheatListener;
import me.core.Commands.EnderchestCommand;
import me.core.Commands.FlightCommand;
import me.core.Commands.HelpCommand;
import me.core.Commands.InspectCommand;
import me.core.Commands.ListCommand;
import me.core.Commands.MessageCommand;
import me.core.Commands.PingCommand;
import me.core.Commands.RenameCommand;
import me.core.Commands.ReplyCommand;
import me.core.Commands.RequestCommand;
import me.core.Commands.TimeCommand;
import me.core.Commands.TogglePrivateMessagesCommand;
import me.core.Commands.Admin.BroadcastCommand;
import me.core.Commands.Admin.EnchantCommand;
import me.core.Commands.Admin.GamemodeCommand;
import me.core.Commands.Admin.GiveCommand;
import me.core.Commands.Admin.IPCommand;
import me.core.Commands.Admin.KillCommand;
import me.core.Commands.Admin.LightningCommand;
import me.core.Commands.Admin.MonitorCommand;
import me.core.Commands.Admin.RepairCommand;
import me.core.Commands.Admin.TeleportAllCommand;
import me.core.Commands.Admin.TeleportCoordCommand;
import me.core.Commands.Staff.ClearChatCommand;
import me.core.Commands.Staff.FreezeCommand;
import me.core.Commands.Staff.HealCommand;
import me.core.Commands.Staff.MuteChatCommand;
import me.core.Commands.Staff.LogsCommand;
import me.core.Commands.Staff.ReportsCommand;
import me.core.Commands.Staff.StaffChatCommand;
import me.core.Commands.Staff.TeleportCommand;
import me.core.Commands.Staff.TeleportHereCommand;
import me.core.Commands.Staff.VanishCommand;
import me.core.Database.Database;
import me.core.Handler.AnnounceHandler;
import me.core.Handler.TabHandler;
import me.core.Listener.ChatListener;
import me.core.Listener.FreezeListener;
import me.core.Listener.InvListener;
import me.core.Listener.ScoreListener;
import me.core.Listener.ServerListener;
import me.core.Listener.TabListener;
import me.core.Listener.VanishListener;
import me.core.Manager.PunishmentManager;
import me.core.Profile.ProfileListener;
import me.core.Profile.ProfileManager;
import me.core.Punishment.PunishmentListener;
import me.core.Punishment.Command.BanCommand;
import me.core.Punishment.Command.CheckIPCommand;
import me.core.Punishment.Command.ClearPunishCommand;
import me.core.Punishment.Command.HistoryCommand;
import me.core.Punishment.Command.IPBanCommand;
import me.core.Punishment.Command.KickCommand;
import me.core.Punishment.Command.MuteCommand;
import me.core.Punishment.Command.TempBanCommand;
import me.core.Punishment.Command.TempMuteCommand;
import me.core.Punishment.Command.UnIPBanCommand;
import me.core.Punishment.Command.UnbanCommand;
import me.core.Punishment.Command.UnmuteCommand;
import me.core.Rank.RankCommand;
import me.core.Rank.RankListener;
import me.core.Report.ReportsHandler;
import me.core.Utilities.Cooldowns;
import me.core.Utilities.Utils;
import net.citizensnpcs.api.CitizensAPI;

public class Kit extends JavaPlugin {
	
	private static Kit plugin;
    private static ProfileManager profileManager;
    private static KitPlayerManager kitManager;
   
	public void onEnable() {
		getLogger().info("Core-KitPvP by xBenz has been enabled!");
		load();
	}

	public void onDisable() {
		getLogger().info("Core-KitPvP by xBenz has been disabled!");
        for (CitizensNPC npc : CitizensNPC.npcs) {
        	npc.disable();
        }
        kitManager.saveUsers();
        profileManager.saveUsers();
    	CitizensAPI.getNPCRegistries().forEach(p -> p.deregisterAll());
		Bukkit.getOnlinePlayers().stream().forEach(p -> p.kickPlayer("Server Restarting! Join back in a few minutes"));
		Bukkit.getScheduler().cancelAllTasks();
		Bukkit.getScheduler().cancelTasks(this);
		Bukkit.getScheduler().getPendingTasks().clear();
		Database.closeConnection();
	}
   
	private void load() {
		plugin = this;
		profileManager = new ProfileManager();
		kitManager = new KitPlayerManager();
		Database.openConnection();
     	Database.createTables();
     	registerConfiguration();
    	registerCommands();
    	registerEvents();
    	registerCooldowns();
    	AnnounceHandler.setUp();
    	Utils.blockTab();
    	PunishmentManager.checkPunishments();
    	KitManager.saveOnRun();
    	TabHandler.removeEmptyTeams();
    	CombatManager.checkTags();
    	CitizensAPI.getNPCRegistries().forEach(p -> p.deregisterAll());
    	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", (PluginMessageListener) new Utils());
	}
   
	private void registerCommands() {
		 getCommand("request").setExecutor(new RequestCommand());
		 getCommand("ping").setExecutor(new PingCommand());
		 getCommand("help").setExecutor(new HelpCommand());
	     getCommand("flight").setExecutor(new FlightCommand());
	     getCommand("toggleprivatemessages").setExecutor(new TogglePrivateMessagesCommand());
	     getCommand("playtime").setExecutor(new TimeCommand());
	     getCommand("list").setExecutor(new ListCommand());
	     getCommand("reports").setExecutor(new ReportsCommand());
	     getCommand("message").setExecutor(new MessageCommand());
	     getCommand("reply").setExecutor(new ReplyCommand());
	     getCommand("stats").setExecutor(new StatsCommand());
	     getCommand("enderchest").setExecutor(new EnderchestCommand());
	     getCommand("shop").setExecutor(new ShopCommand());
	     getCommand("kit").setExecutor(new KitsCommand());
	     getCommand("rename").setExecutor(new RenameCommand());
	     getCommand("bot").setExecutor(new BotFightCommand());
		
		 // Staff
		 getCommand("sc").setExecutor(new StaffChatCommand());
		 getCommand("vanish").setExecutor(new VanishCommand());
	     getCommand("freeze").setExecutor(new FreezeCommand());
	     getCommand("clearchat").setExecutor(new ClearChatCommand());   
	     getCommand("mutechat").setExecutor(new MuteChatCommand());
	     getCommand("teleporthere").setExecutor(new TeleportHereCommand());
	     getCommand("teleport").setExecutor(new TeleportCommand());
	     getCommand("inspect").setExecutor(new InspectCommand());
	     getCommand("ban").setExecutor(new BanCommand());
	     getCommand("clearpunish").setExecutor(new ClearPunishCommand());
	     getCommand("history").setExecutor(new HistoryCommand());
	     getCommand("kick").setExecutor(new KickCommand());
	     getCommand("mute").setExecutor(new MuteCommand());
	     getCommand("tempmute").setExecutor(new TempMuteCommand());
	     getCommand("tempban").setExecutor(new TempBanCommand());
	     getCommand("unban").setExecutor(new UnbanCommand());
	     getCommand("unmute").setExecutor(new UnmuteCommand());
	     getCommand("notes").setExecutor(new LogsCommand());
	     getCommand("heal").setExecutor(new HealCommand());
	     
     
	     // Admin
	     getCommand("broadcast").setExecutor(new BroadcastCommand());    
	     getCommand("rank").setExecutor(new RankCommand());
	     getCommand("teleportall").setExecutor(new TeleportAllCommand());
	     getCommand("teleportcoords").setExecutor(new TeleportCoordCommand());
	     getCommand("gamemode").setExecutor(new GamemodeCommand());
	     getCommand("kill").setExecutor(new KillCommand());
	     getCommand("lightning").setExecutor(new LightningCommand());
	     getCommand("give").setExecutor(new GiveCommand());    
	     getCommand("enchant").setExecutor(new EnchantCommand());  
	     getCommand("iplookup").setExecutor(new IPCommand());
	     getCommand("ipban").setExecutor(new IPBanCommand());
	     getCommand("unipban").setExecutor(new UnIPBanCommand());
	     getCommand("checkip").setExecutor(new CheckIPCommand());
	     getCommand("debug").setExecutor(new DebugCommand());
	     getCommand("coins").setExecutor(new CoinsCommand());
	     getCommand("repair").setExecutor(new RepairCommand());
	     getCommand("monitor").setExecutor(new MonitorCommand());
	     
	     getCommand("acban").setExecutor(new ACBanCommand());
	}
   
	private void registerEvents() {
	     PluginManager manager = Bukkit.getPluginManager();
	     manager.registerEvents(new ChatListener(), this);
	     manager.registerEvents(new FreezeListener(), this);
	     manager.registerEvents(new ScoreListener(), this);
	     manager.registerEvents(new TabListener(), this);
	     manager.registerEvents(new ServerListener(), this);
	     manager.registerEvents(new ProfileListener(), this);
	     manager.registerEvents(new PunishmentListener(), this);
	     manager.registerEvents(new RankListener(), this);
	     manager.registerEvents(new InvListener(), this);
	     manager.registerEvents(new ReportsHandler(), this);
	     manager.registerEvents(new DeathListener(), this);
	     manager.registerEvents(new VanishListener(), this);
	     manager.registerEvents(new ShopListener(), this);
	     manager.registerEvents(new KitsListener(), this);
	     manager.registerEvents(new CheatListener(), this);
	     manager.registerEvents(new CitizensListener(), this);
	     manager.registerEvents(new BotListener(), this);
	}
   
	private void registerCooldowns() {
		Cooldowns.createCooldown("request_delay");
		Cooldowns.createCooldown("chat_delay");
		Cooldowns.createCooldown("vip_kit");
		Cooldowns.createCooldown("elite_kit");
		Cooldowns.createCooldown("mvp_kit");
	}
   
	private void registerConfiguration() {
		FileConfiguration cfg = getConfig();
		cfg.options().copyDefaults(true);
		saveDefaultConfig();
	}
	
	public static KitPlayerManager getKitManager() {
		return kitManager;
	}
	
	public static ProfileManager getProfileManager() {
		return profileManager;
	}
	
	public static Kit getPlugin() {
		return plugin;
	}

}
