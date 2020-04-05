package me.core.Utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HotbarUtils
{
  private static Class<?> CRAFTPLAYERCLASS;
  private static Class<?> PACKET_PLAYER_CHAT_CLASS;
  private static Class<?> ICHATCOMP;
  private static Class<?> CHATMESSAGE;
  private static Class<?> PACKET_CLASS;
  private static Class<?> CHAT_MESSAGE_TYPE_CLASS;
  private static Field PLAYERCONNECTION;
  private static Method GETHANDLE;
  private static Method SENDPACKET;
  private static Constructor<?> PACKET_PLAYER_CHAT_CONSTRUCTOR;
  private static Constructor<?> CHATMESSAGE_CONSTRUCTOR;
  private static Object CHAT_MESSAGE_TYPE_ENUM_OBJECT;
  private static final String SERVER_VERSION;
  
  static
  {
    String name = Bukkit.getServer().getClass().getName();
    name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
    name = name.substring(0, name.indexOf("."));
    SERVER_VERSION = name;
    try
    { 
      CRAFTPLAYERCLASS = Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION + ".entity.CraftPlayer");
      PACKET_PLAYER_CHAT_CLASS = Class.forName("net.minecraft.server." + SERVER_VERSION + ".PacketPlayOutChat");
      PACKET_CLASS = Class.forName("net.minecraft.server." + SERVER_VERSION + ".Packet");
      ICHATCOMP = Class.forName("net.minecraft.server." + SERVER_VERSION + ".IChatBaseComponent");
      GETHANDLE = CRAFTPLAYERCLASS.getMethod("getHandle", new Class[0]);
      PLAYERCONNECTION = GETHANDLE.getReturnType().getField("playerConnection");
      SENDPACKET = PLAYERCONNECTION.getType().getMethod("sendPacket", new Class[] { PACKET_CLASS });
      try
      {
        PACKET_PLAYER_CHAT_CONSTRUCTOR = PACKET_PLAYER_CHAT_CLASS.getConstructor(new Class[] { ICHATCOMP, Byte.TYPE });
      }
      catch (NoSuchMethodException e)
      {
        CHAT_MESSAGE_TYPE_CLASS = Class.forName("net.minecraft.server." + SERVER_VERSION + ".ChatMessageType");
        CHAT_MESSAGE_TYPE_ENUM_OBJECT = CHAT_MESSAGE_TYPE_CLASS.getEnumConstants()[2];
        
        PACKET_PLAYER_CHAT_CONSTRUCTOR = PACKET_PLAYER_CHAT_CLASS.getConstructor(new Class[] { ICHATCOMP, 
          CHAT_MESSAGE_TYPE_CLASS });
      }
      CHATMESSAGE = Class.forName("net.minecraft.server." + SERVER_VERSION + ".ChatMessage");
      
      CHATMESSAGE_CONSTRUCTOR = CHATMESSAGE.getConstructor(new Class[] { String.class, Object[].class });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void sendHotBarMessage(Player player, String message)
  {
    try
    {
      Object icb = CHATMESSAGE_CONSTRUCTOR.newInstance(new Object[] { message, new Object[0] });
      Object packet;
      try
      {
        packet = PACKET_PLAYER_CHAT_CONSTRUCTOR.newInstance(new Object[] { icb, Byte.valueOf((byte) 2) });
      }
      catch (Exception e)
      {
        packet = PACKET_PLAYER_CHAT_CONSTRUCTOR.newInstance(new Object[] { icb, CHAT_MESSAGE_TYPE_ENUM_OBJECT });
      }
      Object craftplayerInst = CRAFTPLAYERCLASS.cast(player);
      
      Object methodhHandle = GETHANDLE.invoke(craftplayerInst, new Object[0]);
      
      Object playerConnection = PLAYERCONNECTION.get(methodhHandle);
      
      SENDPACKET.invoke(playerConnection, new Object[] { packet });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
