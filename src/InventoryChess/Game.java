package InventoryChess;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;



public class Game
{
  private static int cgameid = 0;
  
  private DrawOfferState drawofferstate;
  
  private int gameid;
  private int timeafterend;
  private Board board;
  private Player white;
  private Player black;
  private int timewhite;
  private int timeblack;
  private int size;
  private int whiteresign;
  private int blackresign;
  private Inventory whiteinventory;
  private Inventory blackinventory;
  private int increment;
  private boolean whitetimegoesdown;
  private boolean blacktimegoesdown;
  
  public Game(Player w, Player b)
  {
    white = w;
    black = b;
    
    gameid = cgameid;
    cgameid += 1;
    
    timewhite = 600;
    timeblack = 600;
    
    increment = 5;
    
    whiteresign = 3;
    blackresign = 3;
    
    whitetimegoesdown = false;
    blacktimegoesdown = false;
    
    board = new Board();
    
    timeafterend = 5;
    
    size = 72;
    
    whiteinventory = Bukkit.getServer().createInventory(null, size, ChatColor.GOLD + "InventoryChess w#" + gameid);
    blackinventory = Bukkit.getServer().createInventory(null, size, ChatColor.GOLD + "InventoryChess b#" + gameid);
    
    drawofferstate = DrawOfferState.NONE;
    
    updateInventories();
    updateGameClocks();
    teamSwapNotify();
    addGameEndOptions();
  }
  
  public Game(Player w, Player b, int minutes, int increment)
  {
    white = w;
    black = b;
    
    gameid = cgameid;
    cgameid += 1;
    
    timewhite = (minutes * 60);
    timeblack = (minutes * 60);
    
    this.increment = increment;
    
    whiteresign = 3;
    blackresign = 3;
    
    whitetimegoesdown = false;
    blacktimegoesdown = false;
    
    board = new Board();
    
    timeafterend = 5;
    
    size = 72;
    
    whiteinventory = Bukkit.getServer().createInventory(null, size, ChatColor.GOLD + "InventoryChess w#" + gameid);
    blackinventory = Bukkit.getServer().createInventory(null, size, ChatColor.GOLD + "InventoryChess b#" + gameid);
    
    drawofferstate = DrawOfferState.NONE;
    
    updateInventories();
    updateGameClocks();
    teamSwapNotify();
    addGameEndOptions();
  }
  
