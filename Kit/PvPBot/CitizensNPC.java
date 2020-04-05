package me.Kit.PvPBot;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.PlayerAnimation;
import net.citizensnpcs.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Kit.Kit;
import me.Kit.Kits.Kits;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CitizensNPC {
	
    public static final List<CitizensNPC> npcs = new ArrayList<>();
    private static boolean teleportFix;
    final Location spawnLocation;
    private final NPC npc;
    public CombatTask combatTask;
    private boolean destroyed;

    public CitizensNPC(String name, String skin, Location loc) {
        this.spawnLocation = loc;
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        this.npc.data().set("player-skin-name", skin);
        if (this.npc.isSpawned()) {
            this.npc.despawn();
        }

        new BukkitRunnable() {
            int tries = 0;
            @Override
            public void run() {
                if (this.tries++ == 100) {
                    cancel();
                }
                if (loc != null && loc.getWorld() != null && loc.getChunk() != null) {
                    if (loc.getChunk().isLoaded()) {
                        CitizensNPC.this.npc.spawn(loc);
                        cancel();
                    } else {
                        loc.getChunk().load();
                    }
                }
            }
        }.runTaskTimer(Kit.getPlugin(), 0, 2);
        npcs.add(this);
    }

    public static double getDamage(ItemStack item) {
    	return getDamageNoId(item);
    }


    public static double getDamageNoId(ItemStack item) {
        double d;
        switch (item.getType().toString()) {
            case "WOOD_SWORD":
            case "GOLD_SWORD":
                d = 4;
                break;
            case "STONE_SWORD":
                d = 5;
                break;
            case "IRON_SWORD":
                d = 6;
                break;
            case "DIAMOND_SWORD":
                d = 7;
                break;
            case "WOOD_SPADE":
            case "GOLD_SPADE":
                d = 1;
                break;
            case "STONE_SPADE":
                d = 2;
                break;
            case "IRON_SPADE":
                d = 3;
                break;
            case "DIAMOND_SPADE":
                d = 4;
                break;
            case "WOOD_PICKAXE":
            case "GOLD_PICKAXE":
                d = 2;
                break;
            case "STONE_PICKAXE":
                d = 3;
                break;
            case "IRON_PICKAXE":
                d = 4;
                break;
            case "DIAMOND_PICKAXE":
                d = 5;
                break;
            case "WOOD_AXE":
            case "GOLD_AXE":
                d = 3;
                break;
            case "STONE_AXE":
                d = 4;
                break;
            case "IRON_AXE":
                d = 5;
                break;
            case "DIAMOND_AXE":
                d = 6;
                break;
            default:
                d = 1;
                break;
        }
        return d;
    }

    public boolean isSpawned() {
        return this.npc.isSpawned();
    }

    public Player getBukkitEntity() {
        return (Player) this.npc.getEntity();
    }
    
    public void disable() {
        this.npc.despawn(DespawnReason.PLUGIN);
        if (getBukkitEntity() != null) {
        	getBukkitEntity().setHealth(20);
        }
        this.npc.destroy();
        this.destroyed = true;
    }

    public void destroy(boolean fast) {
        if (!Bukkit.isPrimaryThread()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    destroy(fast);
                }
            }.runTask(Kit.getPlugin());
        }
        this.npc.despawn(DespawnReason.PLUGIN);
        if (getBukkitEntity() != null) {
        	getBukkitEntity().setHealth(20);
        }
        this.npc.destroy();
        this.destroyed = true;
        npcs.remove(this);
    }

    public void destroy() {
        if (!this.destroyed) {
            teleport(Bukkit.getWorld("KitPvP").getSpawnLocation().subtract(0, 100, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    destroy(true);
                }
            }.runTaskLater(Kit.getPlugin(), 15);
        }
        npcs.remove(this);
    }

    public void swingMainArm() {
        if (getBukkitEntity() != null) {
            PlayerAnimation.ARM_SWING.play(getBukkitEntity());
            Bukkit.getPluginManager().callEvent(new PlayerAnimationEvent(getBukkitEntity()));
        }
    }

    public NPC getNPC() {
        return this.npc;
    }


    /**
     * Probably doesn't work
     * Can't set velocities of the NPC's
     */
    public void setVelocity(Vector v) {
        if (this.npc != null && !this.destroyed) {
            getBukkitEntity().setVelocity(v);
        }
    }

    public boolean teleport(Location loc) {
        if (loc == null || getBukkitEntity() == null) {
            return false;
        }
        if (teleportFix && (errorTeleport(loc) || errorTeleport(getBukkitEntity().getLocation()))) {
            return false;
        }
        if (!loc.getChunk().isLoaded()) {
            loc.getChunk().load();
        }
        try {
            this.npc.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            Util.assumePose(getBukkitEntity(), loc.getYaw(), loc.getPitch());
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to teleport a NPC");
        }
        return false;
    }

    /**
     * Will avoid any errors. If there were players close it seems to work (probably loaded chunks, not sure exactly).
     * Sometimes this breaks it on some spigots though
     */
    private boolean errorTeleport(Location loc) {
        double dist = Double.MAX_VALUE;
        for (Player p : loc.getWorld().getPlayers()) {
            // sometimes this is false
            if (p.getWorld() == loc.getWorld()) {
                double d = p.getLocation().distanceSquared(loc);
                if (dist > d) {
                    dist = d;
                }
            }
        }
        return dist > 200 * 200;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void startCombatTask(List<UUID> players, Difficulty difficulty, Kits kit) {
        this.combatTask = new CombatTask(this, players, difficulty, kit);
    }

    public enum Difficulty {
        EASY,
        NORMAL,
        HARD,
        HACKER
    }
}
