package InventoryChess;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class GameList
{
  private ArrayList<Game> games;
  private Main plugin;
  
  public GameList(Main plugin)
  {
    this.plugin = plugin;
    games = new ArrayList();
  }
  
  public boolean isPlayerPlaying(Player p)
  {
    for (Game g : games)
    {
      if ((g.getWhite().equals(p)) || (g.getBlack().equals(p)))
      {
        return true;
      }
    }
    return false;
  }
  
  public void add(Game game) {
    games.add(game);
  }
  
  public String getGamesPlaying(Player p) {
    ArrayList<Integer> gameswithp = new ArrayList();
    for (Game g : games)
    {
      if ((p.getName().equals(g.getWhite().getName())) || (p.getName().equals(g.getBlack().getName())))
      {
        gameswithp.add(Integer.valueOf(g.getGameId()));
      }
    }
    if (gameswithp.size() == 0)
    {
      return ChatColor.RED + "You currently do not have any active games!";
    }
    

    String builder = ChatColor.AQUA + "Current Games: " + ChatColor.GOLD;
    for (Integer i : gameswithp)
    {
      if (getGame(i.intValue()).getWhite().getName().equals(p.getName()))
      {
        builder = builder + "id: " + i.intValue() + " vs. " + getGame(i.intValue()).getBlack().getName();
      }
      else
      {
        builder = builder + "id: " + i.intValue() + " vs. " + getGame(i.intValue()).getWhite().getName();
      }
      builder = builder + ", ";
    }
    return builder.substring(0, builder.length() - 2);
  }
  
  public Game getGame(int game)
  {
    for (Game g : games)
    {
      if (g.getGameId() == game)
      {
        return g;
      }
    }
    return null;
  }
  
  public void decreaseTimeLeft() {
    for (Game g : games)
    {
      if (g.getBoard().getState() == GameState.FINISHED) g.decreaseTimeLeft();
    }
  }
  
  public void removeGameIfOutOfTime() {
    for (int i = games.size() - 1; i >= 0; i--)
    {
      if (((Game)games.get(i)).getTimeLeft() == 0)
      {
        if (((Game)games.get(i)).whiteHasGuiOpen())
        {
          ((Game)games.get(i)).getWhite().closeInventory();
        }
        if (((Game)games.get(i)).blackHasGuiOpen())
        {
          ((Game)games.get(i)).getBlack().closeInventory();
        }
        games.remove(i);
      }
    }
  }
  
  public void increaseGameTime() {
    for (Game g : games)
    {
      if ((g.getBoard().getState() == GameState.WHITEMOVE) || (g.getBoard().getState() == GameState.WHITEPROMOTION)) { g.decreaseWhiteTime();
      }
      if ((g.getBoard().getState() == GameState.BLACKMOVE) || (g.getBoard().getState() == GameState.BLACKPROMOTION)) { g.decreaseBlackTime();
      }
      g.updateGameClocks();
    }
  }
  
  public void endGameIfOutOfTime() {
    for (Game g : games)
    {
      if (g.getBoard().getState() != GameState.FINISHED)
      {
        if (g.getWhiteTime() == 0)
        {
          g.getBoard().setState(GameState.FINISHED);
          g.getBlack().sendMessage(ChatColor.GREEN + g.getWhite().getName() + " just ran out of time and you won!");
          g.getWhite().sendMessage(ChatColor.RED + "You just ran out of time against " + g.getBlack().getName() + " and lost!");
          Main.inventoryChessBroadcast(g.getBlack().getName() + " just defeated " + g.getWhite().getName() + "!");
          g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
          g.getBlack().getWorld().spawnEntity(g.getBlack().getLocation(), EntityType.FIREWORK);
          g.teamSwapNotify();
          
          plugin.executeCommandViaResult(g.getBlack(), 2);
          plugin.executeCommandViaResult(g.getWhite(), 1);
        }
        else if (g.getBlackTime() == 0)
        {
          g.getBoard().setState(GameState.FINISHED);
          g.getWhite().sendMessage(ChatColor.GREEN + g.getBlack().getName() + " just ran out of time and you won!");
          g.getBlack().sendMessage(ChatColor.RED + "You just ran out of time against " + g.getWhite().getName() + " and lost!");
          Main.inventoryChessBroadcast(g.getWhite().getName() + " just defeated " + g.getBlack().getName() + "!");
          g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
          g.getWhite().getWorld().spawnEntity(g.getWhite().getLocation(), EntityType.FIREWORK);
          g.teamSwapNotify();
          
          plugin.executeCommandViaResult(g.getBlack(), 1);
          plugin.executeCommandViaResult(g.getWhite(), 2);
        }
      }
    }
  }
  
  public ArrayList<Game> getList() {
    return games;
  }
}
