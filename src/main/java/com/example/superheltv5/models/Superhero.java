package com.example.superheltv5.models;

public class Superhero {
  private int heroId;
  private String heroName;
  private String realName;
  private int creationYear;

  public Superhero() {
  }

  public Superhero(String heroName, String realName, int creationYear) {
    this.heroName = heroName;
    this.realName = realName;
    this.creationYear = creationYear;
  }

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

  public void setHeroId(int heroId) {
    this.heroId = heroId;
  }

  public void setHeroName(String heroName) {
    this.heroName = heroName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public void setCreationYear(int creationYear) {
    this.creationYear = creationYear;
  }
}

