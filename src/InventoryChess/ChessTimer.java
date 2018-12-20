package InventoryChess;

import java.util.ArrayList;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class ChessTimer extends org.bukkit.scheduler.BukkitRunnable
{
  Main plugin;
  
  public ChessTimer(Main plugin)
  {
    this.plugin = plugin;
  }
  

  public void run()
  {
    for (int i = Main.challenges.size() - 1; i >= 0; i--)
    {
      if (((Challenge)Main.challenges.get(i)).getTimeLeft() == 0)
      {
        ((Challenge)Main.challenges.get(i)).getChallenger().sendMessage(ChatColor.RED + "Your InventoryChess challenge with " + ((Challenge)Main.challenges.get(i)).getChallenged().getName() + " has expired!");
        ((Challenge)Main.challenges.get(i)).getChallenged().sendMessage(ChatColor.RED + "Your InventoryChess challenge from " + ((Challenge)Main.challenges.get(i)).getChallenger().getName() + " has expired!");
        Main.challenges.remove(i);
      }
    }
    for (Challenge c : Main.challenges)
    {
      c.setTimeLeft(c.getTimeLeft() - 1);
    }
    
    Main.games.removeGameIfOutOfTime();
    Main.games.decreaseTimeLeft();
    
    Main.games.endGameIfOutOfTime();
    Main.games.increaseGameTime();
  }
}
