package com.example.superheltv5.models;

public class Superhero {
  private int heroId;
  private String heroName;
  private String realName;
  private int creationYear;

  public Superhero(int heroId, String heroName, String realName, int creationYear) {
    this.heroId = heroId;
    this.heroName = heroName;
    this.realName = realName;
    this.creationYear = creationYear;
  }

  public int getHeroId() {
    return heroId;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getRealName() {
    return realName;
  }

  public int getCreationYear() {
    return creationYear;
  }
}

