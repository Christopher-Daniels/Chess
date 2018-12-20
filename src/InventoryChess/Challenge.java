package InventoryChess;

import org.bukkit.entity.Player;

public class Challenge
{
  private Player challenger;
  private Player challenged;
  private int timeleft;
  private int gameminutes;
  private int increment;
  
  public Challenge(Player challenger, Player challenged)
  {
    this.challenger = challenger;
    this.challenged = challenged;
    gameminutes = 10;
    increment = 5;
    
    timeleft = 30;
  }
  
  public Challenge(Player challenger, Player challenged, int gameminutes) {
    this.challenger = challenger;
    this.challenged = challenged;
    this.gameminutes = gameminutes;
    increment = 5;
    
    timeleft = 30;
  }
  
  public Challenge(Player challenger, Player challenged, int gameminutes, int increment) {
    this.challenger = challenger;
    this.challenged = challenged;
    this.gameminutes = gameminutes;
    this.increment = increment;
    
    timeleft = 30;
  }
  
  public Player getChallenger()
  {
    return challenger;
  }
  
  public Player getChallenged() {
    return challenged;
  }
  
  public int getMinutes() {
    return gameminutes;
  }
  
  public int getIncrement() {
    return increment;
  }
  
  public int getTimeLeft() {
    return timeleft;
  }
  
  public void setTimeLeft(int t) {
    timeleft = t;
  }
}
