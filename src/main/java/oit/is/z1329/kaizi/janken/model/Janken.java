package oit.is.z1329.kaizi.janken.model;

import java.util.Random;

public class Janken {
  private String userHand = "";
  private String enemyHand = "";
  private String result = "";

  public Janken(String hand) {
    this.userHand = hand;
    this.enemyHand = randomHand();
    this.result = battle();
  }

  public String battle() {
    if (this.userHand.equals(this.enemyHand)) {
      return "Draw";
    }
    if (this.userHand.equals("Gu")) {
      if (this.enemyHand.equals("Choki")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Pa")) {
        return "CPU Win!";
      }
    }
    if (this.userHand.equals("Choki")) {
      if (this.enemyHand.equals("Pa")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Gu")) {
        return "CPU Win!";
      }
    }
    if (this.userHand.equals("Pa")) {
      if (this.enemyHand.equals("Gu")) {
        return "You Win!";
      }
      if (this.enemyHand.equals("Choki")) {
        return "CPU Win!";
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
      return "Choki";
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

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
