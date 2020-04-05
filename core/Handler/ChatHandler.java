package me.core.Handler;

public class ChatHandler {
	
	private static boolean chatToggled = false;
	  
	public static boolean isMuted() {
		return chatToggled;
	}
	  
	public static void setChatToggled(boolean chat) {
		chatToggled = chat;
	}
	  
}
