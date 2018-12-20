package InventoryChess;

import java.util.ArrayList;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandManager implements CommandExecutor
{
  private Main plugin;
  
  public CommandManager(Main plugin)
  {
    this.plugin = plugin;
  }
  

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("inventorychess"))
    {
      if (!(sender instanceof Player)) { return true;
      }
      if (args.length == 0)
      {
        sender.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
        sender.sendMessage(ChatColor.AQUA + "Usage: '/inventorychess' or '/ic' + '<args> <optional args> <optional args> <optional args>'");
        sender.sendMessage(ChatColor.AQUA + "/ic challenge <player> <optional minutes> <optional increment> -- challenge someone");
        sender.sendMessage(ChatColor.AQUA + "/ic accept  --   accept someone's challenge");
        sender.sendMessage(ChatColor.AQUA + "/ic decline  --  decline a challenge");
        sender.sendMessage(ChatColor.AQUA + "/ic cancel  --  cancel a challenge");
        sender.sendMessage(ChatColor.AQUA + "/ic check  --  displays all your active games");
        sender.sendMessage(ChatColor.AQUA + "/ic return <game id>  --  return to a game of specified id");
        sender.sendMessage(ChatColor.GREEN + "Developed by coolmann24 in 2016, thanks for playing!");
        sender.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
        return true;
      }
      
      if (args[0].equalsIgnoreCase("decline"))
      {
        Player p1 = null;
        for (Challenge c : Main.challenges)
        {
          if (c.getChallenged().getName().equals(sender.getName()))
          {
            p1 = c.getChallenger();
            Main.challenges.remove(c);
            break;
          }
        }
        if (p1 == null)
        {
          sender.sendMessage(ChatColor.RED + "You don't have any pending InventoryChess challenges!");
          return true;
        }
        sender.sendMessage(ChatColor.GREEN + "Challenge from " + p1.getName() + " declined!");
        p1.sendMessage(ChatColor.RED + sender.getName() + " declined your challenge!");
        return true;
      }
      
      if (args[0].equalsIgnoreCase("cancel"))
      {
        Player p1 = null;
        for (Challenge c : Main.challenges)
        {
          if (c.getChallenger().getName().equals(sender.getName()))
          {
            p1 = c.getChallenged();
            Main.challenges.remove(c);
            break;
          }
        }
        if (p1 == null)
        {
          sender.sendMessage(ChatColor.RED + "You don't have any active InventoryChess challenges!");
          return true;
        }
        sender.sendMessage(ChatColor.GREEN + "Challenge to " + p1.getName() + " cancelled!");
        p1.sendMessage(ChatColor.RED + sender.getName() + " cancelled their challenge!");
      }
      
      if (args[0].equalsIgnoreCase("challenge"))
      {
        if (args.length == 1)
        {
          sender.sendMessage(ChatColor.RED + "Please specify who you want to challenge!");
          return true;
        }
        
        Player p = org.bukkit.Bukkit.getServer().getPlayer(args[1]);
        

        if (p == null)
        {
          sender.sendMessage(ChatColor.RED + "Player not found!");
          return true;
        }
        if (p.equals((Player)sender))
        {
          sender.sendMessage(ChatColor.RED + "You cannot challenge yourself!");
          return true;
        }
        
        for (Challenge c : Main.challenges)
        {
          if (c.getChallenged().equals(p))
          {
            sender.sendMessage(ChatColor.RED + p.getName() + " already has a pending challenge!");
            return true;
          }
        }
        
        if (args.length < 3)
        {
          sender.sendMessage(ChatColor.GREEN + "You have challenged " + p.getName() + " to InventoryChess!");
          p.sendMessage(ChatColor.GREEN + sender.getName() + " has challenged you to InventoryChess with the conditions of 10 minutes and 5 seconds increment for each side! Use '/inventorychess accept' to play!");
          Main.challenges.add(new Challenge((Player)sender, p));
          return true;
        }
        

        try
        {
          minutes = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
          int minutes;
          sender.sendMessage(ChatColor.RED + "Please enter an integer value for the number of minutes for each player that is greater than zero!");
          return true; }
        int minutes;
        if (minutes < 1)
        {
          sender.sendMessage(ChatColor.RED + "Please enter an integer value for the number of minutes for each player that is greater than zero!");
          return true;
        }
        
        if (args.length < 4)
        {
          sender.sendMessage(ChatColor.GREEN + "You have challenged " + p.getName() + " to InventoryChess!");
          p.sendMessage(ChatColor.GREEN + sender.getName() + " has challenged you to InventoryChess with conditions of " + minutes + " minutes and 5 seconds increment of each side! Use '/inventorychess accept' to play!");
          Main.challenges.add(new Challenge((Player)sender, p, minutes));
          return true;
        }
        
        try
        {
          increment = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e) {
          int increment;
          sender.sendMessage(ChatColor.RED + "Please enter an integer value for the number of seconds of increment for each player that is not negative!");
          return true; }
        int increment;
        if (increment < 0)
        {
          sender.sendMessage(ChatColor.RED + "Please enter an integer value for the number of seconds of increment for each player that is not negative!");
          return true;
        }
        sender.sendMessage(ChatColor.GREEN + "You have challenged " + p.getName() + " to InventoryChess!");
        p.sendMessage(ChatColor.GREEN + sender.getName() + " has challenged you to InventoryChess with conditions of " + minutes + " minutes and " + increment + " seconds increment of each side! Use '/inventorychess accept' to play!");
        Main.challenges.add(new Challenge((Player)sender, p, minutes, increment));
        return true;
      }
      if (args[0].equalsIgnoreCase("accept"))
      {
        Player p1 = null;
        int minutes = 0;
        int increment = 0;
        for (Challenge c : Main.challenges)
        {
          if (c.getChallenged().getName().equals(sender.getName()))
          {
            p1 = c.getChallenger();
            minutes = c.getMinutes();
            increment = c.getIncrement();
            Main.challenges.remove(c);
            break;
          }
        }
        if (p1 == null)
        {
          sender.sendMessage(ChatColor.RED + "You don't have any pending InventoryChess challenges!");
          return true;
        }
        
        int n = Main.random.nextInt(2);
        
        sender.sendMessage(ChatColor.GREEN + "You have accepted " + p1.getName() + "'s InventoryChess challenge! Game generating...");
        p1.sendMessage(ChatColor.GREEN + sender.getName() + " has accepted your InventoryChess challenge! Game Generating...");
        Game g;
        Game g; if (n == 0)
        {
          g = new Game(p1, (Player)sender, minutes, increment);
        }
        else
        {
          g = new Game((Player)sender, p1, minutes, increment);
        }
        if (!plugin.gamePlayedYet())
        {
          new ChessTimer(plugin).runTaskTimer(plugin, 1L, 20L);
          plugin.setGamePlayedYet(true);
        }
        Main.games.add(g);
        g.openBlackInventory();
        g.openWhiteInventory();
        return true;
      }
      if (args[0].equalsIgnoreCase("check"))
      {
        sender.sendMessage(Main.games.getGamesPlaying((Player)sender));
      }
      if (args[0].equalsIgnoreCase("return"))
      {
        if (args.length == 1)
        {
          sender.sendMessage(ChatColor.YELLOW + "Please specify which game you wish to return to!");
          return true;
        }
        int id = -1;
        try
        {
          id = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e)
        {
          sender.sendMessage(ChatColor.RED + "Please enter an integer value for the game id!");
          return true;
        }
        Game g = Main.games.getGame(id);
        if (g == null)
        {
          sender.sendMessage(ChatColor.RED + "A game of id " + id + " is not among your active games!");
          return true;
        }
        if (g.getWhite().getName().equals(sender.getName()))
        {
          sender.sendMessage(ChatColor.GREEN + "Opening game of id " + id + " for you to play!");
          g.openWhiteInventory();
          return true;
        }
        if (g.getBlack().getName().equals(sender.getName()))
        {
          sender.sendMessage(ChatColor.GREEN + "Opening game of id " + id + " for you to play!");
          g.openBlackInventory();
          return true;
        }
        

        sender.sendMessage(ChatColor.RED + "A game of id " + id + " is not among your active games!");
        return true;
      }
    }
    

    return true;
  }
}
