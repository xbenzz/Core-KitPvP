package me.core.Rank;

import org.bukkit.ChatColor;

public enum Rank {
	
  DEFAULT("User", ChatColor.GRAY.toString(), ChatColor.GRAY, 0),  
  VIP("Vip", "[VIP] ", ChatColor.GREEN, 1),  
  ELITE("Elite", "[ELITE] ", ChatColor.AQUA, 2),  
  MVP("MVP", "[MVP] ", ChatColor.LIGHT_PURPLE, 3),  
  YT("YouTube", "[YT] ", ChatColor.GOLD, 4),   
  BUILDER("Builder", "[BUILDER] ", ChatColor.BLUE, 5),   
  HELPER("Helper", "[HELPER] ", ChatColor.DARK_GREEN, 6),  
  MODERATOR("Moderator", "[MOD] ", ChatColor.DARK_AQUA, 7),  
  SENIOR_MOD("SrMod", "[SR MOD] ", ChatColor.DARK_PURPLE, 8), 
  ADMIN("Admin", "[ADMIN] ", ChatColor.RED, 9), 
  OWNER("Owner", "[OWNER] ", ChatColor.RED, 10);
  
  private String name;
  private String prefix;
  private ChatColor color;
  private int id;
  
  private Rank(String name, String prefix, ChatColor color, int id) {
    this.name = name;
    this.prefix = prefix;
    this.color = color;
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPrefix() {
    return this.prefix;
  } 
  
  public ChatColor getColor() {
    return this.color;
  }
  
  public int getId() {
    return this.id;
  }
  
  public boolean isAboveOrEqual(Rank rank) {
    return ordinal() >= rank.ordinal();
  }
  
  public boolean isBelowOrEqual(Rank rank) {
    return ordinal() <= rank.ordinal();
  }
  
  public static Rank getRankOrDefault(String rankName) {
    Rank rank;
    try {
      rank = valueOf(rankName.toUpperCase());
    } catch (Exception e) {
      rank = DEFAULT;
    }
    return rank;
  }
  
}
