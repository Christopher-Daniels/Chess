package InventoryChess;

import java.util.ArrayList;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class Main extends org.bukkit.plugin.java.JavaPlugin
{
  public static GameList games;
  public static ArrayList<Challenge> challenges;
  public static Random random = new Random();
  private boolean gameplayedyet;
  
  public Main() {}
  
  public void onEnable()
  {
    games = new GameList(this);
    challenges = new ArrayList();
    gameplayedyet = false;
    
    Bukkit.getPluginManager().registerEvents(new ChessboardClick(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerLeaveEvent(this), this);
    

    getCommand("inventorychess").setExecutor(new CommandManager(this));
    
    Bukkit.getLogger().info("InventoryChess enabled!");
    
    getConfig().options().copyDefaults(true);
    saveConfig();
    reloadConfig();
  }
  
  public void onDisable() {
    games = null;
    challenges = null;
    random = null;
  }
  
  public static int[] convertWhiteNums(int invid)
  {
    int[] nums = new int[2];
    int checkid = invid + 1;
    checkid -= checkid / 9;
    checkid--;
    
    int row = checkid / 8;
    int col = checkid % 8;
    
    nums[0] = row;
    nums[1] = col;
    
    return nums;
  }
  
  public static int[] convertBlackNums(int invid) {
    return convertWhiteNums(70 - invid);
  }
  
  public static void inventoryChessBroadcast(String s) {
    Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "InventoryChess" + ChatColor.GOLD + "] " + ChatColor.BLUE + s);
  }
  
  public void executeCommandViaResult(Player p, int result)
  {
    String name = p.getName();
    String rawcommand = "";
    if (result == 2)
    {
      rawcommand = getConfig().getString("gamefinishcommands.playerwin");
    }
    else if (result == 1)
    {
      rawcommand = getConfig().getString("gamefinishcommands.playerlose");
    }
    else if (result == 0)
    {
      rawcommand = getConfig().getString("gamefinishcommands.playerdrawstalemate");
    }
    
    if (rawcommand.equals("")) { return;
    }
    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), rawcommand.replaceAll("@", name));
  }
  
  public boolean gamePlayedYet() {
    return gameplayedyet;
  }
  
  public void setGamePlayedYet(boolean b) {
    gameplayedyet = b;
  }
}
