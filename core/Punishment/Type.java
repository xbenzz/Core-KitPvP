package me.core.Punishment;

import org.bukkit.Material;

public enum Type {

	MUTE("Mute", null, null), 
	BAN("Ban", null, null),
	IP("IPBan", null, null),
	CHAT("Temporary Mute", "Mute", Material.BOOK_AND_QUILL), 
	GAMEPLAY("Temporary Ban", "Ban", Material.IRON_SWORD), 
	PERM_BAN("Permanent Ban", "Ban", Material.IRON_SWORD), 
	PERM_MUTE("Permanent Mute", "Mute", Material.BOOK_AND_QUILL),
	IPBAN("Permanent IP-Ban", "IPBan", Material.REDSTONE_BLOCK);
	
	private String value;
	private String name;
	private Material material;
	
	private Type(String value, String name, Material material) {
		this.value = value;
		this.name = name;
		this.material = material;
	}
	
	public String value() {
		return value;
	}
	
	public String desc() {
		return name;
	}
	
	public Material mat() {
		return material;
	}
}
