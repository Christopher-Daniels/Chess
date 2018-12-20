package InventoryChess;


public class Board
{
  private int[][] board;
  
  private GameState gamestate;
  
  private boolean whitecancastlekingside;
  private boolean blackcancastlekingside;
  private boolean whitecancastlequeenside;
  private boolean blackcancastlequeenside;
  private boolean lastmovepawntwospaces;
  private int lmptsrow;
  private int lmptscol;
  private int moveprogress;
  private int lastmovefromrow;
  private int lastmovefromcol;
  public static final int WHITEPAWN = 1;
  public static final int WHITEKNIGHT = 2;
  public static final int WHITEBISHOP = 3;
  public static final int WHITEROOK = 4;
  public static final int WHITEQUEEN = 5;
  public static final int WHITEKING = 6;
  public static final int BLACKPAWN = 11;
  public static final int BLACKKNIGHT = 12;
  public static final int BLACKBISHOP = 13;
  public static final int BLACKROOK = 14;
  public static final int BLACKQUEEN = 15;
  public static final int BLACKKING = 16;
  public static final int EMPTYSPACE = 0;
  
  public Board()
  {
    board = new int[8][8];
    
    gamestate = GameState.WHITEMOVE;
    
    lastmovepawntwospaces = false;
    lmptsrow = 0;
    lmptscol = 0;
    
    lastmovefromrow = -1;
    lastmovefromcol = -1;
    
    moveprogress = -1;
    
    whitecancastlekingside = true;
    blackcancastlekingside = true;
    whitecancastlequeenside = true;
    blackcancastlequeenside = true;
    
    for (int i = 0; i < 8; i++)
    {
      for (int j = 0; j < 8; j++)
      {
        board[i][j] = 0;
      }
    }
    
    board[0][0] = 14;
    board[0][1] = 12;
    board[0][2] = 13;
    board[0][3] = 15;
    board[0][4] = 16;
    board[0][5] = 13;
    board[0][6] = 12;
    board[0][7] = 14;
    
    for (int i = 0; i < 8; i++)
    {
      board[1][i] = 11;
    }
    
    for (int i = 0; i < 8; i++)
    {
      board[6][i] = 1;
    }
    
    board[7][0] = 4;
    board[7][1] = 2;
    board[7][2] = 3;
    board[7][3] = 5;
    board[7][4] = 6;
    board[7][5] = 3;
    board[7][6] = 2;
    board[7][7] = 4;
  }
  

  public Board(int[][] b, GameState g, boolean whk, boolean blk, boolean whq, boolean blq)
  {
    lastmovepawntwospaces = false;
    lmptsrow = 0;
    lmptscol = 0;
    
    lastmovefromrow = -1;
    lastmovefromcol = -1;
    
    for (int i = 0; i <= 7; i++)
    {
      for (int j = 0; j <= 7; j++)
      {
        board[i][j] = b[i][j];
      }
    }
    gamestate = g;
    whitecancastlekingside = whk;
    blackcancastlekingside = blk;
    whitecancastlequeenside = whq;
    blackcancastlequeenside = blq;
  }
  
  public void changeTurn() {
    if (gamestate.equals(GameState.WHITEMOVE))
    {
      gamestate = GameState.BLACKMOVE;
    }
    else
    {
      gamestate = GameState.WHITEMOVE;
    }
  }
  
  public GameState getState()
  {
    return gamestate;
  }
  
  public boolean isCheck() {
    return false;
  }
  
