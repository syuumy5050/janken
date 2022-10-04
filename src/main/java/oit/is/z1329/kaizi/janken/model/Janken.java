package oit.is.z1329.kaizi.janken.model;

import java.util.Random;

public class Janken {
  private String userHand = "";
  private String enemyHand = "";

  public Janken(String hand) {
    this.userHand = hand;
    this.enemyHand = randomHand();
  }

  public String battle() {
    if (this.userHand.equals(this.enemyHand)) {
      return "Draw!";
    }
    if (this.userHand.equals("Gu")) {
      if (this.enemyHand.equals("Tyoki")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Pa")) {
        return "You Lose...";
      }
    }
    if (this.userHand.equals("Tyoki")) {
      if (this.enemyHand.equals("Pa")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Gu")) {
        return "You Lose...";
      }
    }
    if (this.userHand.equals("Pa")) {
      if (this.enemyHand.equals("Gu")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Tyoki")) {
        return "You Lose...";
      }
    }
    return "";
  }

  public String randomHand() {
    Random random = new Random();
    int value = random.nextInt(3);
    if (value == 0) {
      return "Gu";
    }
    if (value == 1) {
      return "Tyoki";
    }
    if (value == 2) {
      return "Pa";
    }
    return "";
  }

  public String getUserHand() {
    return this.userHand;
  }

  public void setUserHand(String hand) {
    this.userHand = hand;
  }

  public String getEnemyHand() {
    return this.enemyHand;
  }

  public void setEnemyHand(String hand) {
    this.enemyHand = hand;
  }
}
