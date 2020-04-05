package me.Kit.PvPBot;

import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.util.PlayerAnimation;
import net.citizensnpcs.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Kit.Kit;
import me.Kit.Kits.Kits;
import me.Kit.Manager.BotManager;
import me.Kit.PvPBot.CitizensNPC.Difficulty;
import me.core.Manager.VanishManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CombatTask extends BukkitRunnable {

    private final CitizensNPC npc;
    private final Kit plugin;
    List<UUID> players;
    Difficulty difficulty;
    private double distance = 150 * 150;
    private boolean kit, navi, selfHealing;
    private double attackRange;
    private double swingRangeModifier;
    public Player tar = null;
    private long lastPearl;
    private int pearls;
    Kits kitType;

    public CombatTask(CitizensNPC npc, List<UUID> players, Difficulty difficulty, Kits kit) {
        this.npc = npc;
        this.difficulty = difficulty;
        int delay = 2;
        this.plugin = Kit.getPlugin();
        this.players = players;
        this.tar = Bukkit.getPlayer(players.get(0));
        this.kitType = kit;
        if (!npc.isSpawned()) {
            npc.getNPC().spawn(npc.spawnLocation);
        }
        
        this.attackRange = 3.1;
        if (difficulty == Difficulty.EASY) {
            this.attackRange *= 0.8;
            this.swingRangeModifier = -0.5;
        } else if (difficulty == Difficulty.HARD) {
            this.attackRange *= 2;
            this.swingRangeModifier = 2;
        } else if (difficulty == Difficulty.HACKER) {
            this.attackRange *= 2.6;
            this.swingRangeModifier = 3;
            delay = 1;
        }
        
        BotManager.add(tar, npc);

        this.npc.getNPC().getNavigator().getDefaultParameters().range(128);
        npc.getNPC().teleport(npc.spawnLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
        
       /* if (kitType != null) {
            new BukkitRunnable() {
                int count = 5;
                @Override
                public void run() {
                    this.count--;
                    if (this.count == 0) {
                        cancel();
                    }
                    try {
                        npc.getNPC().teleport(npc.spawnLocation, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    } catch (Exception e) {
                    }
                }
            }.runTaskTimer(this.plugin, 10, 10);
        } */
        int countdown = 5;
 
        runTaskTimerAsynchronously(Kit.getPlugin(), countdown, delay);
        
        Random rand = new Random();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (players == null || players.isEmpty() || npc.isDestroyed()) {
                    cancel();
                    return;
                }
                if (npc.isSpawned() && npc.getBukkitEntity() != null && tar != null && !selfHealing) {
                    if (distance <= attackRange * attackRange * 1.5 && rand.nextDouble() > 0.5) {
                        npc.getNPC().getNavigator().setTarget((Entity) tar, true);
                    } else {
                        npc.getNPC().getNavigator().setTarget(tar.getLocation());
                    }
                }
            }
        }.runTaskTimer(plugin, countdown, delay);
    }

    /**
     * The main combat task (except targeting)
     */
    @Override
    public void run() {
        Random rand = new Random();
        if (this.players == null || this.players.isEmpty() || this.npc.isDestroyed()) {
            cancel();
            return;
        }
        if (this.npc.isSpawned() && this.npc.getBukkitEntity() != null) {
            if (!this.kit) {
                this.npc.getNPC().setProtected(false);
                giveKit(kitType);
            }
            
            if (!this.navi) {
                this.navi = true;
                if (this.difficulty == Difficulty.HARD) {
                    this.npc.getNPC().getNavigator().getLocalParameters().speedModifier(1.33F);
                } else if (this.difficulty == Difficulty.HACKER) {
                    this.npc.getNPC().getNavigator().getLocalParameters().speedModifier(1.66F);
                }
                this.npc.getNPC().getNavigator().getLocalParameters().attackRange(this.attackRange);
                this.npc.getNPC().getNavigator().getLocalParameters().stuckAction((arg0, arg1) -> false);
            }
            if (!this.npc.getBukkitEntity().isDead() && this.npc.getBukkitEntity().getLocation().getBlockY() < 0) {
                this.npc.getBukkitEntity().setHealth(0);
                return;
            }
            
            if (!this.tar.isOnline()) {
                cancel();
                return;
            }
            
            distance = this.tar != null && this.tar.getWorld() == this.npc.getBukkitEntity().getWorld() ? this.tar.getLocation().distanceSquared(this.npc.getBukkitEntity().getLocation()) : 150 * 150;
            if (this.npc.getNPC().getNavigator().getTargetAsLocation() == null || rand.nextInt(10) == 0) {
                for (UUID uuid : this.players) {
                    Player pl = Bukkit.getPlayer(uuid);
                    if (pl != null && pl.getWorld().getName().equals(this.npc.getBukkitEntity().getWorld().getName())) {
                        double dis = this.npc.getBukkitEntity().getLocation().distanceSquared(pl.getLocation());
                        if (dis < distance) {
                            this.tar = pl;
                            distance = dis;
                        }
                    }
                }
            }
            
            if (this.tar != null && !this.selfHealing) {
                double hp = this.npc.getBukkitEntity().getHealth();
                double humanhp = this.tar.getHealth();

                if (distance < 24 * 24 && distance > 5 * 5 && hp > humanhp && humanhp < 15 && System.currentTimeMillis() - lastPearl > 1000 * 10 && rand.nextInt(humanhp >= 1 ? (int) humanhp : 1) == 0) {
                    lastPearl = System.currentTimeMillis();
                    if (pearls > 0) {
                        pearls--;
                        Vector vel = tar.getLocation().subtract(0, 2, 0).toVector().subtract(npc.getBukkitEntity().getLocation().toVector());
                        double theta = Math.atan2(-vel.getX(), vel.getZ());
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                Util.assumePose(npc.getBukkitEntity(), (float) Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D), (float) 10);
                                Projectile proj = npc.getBukkitEntity().launchProjectile(EnderPearl.class);
                                proj.setMetadata("BOT_PEARL", new FixedMetadataValue(plugin, true));
                            }
                        }.runTask(plugin);
                    }
                }
                this.npc.getNPC().getNavigator().setPaused(false);
            }
            if (this.npc.getNPC().getNavigator().getTargetAsLocation() != null) {
                if (!this.npc.getBukkitEntity().isSprinting()) {
                    this.npc.getBukkitEntity().setSprinting(true);
                }
            }

            double swingDistance = this.attackRange + this.swingRangeModifier + rand.nextDouble() * 3;
            if (distance < swingDistance * swingDistance && !this.npc.getNPC().getNavigator().isPaused() && !this.selfHealing) {
                this.npc.swingMainArm();
            }
            if (!this.npc.getBukkitEntity().isDead()) {
                selfHealIfNeeded();
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
		for (Player o : Bukkit.getOnlinePlayers()) {
			if (!VanishManager.isVanished(o)) {
				tar.showPlayer(o);
			}
		}
        BotManager.remove(tar);
        this.tar = null;
    }

    public void giveKit(Kits kit) {
        Arrays.asList(kit.getItems()).stream().filter(i -> i != null && i.getType() == Material.ENDER_PEARL).forEach(i -> pearls += i.getAmount());
        new BukkitRunnable() {

            int counter = 4;

            @Override
            public void run() {
                if (CombatTask.this.npc.getBukkitEntity() != null) {
                    this.counter--;
                    if (this.counter == 0) {
                        npc.getNPC().addTrait(new Trait("strike_velocity") {

                            @Override
                            public boolean isRunImplemented() {
                                return true;
                            }

                            @Override
                            public void run() {
                                if (npc.getEntity() != null && !npc.getNavigator().isNavigating() && !npc.getEntity().isOnGround() && !isInLiquid(npc.getEntity())) {
                                    //plugin.getNMSAccessProvider().getAccess().invoke_g(this.npc.getEntity(), 0f, 0f);
                                }
                            }
                        });
                        cancel();
                    }
                    CombatTask.this.npc.getBukkitEntity().addPotionEffects(kit.getEffects());
                    CombatTask.this.npc.getBukkitEntity().getInventory().setContents(kit.getItems());
                    CombatTask.this.npc.getBukkitEntity().getInventory().setArmorContents(kit.getArmor());
                    CombatTask.this.npc.getBukkitEntity().setMetadata("IN_FIGHT", new FixedMetadataValue(CombatTask.this.plugin, true));
                    CombatTask.this.kit = true;
                }
            }
        }.runTaskTimer(Kit.getPlugin(), 20, 20);
    }

    private boolean isInLiquid(Entity ent) {
        Material mat = ent.getLocation().getBlock().getType();
        return mat != null && (mat == Material.WATER || mat == Material.STATIONARY_WATER || mat == Material.LAVA || mat == Material.STATIONARY_LAVA);
    }


    public void selfHealIfNeeded() {
        Random random = new Random();
        if (this.selfHealing) {
            return;
        }
        Damageable d = this.npc.getBukkitEntity();
        double hp = d.getHealth();
        if (hp < 14) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    int rand = random.nextInt(8);
                    if (hp <= rand) {
                        splashHeal();
                    } else {
                        if (hasGodApples()) {
                            if (gapple()) {
                                return;
                            }
                        } else {
                        	splashHeal();
                        }
                    }
                }
            }.runTask(plugin);
        }
    }

    private boolean hasGodApples() {
        for (ItemStack is : this.npc.getBukkitEntity().getInventory().getContents()) {
            if (is != null && is.getType() == Material.GOLDEN_APPLE) {
                if (is.getDurability() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean gapple() {
    	Random rand = new Random();
        ItemStack gapple = null;
        for (ItemStack is : this.npc.getBukkitEntity().getInventory().getContents()) {
            if (is != null && is.getType() == Material.GOLDEN_APPLE) {
                gapple = is.clone();
            }
        }
        if (gapple != null) {
            boolean godApple = gapple.getDurability() > 0;
            this.selfHealing = true;
            final ItemStack finalGapple = gapple;
            ItemStack hand = null;
            for (int i = 0; i < 9; i++) {
                if (this.npc.getBukkitEntity().getInventory().getItem(i) != null && this.npc.getBukkitEntity().getInventory().getItem(i).equals(gapple)) {
                    hand = this.npc.getBukkitEntity().getInventory().getItem(i);
                    this.npc.getBukkitEntity().getInventory().setHeldItemSlot(i);
                    break;
                }
            }
            if (hand == null) {
                this.npc.getBukkitEntity().getInventory().setHeldItemSlot(1);
                this.npc.getBukkitEntity().getInventory().remove(finalGapple);
                for (int i = 9; i < 36; i++) {
                    if (this.npc.getBukkitEntity().getInventory().getItem(i) == null || this.npc.getBukkitEntity().getInventory().getItem(i).getType() == Material.AIR) {
                        this.npc.getBukkitEntity().getInventory().setItem(i, this.npc.getBukkitEntity().getItemInHand());
                        break;
                    }
                }
                this.npc.getBukkitEntity().setItemInHand(gapple);
            }
            new BukkitRunnable() {

                @Override
                public void run() {
                    CombatTask.this.npc.getBukkitEntity().setItemInHand(finalGapple);
                    try {
                        Class<?> clz = PlayerAnimation.class;
                        clz.getField("START_USE_ITEM");
                        try {
                            PlayerAnimation.START_USE_ITEM.play(CombatTask.this.npc.getBukkitEntity());
                        } catch (NoSuchFieldError e) {
                        }
                        clz.getField("EAT_FOOD");
                        try {
                            PlayerAnimation.EAT_FOOD.play(CombatTask.this.npc.getBukkitEntity());
                        } catch (NoSuchFieldError e) {
                        }
                    } catch (Exception e) {
                    }
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (CombatTask.this.npc.getBukkitEntity() != null) {
                                CombatTask.this.npc.getNPC().getNavigator().setPaused(true);
                                finalGapple.setAmount(1);
                                if (!godApple) {
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 2, 1));
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60 * 2, 1));
                                } else {
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 4));
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60 * 2, 1));
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 5, 0));
                                    CombatTask.this.npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 60 * 5, 0));
                                }
                                if (!godApple) {
                                	CombatTask.this.npc.getBukkitEntity().getInventory().remove(new ItemStack(Material.GOLDEN_APPLE, finalGapple.getAmount()));
                                } else {
                                	CombatTask.this.npc.getBukkitEntity().getInventory().remove(new ItemStack(Material.GOLDEN_APPLE, finalGapple.getAmount(), (short)1));
                                }
                                CombatTask.this.npc.getBukkitEntity().getInventory().remove(finalGapple);
                                
                                new BukkitRunnable() {

                                    int counter = godApple ? 60 : 4;
                                    double appleHeals = godApple ? 8 : 2;

                                    @Override
                                    public void run() {
                                        if (npc.getBukkitEntity() == null) {
                                            cancel();
                                            return;
                                        }
                                        this.counter--;
                                        if (this.counter < 0) {
                                            cancel();
                                            npc.getBukkitEntity().removePotionEffect(PotionEffectType.REGENERATION);
                                        } else {
                                            if (CombatTask.this.npc != null && CombatTask.this.npc.getBukkitEntity() != null) {
                                                double hp = CombatTask.this.npc.getBukkitEntity().getHealth();
                                                double max = CombatTask.this.npc.getBukkitEntity().getMaxHealth();
                                                if (hp < max) {
                                                    double diff = max - hp;
                                                    double heal = diff > this.appleHeals ? this.appleHeals : diff;
                                                    double totalHeal = hp + heal;
                                                    if (totalHeal > max) {
                                                        totalHeal = max;
                                                    }
                                                    CombatTask.this.npc.getBukkitEntity().setHealth(totalHeal);
                                                }
                                            }
                                        }
                                    }
                                }.runTaskTimer(CombatTask.this.plugin, 4, 10);
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        if (CombatTask.this.npc.getNPC() != null && CombatTask.this.npc.isSpawned() && CombatTask.this.npc.getNPC().getNavigator() != null) {
                                            CombatTask.this.npc.getNPC().getNavigator().setPaused(false);
                                            CombatTask.this.npc.getBukkitEntity().getInventory().setHeldItemSlot(0);
                                            CombatTask.this.selfHealing = false;
                                        }
                                    }
                                }.runTaskLater(Kit.getPlugin(), 4);
                            }
                        }
                    }.runTaskLater(Kit.getPlugin(), 35);
                }
            }.runTaskLater(Kit.getPlugin(), rand.nextInt(2) + 1);
            return true;
        }
        return false;
    }


    private boolean splashHeal() {
    	Random rand = new Random();
        if (this.npc.getBukkitEntity() == null) {
            return true;
        }

        if (!this.npc.getBukkitEntity().isOnGround() && this.npc.getBukkitEntity().getLocation().getY() - this.npc.getBukkitEntity().getLocation().getBlockY() > 0.35) {
            if (rand.nextInt(3) == 0) {
                return true;
            }
        }
        ItemStack pot = getPot();
        if (pot == null) {
            return false;
        }
        this.selfHealing = true;
        final int[] toRemove = {0};
        npc.getBukkitEntity().getInventory().setHeldItemSlot(2);
        if (npc.getBukkitEntity().getItemInHand() == null || npc.getBukkitEntity().getItemInHand().getType() == Material.AIR) {
            toRemove[0]++;
            npc.getBukkitEntity().getInventory().setItemInHand(pot);
        }
        Location behind = this.npc.getBukkitEntity().getLocation().add(this.npc.getBukkitEntity().getLocation().getDirection().normalize().multiply(-5)).subtract(0, 10, 0);
        this.npc.getNPC().getNavigator().setTarget(behind);
        new BukkitRunnable() {

            int startCounter = rand.nextInt(5) + 5;
            int counter = this.startCounter;

            @Override
            public void run() {
                if (CombatTask.this.npc.getNPC() != null && CombatTask.this.npc.isSpawned() && CombatTask.this.npc.getNPC().getNavigator() != null) {
                    this.counter--;
                    if (this.counter == 0 || Math.abs(CombatTask.this.npc.getBukkitEntity().getLocation().getPitch() - 90) < 50) {
                        cancel();
                        CombatTask.this.npc.swingMainArm();
                        ThrownPotion thrownPotion = throwPotion(pot);
                        toRemove[0]++;
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (CombatTask.this.selfHealing && thrownPotion != null && CombatTask.this.npc.getNPC() != null && CombatTask.this.npc.getNPC().isSpawned() && !CombatTask.this.npc.getBukkitEntity().isDead() && !thrownPotion.isDead()) {
                                    CombatTask.this.npc.getNPC().getNavigator().setTarget(thrownPotion.getLocation());
                                } else {
                                    cancel();
                                }
                            }
                        }.runTaskTimer(CombatTask.this.plugin, 0, 1);
                        Damageable d = CombatTask.this.npc.getBukkitEntity();
                        Inventory inv = npc.getBukkitEntity().getInventory();
                        for (int i = 0; i < inv.getContents().length; i++) {
                            ItemStack is = inv.getItem(i);
                            if (is != null) {
                                if (is.getType() == Material.POTION && (is.getDurability() == 16421 || is.getDurability() == 16453)) {
                                    if (toRemove[0] > 0) {
                                        toRemove[0]--;
                                        inv.setItem(i, new ItemStack(Material.AIR));
                                    } else if (d.getHealth() < 4) {
                                        inv.setItem(i, new ItemStack(Material.AIR));
                                        throwPotion(pot);
                                        break;
                                    }
                                }
                            }
                        }
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (CombatTask.this.npc.getNPC() != null && CombatTask.this.npc.isSpawned() && CombatTask.this.npc.getNPC().getNavigator() != null) {
                                    CombatTask.this.npc.getBukkitEntity().getInventory().setHeldItemSlot(0);
                                    CombatTask.this.run();
                                }
                            }
                        }.runTaskLater(Kit.getPlugin(), 4);
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Kit.getPlugin(), 1, 1);
        return true;
    }

    private ItemStack getPot() {
        for (ItemStack is : this.npc.getBukkitEntity().getInventory().getContents()) {
            if (is != null) {
                if (is.getType() == Material.POTION && (is.getDurability() == 16421 || is.getDurability() == 16453 || is.getDurability() == 16389)) {
                    return is.clone();
                }
            }
        }
        return null;
    }

    private ThrownPotion throwPotion(ItemStack potion) {
    	Random rand = new Random();
    	ThrownPotion thrownPotion = (ThrownPotion) this.npc.getBukkitEntity().getWorld().spawnEntity(this.npc.getBukkitEntity().getEyeLocation(), EntityType.SPLASH_POTION);
        thrownPotion.getEffects().addAll(Potion.fromItemStack(potion).getEffects());
        thrownPotion.setItem(potion);
        Vector vec = this.npc.getBukkitEntity().getLocation().getDirection();
        if (vec.getY() == 0) {
            vec.setY(-rand.nextInt(2) + 1 + rand.nextDouble() / 10);
        }
        thrownPotion.setVelocity(vec);
        return thrownPotion;
    }
    
}

