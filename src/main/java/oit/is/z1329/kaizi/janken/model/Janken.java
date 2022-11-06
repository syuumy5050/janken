package oit.is.z1329.kaizi.janken.model;

import java.util.Random;

public class Janken {
  private String user1Hand = "";
  private String user2Hand = "";
  private String result = "";

  public Janken(String hand) {
    this.user1Hand = hand;
    this.user2Hand = randomHand();
    this.result = battle();
  }

  public Janken(String user1Hand, String user2Hand) {
    this.user1Hand = user1Hand;
    this.user2Hand = user2Hand;
    this.result = battle();
  }

  public String battle() {
    if (this.user1Hand.equals(this.user2Hand)) {
      return "Draw";
    }
    if (this.user1Hand.equals("Gu")) {
      if (this.user2Hand.equals("Choki")) {
        return "You Win!";
      }
      if (this.user2Hand.equals("Pa")) {
        return "CPU Win!";
      }
    }
    if (this.user1Hand.equals("Choki")) {
      if (this.user2Hand.equals("Pa")) {
        return "You Win!";
      }
      if (this.user2Hand.equals("Gu")) {
        return "CPU Win!";
      }
    }
    if (this.user1Hand.equals("Pa")) {
      if (this.user2Hand.equals("Gu")) {
        return "You Win!";
      }
      if (this.user2Hand.equals("Choki")) {
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

  public String getUser1Hand() {
    return this.user1Hand;
  }

  public void setUser1Hand(String hand) {
    this.user1Hand = hand;
  }

  public String getUser2Hand() {
    return this.user2Hand;
  }

  public void setUser2Hand(String hand) {
    this.user2Hand = hand;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
