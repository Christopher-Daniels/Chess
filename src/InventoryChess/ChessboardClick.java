package InventoryChess;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class ChessboardClick implements org.bukkit.event.Listener
{
  private Main plugin;
  
  public ChessboardClick(Main plugin)
  {
    this.plugin = plugin;
  }
  
  @org.bukkit.event.EventHandler
  public void onInventoryClick(InventoryClickEvent e)
  {
    if (e.getClickedInventory() == null) { return;
    }
    String name = ChatColor.stripColor(e.getClickedInventory().getName());
    
    String name2 = ChatColor.stripColor(e.getWhoClicked().getOpenInventory().getTopInventory().getName());
    
    if (name.length() <= 14) { return;
    }
    int idclicked = e.getSlot();
    
    if (name2.length() >= 15)
    {
      if ((name2.substring(0, 14).equalsIgnoreCase("inventorychess")) && (e.getWhoClicked().getOpenInventory().getTopInventory().getSize() == 72))
      {
        e.setCancelled(true);
      }
    }
    
    if ((name.substring(0, 14).equalsIgnoreCase("inventorychess")) && (e.getClickedInventory().getSize() == 72))
    {
      e.setCancelled(true);
      
      if ((idclicked < 0) || (idclicked > 71)) { return;
      }
      String team = name.substring(15, 16);
      int gameid = Integer.parseInt(name.substring(17));
      Game g = Main.games.getGame(gameid);
      
      if (g == null) { return;
      }
      if (g.getBoard().getState() == GameState.FINISHED) { return;
      }
      

      if (team.equals("w"))
      {
        if (!g.getWhite().getName().equals(e.getWhoClicked().getName())) { return;
        }
      }
      if (team.equals("b"))
      {
        if (!g.getBlack().getName().equals(e.getWhoClicked().getName())) { return;
        }
      }
      if ((idclicked + 1) % 9 == 0)
      {
        if (team.equals("w"))
        {
          if (g.getBoard().getState() == GameState.WHITEPROMOTION)
          {
            int row = -1;
            int col = -1;
            
            if (idclicked == 44)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(0, i) == 1)
                {
                  g.getBoard().setPiece(0, i, 5);
                  row = 0;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.BLACKMOVE);
              g.incrementWhiteTime();
            }
            if (idclicked == 53)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(0, i) == 1)
                {
                  g.getBoard().setPiece(0, i, 4);
                  row = 0;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.BLACKMOVE);
              g.incrementWhiteTime();
            }
            if (idclicked == 62)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(0, i) == 1)
                {
                  g.getBoard().setPiece(0, i, 3);
                  row = 0;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.BLACKMOVE);
              g.incrementWhiteTime();
            }
            if (idclicked == 71)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(0, i) == 1)
                {
                  g.getBoard().setPiece(0, i, 2);
                  row = 0;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.BLACKMOVE);
              g.incrementWhiteTime();
            }
            
            if ((row != -1) || (col != -1))
            {


              if (g.getBoard().isCheckAfterMove(row, col, row, col, true))
              {
                if (g.getBoard().isCheckMate("b"))
                {
                  g.getBoard().setState(GameState.FINISHED);
                  g.getWhite().sendMessage(ChatColor.GREEN + "You just checkmated " + g.getBlack().getName() + " and won!");
                  g.getBlack().sendMessage(ChatColor.RED + g.getWhite().getName() + " just checkmated you and you lost!");
                  Main.inventoryChessBroadcast(g.getWhite().getName() + " just checkmated " + g.getBlack().getName() + "!");
                  g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                  g.getWhite().getWorld().spawnEntity(g.getWhite().getLocation(), EntityType.FIREWORK);
                  g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                  
                  plugin.executeCommandViaResult(g.getBlack(), 1);
                  plugin.executeCommandViaResult(g.getWhite(), 2);
                }
                else
                {
                  g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                  g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                }
              }
              else if (g.getBoard().isCheckMate("b"))
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getWhite().sendMessage(ChatColor.GOLD + "You just stalemated " + g.getBlack().getName() + ".");
                g.getBlack().sendMessage(ChatColor.GOLD + g.getWhite().getName() + "just stalemated you.");
                Main.inventoryChessBroadcast(g.getWhite().getName() + " just stalemated " + g.getBlack().getName() + ".");
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
                
                plugin.executeCommandViaResult(g.getBlack(), 0);
                plugin.executeCommandViaResult(g.getWhite(), 0);
              }
              else
              {
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
              }
            }
            
            g.updateInventories();
            g.addGameEndOptions();
            g.teamSwapNotify();
            g.updateGameClocks();
            return;
          }
          
          if (idclicked == 62)
          {
            g.decreaseWhiteResign();
            if (g.getWhiteresign() == 0)
            {
              if (g.bothPlayersMovedYet())
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getBlack().sendMessage(ChatColor.GREEN + g.getWhite().getName() + " just resigned against you and you won!");
                g.getWhite().sendMessage(ChatColor.RED + "You just resigned against " + g.getBlack().getName() + " and lost!");
                Main.inventoryChessBroadcast(g.getBlack().getName() + " just defeated " + g.getWhite().getName() + "!");
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                g.getBlack().getWorld().spawnEntity(g.getBlack().getLocation(), EntityType.FIREWORK);
                g.teamSwapNotify();
                
                plugin.executeCommandViaResult(g.getBlack(), 2);
                plugin.executeCommandViaResult(g.getWhite(), 1);
                return;
              }
              

              g.getBoard().setState(GameState.FINISHED);
              g.getBlack().sendMessage(ChatColor.GOLD + g.getWhite().getName() + " just aborted the game!");
              g.getWhite().sendMessage(ChatColor.GOLD + "You just aborted the game against " + g.getBlack().getName() + "!");
              g.teamSwapNotify();
              return;
            }
          }
          
          if (idclicked == 71)
          {
            if (g.getDrawOfferState() == DrawOfferState.BLACKTOWHITE)
            {
              g.getBoard().setState(GameState.FINISHED);
              g.getBlack().sendMessage(ChatColor.YELLOW + g.getWhite().getName() + " accepted you draw offer.");
              g.getWhite().sendMessage(ChatColor.YELLOW + "You just accepted " + g.getBlack().getName() + "'s draw offer");
              Main.inventoryChessBroadcast(g.getWhite().getName() + " drew against " + g.getBlack().getName() + ".");
              g.teamSwapNotify();
              
              plugin.executeCommandViaResult(g.getBlack(), 0);
              plugin.executeCommandViaResult(g.getWhite(), 0);
              return;
            }
            if (g.getDrawOfferState() == DrawOfferState.WHITETOBLACK)
            {
              g.setDrawOfferState(DrawOfferState.NONE);
            }
            else if (g.getDrawOfferState() == DrawOfferState.NONE)
            {
              g.setDrawOfferState(DrawOfferState.WHITETOBLACK);
            }
            g.updateDrawOfferStateVisible();
            return;
          }
        }
        if (team.equals("b"))
        {
          if (g.getBoard().getState() == GameState.BLACKPROMOTION)
          {
            int row = -1;
            int col = -1;
            
            if (idclicked == 44)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(7, i) == 11)
                {
                  g.getBoard().setPiece(7, i, 15);
                  row = 7;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.WHITEMOVE);
              g.incrementBlackTime();
            }
            if (idclicked == 53)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(7, i) == 11)
                {
                  g.getBoard().setPiece(7, i, 14);
                  row = 7;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.WHITEMOVE);
              g.incrementWhiteTime();
            }
            if (idclicked == 62)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(7, i) == 11)
                {
                  g.getBoard().setPiece(7, i, 13);
                  row = 7;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.WHITEMOVE);
              g.incrementWhiteTime();
            }
            if (idclicked == 71)
            {
              for (int i = 0; i <= 7; i++)
              {
                if (g.getBoard().getPiece(7, i) == 11)
                {
                  g.getBoard().setPiece(7, i, 12);
                  row = 7;
                  col = i;
                }
              }
              g.getBoard().setState(GameState.WHITEMOVE);
              g.incrementWhiteTime();
            }
            
            if ((row != -1) || (col != -1))
            {



              if (g.getBoard().isCheckAfterMove(row, col, row, col, true))
              {
                if (g.getBoard().isCheckMate("w"))
                {
                  g.getBoard().setState(GameState.FINISHED);
                  g.getBlack().sendMessage(ChatColor.GREEN + "You just checkmated " + g.getWhite().getName() + " and won!");
                  g.getWhite().sendMessage(ChatColor.RED + g.getBlack().getName() + " just checkmated you and you lost!");
                  Main.inventoryChessBroadcast(g.getBlack().getName() + " just checkmated " + g.getWhite().getName() + "!");
                  g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                  g.getBlack().getWorld().spawnEntity(g.getBlack().getLocation(), EntityType.FIREWORK);
                  g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                  
                  plugin.executeCommandViaResult(g.getBlack(), 2);
                  plugin.executeCommandViaResult(g.getWhite(), 1);
                }
                else
                {
                  g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                  g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                }
              }
              else if (g.getBoard().isCheckMate("w"))
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getBlack().sendMessage(ChatColor.GOLD + "You just stalemated " + g.getWhite().getName() + ".");
                g.getWhite().sendMessage(ChatColor.GOLD + g.getBlack().getName() + "just stalemated you.");
                Main.inventoryChessBroadcast(g.getBlack().getName() + " just stalemated " + g.getWhite().getName() + ".");
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
                
                plugin.executeCommandViaResult(g.getBlack(), 0);
                plugin.executeCommandViaResult(g.getWhite(), 0);
              }
              else
              {
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
              }
            }
            
            g.updateInventories();
            g.addGameEndOptions();
            g.teamSwapNotify();
            g.updateGameClocks();
            return;
          }
          
          if (idclicked == 62)
          {
            g.decreaseBlackResign();
            if (g.getBlackresign() == 0)
            {
              if (g.bothPlayersMovedYet())
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getWhite().sendMessage(ChatColor.GREEN + g.getBlack().getName() + " just resigned against you and you won!");
                g.getBlack().sendMessage(ChatColor.RED + "You just resigned against " + g.getWhite().getName() + " and lost!");
                Main.inventoryChessBroadcast(g.getWhite().getName() + " just defeated " + g.getBlack().getName() + "!");
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                g.getWhite().getWorld().spawnEntity(g.getWhite().getLocation(), EntityType.FIREWORK);
                g.teamSwapNotify();
                
                plugin.executeCommandViaResult(g.getBlack(), 1);
                plugin.executeCommandViaResult(g.getWhite(), 2);
                return;
              }
              

              g.getBoard().setState(GameState.FINISHED);
              g.getWhite().sendMessage(ChatColor.GOLD + g.getBlack().getName() + " just aborted the game!");
              g.getBlack().sendMessage(ChatColor.GOLD + "You just aborted the game against " + g.getWhite().getName() + "!");
              g.teamSwapNotify();
              return;
            }
          }
          
          if (idclicked == 71)
          {
            if (g.getDrawOfferState() == DrawOfferState.WHITETOBLACK)
            {
              g.getBoard().setState(GameState.FINISHED);
              g.getWhite().sendMessage(ChatColor.YELLOW + g.getBlack().getName() + " accepted you draw offer.");
              g.getBlack().sendMessage(ChatColor.YELLOW + "You just accepted " + g.getWhite().getName() + "'s draw offer");
              Main.inventoryChessBroadcast(g.getBlack().getName() + " drew against " + g.getWhite().getName() + ".");
              g.teamSwapNotify();
              
              plugin.executeCommandViaResult(g.getBlack(), 0);
              plugin.executeCommandViaResult(g.getWhite(), 0);
              return;
            }
            if (g.getDrawOfferState() == DrawOfferState.BLACKTOWHITE)
            {
              g.setDrawOfferState(DrawOfferState.NONE);
            }
            else if (g.getDrawOfferState() == DrawOfferState.NONE)
            {
              g.setDrawOfferState(DrawOfferState.BLACKTOWHITE);
            }
            g.updateDrawOfferStateVisible();
            return;
          }
        }
        return;
      }
      if ((g.getBoard().getState() == GameState.WHITEPROMOTION) || (g.getBoard().getState() == GameState.BLACKPROMOTION)) { return;
      }
      
      if ((team.equals("w")) && (g.getBoard().getState() == GameState.BLACKMOVE)) { return;
      }
      if ((team.equals("b")) && (g.getBoard().getState() == GameState.WHITEMOVE)) { return;
      }
      if (g.getBoard().getMoveProgress() == -1)
      {



        if (team.equals("w"))
        {
          int[] coords = Main.convertWhiteNums(idclicked);
          if (g.getBoard().getPiece(coords[0], coords[1]) == 0)
          {
            g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            return;
          }
          

          if (g.getBoard().getPiece(coords[0], coords[1]) < 10)
          {
            g.getBoard().setMoveProgress(idclicked);
            g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
          }
          else
          {
            g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          }
        }
        
        if (team.equals("b"))
        {
          int[] coords = Main.convertBlackNums(idclicked);
          if (g.getBoard().getPiece(coords[0], coords[1]) == 0)
          {
            g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            return;
          }
          

          if (g.getBoard().getPiece(coords[0], coords[1]) > 10)
          {
            g.getBoard().setMoveProgress(idclicked);
            g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
          }
          else
          {
            g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          }
          
        }
      }
      else
      {
        if (team.equals("w"))
        {
          int[] spotfrom = Main.convertWhiteNums(g.getBoard().getMoveProgress());
          int[] spotto = Main.convertWhiteNums(idclicked);
          
          if ((g.getBoard().isMovePossible(spotfrom[0], spotfrom[1], spotto[0], spotto[1])) && (g.getBoard().isMoveLegal(spotfrom[0], spotfrom[1], spotto[0], spotto[1])))
          {
            g.getBoard().makeMove(spotfrom[0], spotfrom[1], spotto[0], spotto[1]);
            g.getBoard().setState(GameState.BLACKMOVE);
            g.getBoard().setMoveProgress(-1);
            g.getBoard().setLastMovePawnTwoSpaces(false, -1, -1);
            g.setWhiteTimeGoesDown();
            
            if (g.getBoard().getPiece(spotto[0], spotto[1]) == 1)
            {
              if ((spotfrom[0] == 6) && (spotto[0] == 4))
              {
                g.getBoard().setLastMovePawnTwoSpaces(true, 5, spotto[1]);
              }
              if (spotto[0] == 0)
              {

                g.getBoard().setState(GameState.WHITEPROMOTION);
                g.updatePromotionOptions();
                g.updateInventories();
                g.updateGameClocks();
                g.teamSwapNotify();
                return;
              }
              

              g.incrementWhiteTime();

            }
            else
            {
              g.incrementWhiteTime();
            }
            if (spotfrom[0] == 7)
            {
              if (spotfrom[1] == 0) g.getBoard().setWhiteCantCastleQueenside();
              if (spotfrom[1] == 7) { g.getBoard().setWhiteCantCastleKingside();
              }
            }
            if (g.getBoard().isCheckAfterMove(spotto[0], spotto[1], spotto[0], spotto[1], true))
            {
              if (g.getBoard().isCheckMate("b"))
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getWhite().sendMessage(ChatColor.GREEN + "You just checkmated " + g.getBlack().getName() + " and won!");
                g.getBlack().sendMessage(ChatColor.RED + g.getWhite().getName() + " just checkmated you and you lost!");
                Main.inventoryChessBroadcast(g.getWhite().getName() + " just checkmated " + g.getBlack().getName() + "!");
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                g.getWhite().getWorld().spawnEntity(g.getWhite().getLocation(), EntityType.FIREWORK);
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                
                plugin.executeCommandViaResult(g.getBlack(), 1);
                plugin.executeCommandViaResult(g.getWhite(), 2);
              }
              else
              {
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              }
            }
            else if (g.getBoard().isCheckMate("b"))
            {
              g.getBoard().setState(GameState.FINISHED);
              g.getWhite().sendMessage(ChatColor.GOLD + "You just stalemated " + g.getBlack().getName() + ".");
              g.getBlack().sendMessage(ChatColor.GOLD + g.getWhite().getName() + "just stalemated you.");
              Main.inventoryChessBroadcast(g.getWhite().getName() + " just stalemated " + g.getBlack().getName() + ".");
              g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              g.getBlack().playSound(g.getBlack().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
              
              plugin.executeCommandViaResult(g.getBlack(), 0);
              plugin.executeCommandViaResult(g.getWhite(), 0);
            }
            else
            {
              g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              g.getBlack().playSound(g.getBlack().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
            }
            g.updateInventories();
            g.updateGameClocks();
            g.teamSwapNotify();
          }
          else
          {
            g.getBoard().setMoveProgress(-1);
            g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          }
        }
        if (team.equals("b"))
        {
          int[] spotfrom = Main.convertBlackNums(g.getBoard().getMoveProgress());
          int[] spotto = Main.convertBlackNums(idclicked);
          
          if ((g.getBoard().isMovePossible(spotfrom[0], spotfrom[1], spotto[0], spotto[1])) && (g.getBoard().isMoveLegal(spotfrom[0], spotfrom[1], spotto[0], spotto[1])))
          {
            g.getBoard().makeMove(spotfrom[0], spotfrom[1], spotto[0], spotto[1]);
            g.getBoard().setState(GameState.WHITEMOVE);
            g.getBoard().setMoveProgress(-1);
            g.getBoard().setLastMovePawnTwoSpaces(false, -1, -1);
            g.setBlackTimeGoesDown();
            
            if (g.getBoard().getPiece(spotto[0], spotto[1]) == 11)
            {
              if ((spotfrom[0] == 1) && (spotto[0] == 3))
              {
                g.getBoard().setLastMovePawnTwoSpaces(true, 2, spotto[1]);
              }
              if (spotto[0] == 7)
              {

                g.getBoard().setState(GameState.BLACKPROMOTION);
                g.updatePromotionOptions();
                g.updateInventories();
                g.updateGameClocks();
                g.teamSwapNotify();
                return;
              }
              

              g.incrementBlackTime();

            }
            else
            {
              g.incrementBlackTime();
            }
            if (spotfrom[0] == 0)
            {
              if (spotfrom[1] == 0) g.getBoard().setBlackCantCastleQueenside();
              if (spotfrom[1] == 7) { g.getBoard().setBlackCantCastleKingside();
              }
            }
            if (g.getBoard().isCheckAfterMove(spotto[0], spotto[1], spotto[0], spotto[1], true))
            {
              if (g.getBoard().isCheckMate("w"))
              {
                g.getBoard().setState(GameState.FINISHED);
                g.getBlack().sendMessage(ChatColor.GREEN + "You just checkmated " + g.getWhite().getName() + " and won!");
                g.getWhite().sendMessage(ChatColor.RED + g.getBlack().getName() + " just checkmated you and you lost!");
                Main.inventoryChessBroadcast(g.getBlack().getName() + " just checkmated " + g.getWhite().getName() + "!");
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                g.getBlack().getWorld().spawnEntity(g.getBlack().getLocation(), EntityType.FIREWORK);
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                
                plugin.executeCommandViaResult(g.getBlack(), 2);
                plugin.executeCommandViaResult(g.getWhite(), 1);
              }
              else
              {
                g.getWhite().playSound(g.getWhite().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              }
            }
            else if (g.getBoard().isCheckMate("w"))
            {
              g.getBoard().setState(GameState.FINISHED);
              g.getBlack().sendMessage(ChatColor.GOLD + "You just stalemated " + g.getWhite().getName() + ".");
              g.getWhite().sendMessage(ChatColor.GOLD + g.getBlack().getName() + "just stalemated you.");
              Main.inventoryChessBroadcast(g.getBlack().getName() + " just stalemated " + g.getWhite().getName() + ".");
              g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              g.getWhite().playSound(g.getWhite().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
              
              plugin.executeCommandViaResult(g.getBlack(), 0);
              plugin.executeCommandViaResult(g.getWhite(), 0);
            }
            else
            {
              g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
              g.getWhite().playSound(g.getWhite().getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F);
            }
            g.updateInventories();
            g.updateGameClocks();
            g.teamSwapNotify();
          }
          else
          {
            g.getBoard().setMoveProgress(-1);
            g.getBlack().playSound(g.getBlack().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          }
        }
      }
    }
  }
}
