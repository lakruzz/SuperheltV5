package com.example.superheltv5.services;

import com.example.superheltv5.repositories.SuperheroRepository;

public class SuperheroService {

  private SuperheroRepository repo = null;

  // Dependency injection
  public SuperheroService(SuperheroRepository heroRepository) {
    this.repo = heroRepository;
  }
}