  public void updateInventories()
  {
    for (int i = 0; i < size - size / 9; i++)
    {
      int whiteindex = i + i / 8;
      int blackindex = size - 2 - whiteindex;
      
      int row = i / 8;
      int column = i % 8;
      


      if (board.getPiece(row, column) == 0)
      {
        ItemStack itemstack = new ItemStack(Material.THIN_GLASS);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Empty Square");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 1)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "White Pawn");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 2)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "White Knight");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 3)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "White Bishop");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 4)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CREEPER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "White Rook");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 5)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_TOP));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "White Queen");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 6)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CREEPER));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "White King");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      


      if (board.getPiece(row, column) == 11)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "Black Pawn");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 12)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "Black Knight");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 13)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "Black Bishop");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 14)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
        
        meta.setDisplayName(ChatColor.AQUA + "Black Rook");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 15)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "Black Queen");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if (board.getPiece(row, column) == 16)
      {
        ItemStack itemstack = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
        
        meta.setBaseColor(DyeColor.SILVER);
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
        meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        
        meta.setDisplayName(ChatColor.AQUA + "Black King");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
      if ((row == board.getLastMoveFromRow()) && (column == board.getLastMoveFromCol()))
      {
        ItemStack itemstack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Empty Square");
        itemstack.setItemMeta(meta);
        
        whiteinventory.setItem(whiteindex, itemstack);
        blackinventory.setItem(blackindex, itemstack);
      }
    }
    
    updateInvIfPlayerHasGuiOpen();
  }
  
  public void updateGameClocks()
  {
    if (board.getState() == GameState.FINISHED) { return;
    }
    ItemStack i = new ItemStack(Material.WATCH, 1);
    ItemMeta it = i.getItemMeta();
    if (timewhite % 60 < 10)
    {
      it.setDisplayName(ChatColor.AQUA + "White's Time " + timewhite / 60 + ":0" + timewhite % 60);
    }
    else
    {
      it.setDisplayName(ChatColor.AQUA + "White's Time " + timewhite / 60 + ":" + timewhite % 60);
    }
    i.setItemMeta(it);
    if (board.getState() != GameState.WHITEPROMOTION)
    {
      whiteinventory.setItem(53, i);
    }
    blackinventory.setItem(26, i);
    
    ItemStack ib = new ItemStack(Material.WATCH, 1);
    ItemMeta itb = ib.getItemMeta();
    if (timeblack % 60 < 10)
    {
      itb.setDisplayName(ChatColor.AQUA + "Black's Time " + timeblack / 60 + ":0" + timeblack % 60);
    }
    else
    {
      itb.setDisplayName(ChatColor.AQUA + "Black's Time " + timeblack / 60 + ":" + timeblack % 60);
    }
    ib.setItemMeta(itb);
    if (board.getState() != GameState.BLACKPROMOTION)
    {
      blackinventory.setItem(53, ib);
    }
    whiteinventory.setItem(26, ib);
    
    updateInvIfPlayerHasGuiOpen();
  }
  
  public void teamSwapNotify()
  {
    ItemStack ilast = new ItemStack(Material.STAINED_CLAY, 1, (short)4);
    ItemStack i = new ItemStack(Material.STAINED_CLAY, 1, (short)5);
    ItemStack i2 = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
    



    if (board.getState() == GameState.WHITEMOVE)
    {
      ItemMeta i3 = i.getItemMeta();
      i3.setDisplayName(ChatColor.AQUA + "White's Move");
      i.setItemMeta(i3);
      
      ItemMeta i4 = i2.getItemMeta();
      i4.setDisplayName(ChatColor.AQUA + "White's Move");
      i2.setItemMeta(i4);
      
      whiteinventory.setItem(44, i);
      whiteinventory.setItem(35, i2);
      blackinventory.setItem(44, i2);
      blackinventory.setItem(35, i);
    }
    if (board.getState() == GameState.BLACKMOVE)
    {
      ItemMeta i3 = i.getItemMeta();
      i3.setDisplayName(ChatColor.AQUA + "Black's Move");
      i.setItemMeta(i3);
      
      ItemMeta i4 = i2.getItemMeta();
      i4.setDisplayName(ChatColor.AQUA + "Black's Move");
      i2.setItemMeta(i4);
      
      blackinventory.setItem(44, i);
      blackinventory.setItem(35, i2);
      whiteinventory.setItem(44, i2);
      whiteinventory.setItem(35, i);
    }
    if (board.getState() == GameState.FINISHED)
    {
      ItemMeta i5 = ilast.getItemMeta();
      i5.setDisplayName(ChatColor.AQUA + "Game is Finished");
      ilast.setItemMeta(i5);
      
      blackinventory.setItem(44, ilast);
      blackinventory.setItem(35, ilast);
      whiteinventory.setItem(44, ilast);
      whiteinventory.setItem(35, ilast);
    }
    updateInvIfPlayerHasGuiOpen();
  }
  

  public void addGameEndOptions()
  {
    ItemStack i = new ItemStack(Material.PAPER, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + "Click three times to resign");
    List<String> lore = new ArrayList();
    lore.add("or abort game if not started");
    im.setLore(lore);
    i.setItemMeta(im);
    
    if (board.getState() != GameState.WHITEPROMOTION)
    {
      whiteinventory.setItem(62, i);
    }
    whiteinventory.setItem(17, i);
    
    if (board.getState() != GameState.BLACKPROMOTION)
    {
      blackinventory.setItem(62, i);
    }
    blackinventory.setItem(17, i);
    
    updateDrawOfferStateVisible();
  }
  



  public void updateDrawOfferStateVisible()
  {
    ItemStack i1 = new ItemStack(Material.INK_SACK, 1, (short)10);
    ItemStack i2 = new ItemStack(Material.INK_SACK, 1, (short)1);
    ItemStack i3 = new ItemStack(Material.PAPER, 1);
    
    if (drawofferstate == DrawOfferState.WHITETOBLACK)
    {
      ItemMeta im1 = i1.getItemMeta();
      im1.setDisplayName(ChatColor.AQUA + "Accept Draw Offer");
      i1.setItemMeta(im1);
      
      ItemMeta im2 = i2.getItemMeta();
      im2.setDisplayName(ChatColor.AQUA + "Cancel Draw Offer");
      i2.setItemMeta(im2);
      
      if (board.getState() != GameState.WHITEPROMOTION)
      {
        whiteinventory.setItem(71, i2);
      }
      whiteinventory.setItem(8, i1);
      
      if (board.getState() != GameState.BLACKPROMOTION)
      {
        blackinventory.setItem(71, i1);
      }
      blackinventory.setItem(8, i2);
    }
    if (drawofferstate == DrawOfferState.BLACKTOWHITE)
    {
      ItemMeta im1 = i1.getItemMeta();
      im1.setDisplayName(ChatColor.AQUA + "Accept Draw Offer");
      i1.setItemMeta(im1);
      
      ItemMeta im2 = i2.getItemMeta();
      im2.setDisplayName(ChatColor.AQUA + "Cancel Draw Offer");
      i2.setItemMeta(im2);
      
      if (board.getState() != GameState.WHITEPROMOTION)
      {
        whiteinventory.setItem(71, i1);
      }
      whiteinventory.setItem(8, i2);
      
      if (board.getState() != GameState.BLACKPROMOTION)
      {
        blackinventory.setItem(71, i2);
      }
      blackinventory.setItem(8, i1);
    }
    if (drawofferstate == DrawOfferState.NONE)
    {
      i3 = new ItemStack(Material.PAPER, 1);
      ItemMeta im1 = i3.getItemMeta();
      im1.setDisplayName(ChatColor.AQUA + "Offer Draw");
      i3.setItemMeta(im1);
      
      if (board.getState() != GameState.WHITEPROMOTION)
      {
        whiteinventory.setItem(71, i3);
      }
      whiteinventory.setItem(8, i3);
      
      if (board.getState() != GameState.BLACKPROMOTION)
      {
        blackinventory.setItem(71, i3);
      }
      blackinventory.setItem(8, i3);
    }
    updateInvIfPlayerHasGuiOpen();
  }
  
  public void updatePromotionOptions()
  {
    if (board.getState() == GameState.WHITEPROMOTION)
    {
      ItemStack itemstack = new ItemStack(Material.BANNER);
      BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
      
      meta.setBaseColor(DyeColor.SILVER);
      meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_TOP));
      meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
      meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
      meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
      
      meta.setDisplayName(ChatColor.AQUA + "Promote to Queen");
      itemstack.setItemMeta(meta);
      
      whiteinventory.setItem(44, itemstack);
      
      ItemStack itemstack4 = new ItemStack(Material.BANNER);
      BannerMeta meta4 = (BannerMeta)itemstack4.getItemMeta();
      
      meta4.setBaseColor(DyeColor.SILVER);
      meta4.addPattern(new Pattern(DyeColor.WHITE, PatternType.CREEPER));
      meta4.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
      meta4.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
      
      meta4.setDisplayName(ChatColor.AQUA + "Promote to Rook");
      itemstack4.setItemMeta(meta4);
      
      whiteinventory.setItem(53, itemstack4);
      
      ItemStack itemstack3 = new ItemStack(Material.BANNER);
      BannerMeta meta3 = (BannerMeta)itemstack3.getItemMeta();
      
      meta3.setBaseColor(DyeColor.SILVER);
      meta3.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
      meta3.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
      
      meta3.setDisplayName(ChatColor.AQUA + "Promote to Bishop");
      itemstack3.setItemMeta(meta3);
      
      whiteinventory.setItem(62, itemstack3);
      
      ItemStack itemstack2 = new ItemStack(Material.BANNER);
      BannerMeta meta2 = (BannerMeta)itemstack2.getItemMeta();
      
      meta2.setBaseColor(DyeColor.SILVER);
      meta2.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
      meta2.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
      meta2.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
      meta2.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
      
      meta2.setDisplayName(ChatColor.AQUA + "Promote to Knight");
      itemstack2.setItemMeta(meta2);
      
      whiteinventory.setItem(71, itemstack2);
    }
    if (board.getState() == GameState.BLACKPROMOTION)
    {
      ItemStack itemstack = new ItemStack(Material.BANNER);
      BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
      
      meta.setBaseColor(DyeColor.SILVER);
      meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
      meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER));
      meta.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
      meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
      
      meta.setDisplayName(ChatColor.AQUA + "Promote to Queen");
      itemstack.setItemMeta(meta);
      
      blackinventory.setItem(44, itemstack);
      
      ItemStack itemstack2 = new ItemStack(Material.BANNER);
      BannerMeta meta2 = (BannerMeta)itemstack2.getItemMeta();
      
      meta2.setBaseColor(DyeColor.SILVER);
      meta2.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
      meta2.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
      meta2.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
      
      meta2.setDisplayName(ChatColor.AQUA + "Promote to Rook");
      itemstack2.setItemMeta(meta2);
      
      blackinventory.setItem(53, itemstack2);
      
      ItemStack itemstack3 = new ItemStack(Material.BANNER);
      BannerMeta meta3 = (BannerMeta)itemstack3.getItemMeta();
      
      meta3.setBaseColor(DyeColor.SILVER);
      meta3.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
      meta3.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
      
      meta3.setDisplayName(ChatColor.AQUA + "Promote to Bishop");
      itemstack3.setItemMeta(meta3);
      
      blackinventory.setItem(62, itemstack3);
      
      ItemStack itemstack4 = new ItemStack(Material.BANNER);
      BannerMeta meta4 = (BannerMeta)itemstack4.getItemMeta();
      
      meta4.setBaseColor(DyeColor.SILVER);
      meta4.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
      meta4.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
      meta4.addPattern(new Pattern(DyeColor.SILVER, PatternType.CURLY_BORDER));
      meta4.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
      
      meta4.setDisplayName(ChatColor.AQUA + "Promote to Knight");
      itemstack4.setItemMeta(meta4);
      blackinventory.setItem(71, itemstack4);
    }
  }
  
  public void decreaseWhiteResign()
  {
    whiteresign -= 1;
  }
  
  public void decreaseBlackResign()
  {
    blackresign -= 1;
  }
  
  public int getWhiteresign()
  {
    return whiteresign;
  }
  
  public int getBlackresign()
  {
    return blackresign;
  }
  
  public Player getWhite()
  {
    return white;
  }
  
  public Player getBlack()
  {
    return black;
  }
  
  public int getWhiteTime()
  {
    return timewhite;
  }
  
  public int getBlackTime() {
    return timeblack;
  }
  
  public void setWhiteTime(int x) {
    timewhite = x;
  }
  
  public void setBlackTime(int x) {
    timeblack = x;
  }
  
  public Board getBoard() {
    return board;
  }
  
  public void setBoard(Board b) {
    board = b;
  }
  
  public void openWhiteInventory()
  {
    white.openInventory(whiteinventory);
  }
  
  public void openBlackInventory() {
    black.openInventory(blackinventory);
  }
  
  public int getGameId() {
    return gameid;
  }
  
  public int getTimeLeft() {
    return timeafterend;
  }
  
  public void decreaseTimeLeft() {
    timeafterend -= 1;
  }
  
  public void decreaseWhiteTime() {
    if (whitetimegoesdown) timewhite -= 1;
  }
  
  public void decreaseBlackTime() {
    if (blacktimegoesdown) timeblack -= 1;
  }
  
  public void incrementWhiteTime() {
    timewhite += increment;
  }
  
  public void incrementBlackTime() {
    timeblack += increment;
  }
  
  public boolean whiteHasGuiOpen() {
    if (white == null) { return false;
    }
    InventoryView i = white.getOpenInventory();
    
    if (i == null) { return false;
    }
    Inventory gui = i.getTopInventory();
    
    String s = ChatColor.stripColor(gui.getName());
    
    if ((gui.getSize() == 72) && (s.equals(ChatColor.stripColor(whiteinventory.getName())))) { return true;
    }
    return false;
  }
  
  public boolean blackHasGuiOpen() {
    if (black == null) { return false;
    }
    InventoryView i = black.getOpenInventory();
    
    if (i == null) { return false;
    }
    Inventory gui = i.getTopInventory();
    
    String s = ChatColor.stripColor(gui.getName());
    
    if ((gui.getSize() == 72) && (s.equals(ChatColor.stripColor(blackinventory.getName())))) { return true;
    }
    return false;
  }
  
  public void updateInvIfPlayerHasGuiOpen()
  {
    if (whiteHasGuiOpen()) { white.updateInventory();
    }
    if (blackHasGuiOpen()) black.updateInventory();
  }
  
  public DrawOfferState getDrawOfferState()
  {
    return drawofferstate;
  }
  
  public void setDrawOfferState(DrawOfferState d)
  {
    drawofferstate = d;
  }
  
  public void setWhiteTimeGoesDown() {
    whitetimegoesdown = true;
  }
  
  public void setBlackTimeGoesDown() {
    blacktimegoesdown = true;
  }
  
  public boolean bothPlayersMovedYet() {
    if ((!whitetimegoesdown) || (!blacktimegoesdown)) return false;
    return true;
  }
}
