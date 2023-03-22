package com.example.superheltv5.services;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.repositories.SuperheroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

  private SuperheroRepository repo;

  public SuperheroService(ApplicationContext context, @Value("${superhero.repository}") String impl) {
    repo = (SuperheroRepository) context.getBean(impl);
  }

  public List<Superhero> getAll() {
    return repo.getAll();
  }

  public List<Superhero> getAll2() throws SuperheroException {
    return repo.getAll2();
  }

  // Dependency injection
 /* public SuperheroService(SuperheroRepository heroRepository) {
    this.repo = heroRepository;
  }*/
}
