package com.example.superheltv5.repositories;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;

import java.util.List;

public interface ISuperheroRepository {

  public List<Superhero> getAll();
  public List<Superhero> getAll2() throws SuperheroException;
  public void save(Superhero hero) throws SuperheroException;
  void saveall(List<Superhero> superheroes) throws SuperheroException;
}