  public boolean isCheckAfterMove(int rowfrom, int colfrom, int rowto, int colto, boolean swapteam)
  {
    int[][] newboard = new int[8][8];
    String team = "";
    int currentrow = 0;
    int currentcol = 0;
    
    if (board[rowfrom][colfrom] == 0)
    {
      return false;
    }
    if (board[rowfrom][colfrom] < 10)
    {
      team = "b";
    }
    if (board[rowfrom][colfrom] > 10)
    {
      team = "w";
    }
    if (swapteam)
    {
      if (team.equals("w")) team = "b"; else {
        team = "w";
      }
    }
    
    for (int i = 0; i <= 7; i++)
    {
      for (int j = 0; j <= 7; j++)
      {
        newboard[i][j] = board[i][j];
      }
    }
    newboard[rowto][colto] = board[rowfrom][colfrom];
    if ((rowto != rowfrom) || (colto != colfrom))
    {
      newboard[rowfrom][colfrom] = 0;
    }
    if (board[rowfrom][colfrom] == 1)
    {
      if ((colfrom != colto) && (board[rowto][colto] == 0))
      {
        newboard[(rowto + 1)][colto] = 0;
      }
    }
    if (board[rowfrom][colfrom] == 11)
    {
      if ((colfrom != colto) && (board[rowto][colto] == 0))
      {
        newboard[(rowto - 1)][colto] = 0;
      }
    }
    
    if (isMoveCastle(rowfrom, colfrom, rowto, colto))
    {
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 6))
      {
        newboard[7][5] = 6;
        newboard[7][7] = 0;
      }
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 2))
      {
        newboard[7][3] = 6;
        newboard[7][0] = 0;
      }
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 6))
      {
        newboard[0][5] = 16;
        newboard[0][7] = 0;
      }
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 2))
      {
        newboard[0][3] = 16;
        newboard[0][0] = 0;
      }
    }
    
    for (int i = 0; i <= 7; i++)
    {
      for (int j = 0; j <= 7; j++)
      {
        currentrow = i;
        currentcol = j;
        
        if (newboard[i][j] != 0)
        {


          if ((newboard[i][j] < 10) && (team.equals("w")))
          {
            if ((newboard[i][j] == 3) || 
              (newboard[i][j] == 5))
            {
              do
              {
                currentrow++;
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow++;
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
            }
            
            if ((newboard[i][j] == 4) || 
              (newboard[i][j] == 5))
            {
              do
              {
                currentrow++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
            }
            
            if (newboard[i][j] == 2)
            {
              currentrow++;
              currentcol += 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol -= 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol += 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol -= 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow += 2;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow += 2;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow -= 2;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow -= 2;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
            if (newboard[i][j] == 1)
            {
              currentrow--;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
            
            if (newboard[i][j] == 6)
            {
              currentrow++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 16)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
          }
          if ((newboard[i][j] > 10) && (team.equals("b")))
          {
            if ((newboard[i][j] == 13) || 
              (newboard[i][j] == 15))
            {
              do
              {
                currentrow++;
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow++;
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
            }
            
            if ((newboard[i][j] == 14) || 
              (newboard[i][j] == 15))
            {
              do
              {
                currentrow++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentrow--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentcol++;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
              do
              {
                currentcol--;
                
                if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
                  break;
                }
                


                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              } while (newboard[currentrow][currentcol] == 0);
              






              currentrow = i;
              currentcol = j;
            }
            
            if (newboard[i][j] == 12)
            {
              currentrow++;
              currentcol += 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol -= 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol += 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol -= 2;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow += 2;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow += 2;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow -= 2;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow -= 2;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
            if (newboard[i][j] == 11)
            {
              currentrow++;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
            
            if (newboard[i][j] == 16)
            {
              currentrow++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow++;
              currentcol--;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
              
              currentrow--;
              currentcol++;
              if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
              {



                if (newboard[currentrow][currentcol] == 6)
                {
                  return true;
                }
              }
              currentrow = i;
              currentcol = j;
            }
          }
        }
      }
    }
    


    return false;
  }
  
  public boolean isCheckMate(String teamcheckmated) {
    for (int a = 0; a <= 7; a++)
    {
      for (int b = 0; b <= 7; b++)
      {
        if (board[a][b] != 0)
        {



          if ((board[a][b] < 10) && (teamcheckmated.equals("w")))
          {
            for (int c = 0; c <= 7; c++)
            {
              for (int d = 0; d <= 7; d++)
              {
                if ((isMovePossible(a, b, c, d)) && (isMoveLegal(a, b, c, d)))
                {
                  return false;
                }
              }
            }
          }
          if ((board[a][b] > 10) && (teamcheckmated.equals("b")))
          {
            for (int c = 0; c <= 7; c++)
            {
              for (int d = 0; d <= 7; d++)
              {
                if ((isMovePossible(a, b, c, d)) && (isMoveLegal(a, b, c, d)))
                {
                  return false;
                }
              }
            }
          }
        }
      }
    }
    
    return true;
  }
  
  public boolean isMovePossible(int rowfrom, int colfrom, int rowto, int colto)
  {
    if ((rowfrom == rowto) && (colfrom == colto))
    {
      return false;
    }
    if (board[rowfrom][colfrom] == 0)
    {
      return false;
    }
    
    if ((board[rowfrom][colfrom] < 10) && (board[rowto][colto] < 10))
    {
      if ((board[rowfrom][colfrom] > 0) && (board[rowto][colto] > 0))
        return false;
    }
    if ((board[rowfrom][colfrom] > 10) && (board[rowto][colto] > 10))
    {
      return false;
    }
    

    int currentrow = rowfrom;
    int currentcol = colfrom;
    


    if ((board[rowfrom][colfrom] == 3) || 
      (board[rowfrom][colfrom] == 5) || 
      (board[rowfrom][colfrom] == 13) || 
      (board[rowfrom][colfrom] == 15))
    {
      do
      {
        currentrow++;
        currentcol++;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentrow++;
        currentcol--;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentrow--;
        currentcol++;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentrow--;
        currentcol--;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
    }
    
    if ((board[rowfrom][colfrom] == 4) || 
      (board[rowfrom][colfrom] == 5) || 
      (board[rowfrom][colfrom] == 14) || 
      (board[rowfrom][colfrom] == 15))
    {
      do
      {
        currentrow++;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentrow--;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentcol++;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
      do
      {
        currentcol--;
        
        if ((currentrow < 0) || (currentrow > 7) || (currentcol < 0) || (currentcol > 7)) {
          break;
        }
        


        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      } while (board[currentrow][currentcol] == 0);
      






      currentrow = rowfrom;
      currentcol = colfrom;
    }
    
    if ((board[rowfrom][colfrom] == 2) || 
      (board[rowfrom][colfrom] == 12))
    {
      currentrow++;
      currentcol += 2;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow++;
      currentcol -= 2;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow--;
      currentcol += 2;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow--;
      currentcol -= 2;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow += 2;
      currentcol++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow += 2;
      currentcol--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow -= 2;
      currentcol++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow -= 2;
      currentcol--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
    }
    
    if (board[rowfrom][colfrom] == 1)
    {
      currentrow--;
      if (board[currentrow][currentcol] == 0)
      {
        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
        

        currentrow--;
        if ((rowfrom == 6) && (board[currentrow][currentcol] == 0))
        {
          if ((currentrow == rowto) && (currentcol == colto))
          {
            return true;
          }
        }
      }
      
      currentrow = rowfrom - 2;
      
      currentrow++;
      currentcol++;
      if ((currentcol >= 0) && (currentcol <= 7))
      {




        if ((currentrow == rowto) && (currentcol == colto) && ((board[currentrow][currentcol] > 0) || ((lastmovepawntwospaces) && (lmptsrow == currentrow) && (lmptscol == currentcol))))
        {
          return true;
        }
      }
      currentcol -= 2;
      if ((currentcol >= 0) && (currentcol <= 7))
      {




        if ((currentrow == rowto) && (currentcol == colto) && ((board[currentrow][currentcol] > 0) || ((lastmovepawntwospaces) && (lmptsrow == currentrow) && (lmptscol == currentcol))))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
    }
    
    if (board[rowfrom][colfrom] == 11)
    {
      currentrow++;
      if (board[currentrow][currentcol] == 0)
      {
        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
        

        currentrow++;
        if ((rowfrom == 1) && (board[currentrow][currentcol] == 0))
        {
          if ((currentrow == rowto) && (currentcol == colto))
          {
            return true;
          }
        }
      }
      
      currentrow = rowfrom + 2;
      
      currentrow--;
      currentcol++;
      if ((currentcol >= 0) && (currentcol <= 7))
      {




        if ((currentrow == rowto) && (currentcol == colto) && ((board[currentrow][currentcol] > 0) || ((lastmovepawntwospaces) && (lmptsrow == currentrow) && (lmptscol == currentcol))))
        {
          return true;
        }
      }
      currentcol -= 2;
      if ((currentcol >= 0) && (currentcol <= 7))
      {




        if ((currentrow == rowto) && (currentcol == colto) && ((board[currentrow][currentcol] > 0) || ((lastmovepawntwospaces) && (lmptsrow == currentrow) && (lmptscol == currentcol))))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
    }
    if ((board[rowfrom][colfrom] == 6) || 
      (board[rowfrom][colfrom] == 16))
    {
      currentrow++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentcol++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentcol--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow++;
      currentcol++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow--;
      currentcol--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow++;
      currentcol--;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      currentrow--;
      currentcol++;
      if ((currentrow >= 0) && (currentrow <= 7) && (currentcol >= 0) && (currentcol <= 7))
      {



        if ((currentrow == rowto) && (currentcol == colto))
        {
          return true;
        }
      }
      currentrow = rowfrom;
      currentcol = colfrom;
      
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 6) && (whitecancastlekingside))
      {
        if ((board[7][5] == 0) && (board[7][6] == 0))
        {
          return true;
        }
      }
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 2) && (whitecancastlequeenside))
      {
        if ((board[7][3] == 0) && (board[7][2] == 0) && (board[7][1] == 0))
        {
          return true;
        }
      }
      
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 6) && (blackcancastlekingside))
      {
        if ((board[0][5] == 0) && (board[0][6] == 0))
        {
          return true;
        }
      }
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 2) && (blackcancastlequeenside))
      {
        if ((board[0][3] == 0) && (board[0][2] == 0) && (board[0][1] == 0))
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean isMoveLegal(int rowfrom, int colfrom, int rowto, int colto)
  {
    if (isMoveCastle(rowfrom, colfrom, rowto, colto))
    {
      if (isCheckAfterMove(rowfrom, colfrom, rowfrom, colfrom, false))
      {
        return false;
      }
    }
    if (isCheckAfterMove(rowfrom, colfrom, rowto, colto, false))
    {
      return false;
    }
    return true;
  }
  
  public boolean isMoveCastle(int rowfrom, int colfrom, int rowto, int colto) {
    if ((board[rowfrom][colfrom] == 6) || (board[rowfrom][colfrom] == 16))
    {
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 6)) return true;
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 2)) return true;
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 6)) return true;
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 2)) return true;
    }
    return false;
  }
  
  public boolean whiteCanCastleKingSide() {
    return whitecancastlekingside;
  }
  
  public boolean blackCanCastleKingSide() {
    return blackcancastlekingside;
  }
  
  public boolean whiteCanCastleQueenSide() {
    return whitecancastlequeenside;
  }
  
  public boolean blackCanCastleQueenSide() {
    return blackcancastlequeenside;
  }
  
  public int getPiece(int row, int column) {
    return board[row][column];
  }
  
  public int getMoveProgress() {
    return moveprogress;
  }
  
  public void setMoveProgress(int progress) {
    moveprogress = progress;
  }
  
  public void makeMove(int rowfrom, int colfrom, int rowto, int colto) {
    lastmovefromrow = rowfrom;
    lastmovefromcol = colfrom;
    
    if (board[rowfrom][colfrom] == 1)
    {
      if ((colfrom != colto) && (board[rowto][colto] == 0))
      {
        board[(rowto + 1)][colto] = 0;
      }
    }
    if (board[rowfrom][colfrom] == 11)
    {
      if ((colfrom != colto) && (board[rowto][colto] == 0))
      {
        board[(rowto - 1)][colto] = 0;
      }
    }
    if (isMoveCastle(rowfrom, colfrom, rowto, colto))
    {
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 6))
      {
        board[7][5] = 4;
        board[7][7] = 0;
      }
      if ((rowfrom == 7) && (colfrom == 4) && (rowto == 7) && (colto == 2))
      {
        board[7][3] = 4;
        board[7][0] = 0;
      }
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 6))
      {
        board[0][5] = 14;
        board[0][7] = 0;
      }
      if ((rowfrom == 0) && (colfrom == 4) && (rowto == 0) && (colto == 2))
      {
        board[0][3] = 14;
        board[0][0] = 0;
      }
    }
    if (board[rowfrom][colfrom] == 6) setWhiteCantCastle();
    if (board[rowfrom][colfrom] == 16) setBlackCantCastle();
    board[rowto][colto] = board[rowfrom][colfrom];
    board[rowfrom][colfrom] = 0;
  }
  
  public void setState(GameState g) {
    gamestate = g;
  }
  
  public void setWhiteCantCastle() {
    whitecancastlekingside = false;
    whitecancastlequeenside = false;
  }
  
  public void setWhiteCantCastleKingside() {
    whitecancastlekingside = false;
  }
  
  public void setWhiteCantCastleQueenside() {
    whitecancastlequeenside = false;
  }
  
  public void setBlackCantCastle() {
    blackcancastlekingside = false;
    blackcancastlequeenside = false;
  }
  
  public void setBlackCantCastleKingside() {
    blackcancastlekingside = false;
  }
  
  public void setBlackCantCastleQueenside() {
    blackcancastlequeenside = false;
  }
  
  public void setLastMovePawnTwoSpaces(boolean is, int row, int col) {
    if (is)
    {
      lastmovepawntwospaces = true;
      lmptsrow = row;
      lmptscol = col;
    }
    else
    {
      lastmovepawntwospaces = false;
      lmptsrow = -1;
      lmptscol = -1;
    }
  }
  
  public void setPiece(int row, int col, int piece) {
    board[row][col] = piece;
  }
  
  public int getLastMoveFromRow() {
    return lastmovefromrow;
  }
  
  public int getLastMoveFromCol() {
    return lastmovefromcol;
  }
}
