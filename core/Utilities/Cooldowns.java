package me.core.Utilities;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class Cooldowns
{
  private static HashMap<String, HashMap<UUID, Long>> cooldowns = new HashMap<String, HashMap<UUID, Long>>();
  
  public static void deleteCooldowns()
  {
    cooldowns.clear();
  }
  
  public static void createCooldown(String cooldown)
  {
    if (cooldowns.containsKey(cooldown)) {
      throw new IllegalArgumentException("Sorry, but cooldown doesn't exists.");
    }
    cooldowns.put(cooldown, new HashMap<UUID, Long>());
  }
  
  public static HashMap<UUID, Long> getCooldownMap(String cooldown)
  {
    if (cooldowns.containsKey(cooldown)) {
      return (HashMap<UUID, Long>)cooldowns.get(cooldown);
    }
    return null;
  }
  
  public static void addCooldown(String cooldown, Player p, int seconds)
  {
    if (!cooldowns.containsKey(cooldown)) { 
      throw new IllegalArgumentException(String.valueOf(cooldown) + " doesn't exists.");
    }
    long next = System.currentTimeMillis() + seconds * 1000L;
    ((HashMap<UUID, Long>)cooldowns.get(cooldown)).put(p.getUniqueId(), Long.valueOf(next));
  }
  
  public static boolean isOnCooldown(String cooldown, Player p)
  {
    return (cooldowns.containsKey(cooldown)) && (((HashMap<?, ?>)cooldowns.get(cooldown)).containsKey(p.getUniqueId())) && (System.currentTimeMillis() <= ((Long)((HashMap<?, ?>)cooldowns.get(cooldown)).get(p.getUniqueId())).longValue());
  }
  
  public static int getCooldownInt(String cooldown, Player p)
  {
    return (int)((((Long)((HashMap<?, ?>)cooldowns.get(cooldown)).get(p.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000L);
  }
  
  public static long getCooldownLong(String cooldown, Player p)
  {
    return ((Long)((HashMap<?, ?>)cooldowns.get(cooldown)).get(p.getUniqueId())).longValue() - System.currentTimeMillis();
  }
  
  public static void removeCooldown(String k, Player p)
  {
    if (!cooldowns.containsKey(k)) {
      throw new IllegalArgumentException(String.valueOf(k) + " doesn't exists.");
    }
    ((HashMap<?, ?>)cooldowns.get(k)).remove(p.getUniqueId());
  }
}
