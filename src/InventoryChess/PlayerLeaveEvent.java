package InventoryChess;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements org.bukkit.event.Listener
{
  private Main plugin;
  
  public PlayerLeaveEvent(Main plugin)
  {
    this.plugin = plugin;
  }
  
  @org.bukkit.event.EventHandler
  public void onPlayerLeave(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    
    for (int i = Main.challenges.size() - 1; i >= 0; i--)
    {
      if (((Challenge)Main.challenges.get(i)).getChallenger().equals(p))
      {
        ((Challenge)Main.challenges.get(i)).getChallenged().sendMessage(ChatColor.RED + ((Challenge)Main.challenges.get(i)).getChallenger().getName() + " cancelled their challenge because they left!");
        Main.challenges.remove(i);
      }
      else if (((Challenge)Main.challenges.get(i)).getChallenged().equals(p))
      {
        ((Challenge)Main.challenges.get(i)).getChallenger().sendMessage(ChatColor.RED + "Challenge to " + ((Challenge)Main.challenges.get(i)).getChallenged().getName() + " cancelled because they left!");
        Main.challenges.remove(i);
      }
    }
    Game g = null;
    for (int i = Main.games.getList().size() - 1; i >= 0; i--)
    {
      if (((Game)Main.games.getList().get(i)).getWhite().equals(p))
      {
        g = (Game)Main.games.getList().get(i);
        
        g.getBoard().setState(GameState.FINISHED);
        
        g.getBlack().sendMessage(ChatColor.GREEN + g.getWhite().getName() + " just resigned against you because they left and you won!");
        Main.inventoryChessBroadcast(g.getBlack().getName() + " just defeated " + g.getWhite().getName() + "!");
        g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        g.getBlack().getWorld().spawnEntity(g.getBlack().getLocation(), EntityType.FIREWORK);
        g.teamSwapNotify();
        
        plugin.executeCommandViaResult(g.getBlack(), 2);
        plugin.executeCommandViaResult(g.getWhite(), 1);
      }
      else if (((Game)Main.games.getList().get(i)).getBlack().equals(p))
      {
        g = (Game)Main.games.getList().get(i);
        
        g.getBoard().setState(GameState.FINISHED);
        g.getWhite().sendMessage(ChatColor.GREEN + g.getBlack().getName() + " just resigned against you because they left and you won!");
        Main.inventoryChessBroadcast(g.getWhite().getName() + " just defeated " + g.getBlack().getName() + "!");
        g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        g.getWhite().getWorld().spawnEntity(g.getWhite().getLocation(), EntityType.FIREWORK);
        g.teamSwapNotify();
        
        plugin.executeCommandViaResult(g.getBlack(), 1);
        plugin.executeCommandViaResult(g.getWhite(), 2);
      }
    }
  }
}
