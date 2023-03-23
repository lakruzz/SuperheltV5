package com.example.superheltv5.dto;

import com.example.superheltv5.models.Superhero;

import java.util.ArrayList;
import java.util.List;

public class SuperheroesCreationDto {

  private List<Superhero> superheroes;

  public SuperheroesCreationDto() {
    superheroes = new ArrayList<>();
  }

  public SuperheroesCreationDto(List<Superhero> superheroes) {
    this.superheroes = superheroes;
  }

  public void addHero(Superhero superhero) {
    this.superheroes.add(superhero);
  }

  public List<Superhero> getSuperheroes() {
    return superheroes;
  }

  public void setSuperheroes(List<Superhero> superheroes) {
    this.superheroes = superheroes;
  }
}
