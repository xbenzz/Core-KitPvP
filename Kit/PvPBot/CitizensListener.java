package me.Kit.PvPBot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Kit.Kit;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;

public class CitizensListener implements Listener {
	
    @EventHandler
    public void onDie(NPCDeathEvent e) {
        try {
            for (CitizensNPC npc : CitizensNPC.npcs) {
                if (e.getNPC().getUniqueId().equals(npc.getNPC().getUniqueId())) {
                    CombatTask task = npc.combatTask;
                    if (task != null) {
                        task.cancel();
                        npc.destroy();                    
                    }
                    return;
                }
            }
        } catch (Exception ex) { 
        	Bukkit.getLogger().warning("Failed to death bot!");
        }
    }

    /**
     * Cancels the combat "AI" when it's despawned and if the NPC will be respawned it will continue the combat.
     */
    @EventHandler
    public void onDespawn(NPCDespawnEvent e) {
        try {
            for (CitizensNPC npc : CitizensNPC.npcs) {
                if (e.getNPC().getUniqueId().equals(npc.getNPC().getUniqueId())) {
                    CombatTask task = npc.combatTask;
                    if (task != null) {
                        task.cancel();
                        if (e.getReason() == DespawnReason.PENDING_RESPAWN) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    npc.combatTask = new CombatTask(npc, task.players, task.difficulty, task.kitType);
                                }
                            }.runTaskLater(Kit.getPlugin(), 5);
                        }
                    }
                    return;
                }
            }
        }catch (Exception ex) {
        	Bukkit.getLogger().warning("Failed to despawn bot!");
        }
    }

    /**
     * Track the bot's pearls and teleport it
     */
    @EventHandler
    public void onPearl(ProjectileHitEvent e) {
        if(e.getEntity().hasMetadata("BOT_PEARL") && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof LivingEntity) {
            for (CitizensNPC npc : CitizensNPC.npcs) {
                if (npc.getBukkitEntity() == e.getEntity().getShooter()) {
                    npc.getNPC().getNavigator().setTarget(null);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if(npc.getNPC() != null && npc.getBukkitEntity() != null) {
                                npc.getNPC().teleport(e.getEntity().getLocation().add(0,1,0), PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                                npc.getBukkitEntity().damage(0.2);
                            }
                        }
                    }.runTaskLater(Kit.getPlugin(), 1);
                    return;
                }
            }
        }
    }

    /**
     * Somewhere the bot is spawned with 0 hp and too lazy to find and fix why so this will fix some stuff
     */
    @EventHandler
    public void onSpawn(NPCSpawnEvent e) {
        if(e.getNPC() != null) {
            Damageable damageable = (Damageable) e.getNPC().getEntity();
            if (damageable != null && damageable.getHealth() <= 0) {
                e.setCancelled(true);
            }
        }
    }
}