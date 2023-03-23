package com.example.superheltv5.services;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.repositories.ISuperheroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

  private ISuperheroRepository repository;

  public SuperheroService(ApplicationContext context, @Value("${superhero.repository}") String impl) {
    repository = (ISuperheroRepository) context.getBean(impl);
  }

  public List<Superhero> getAll() {
    return repository.getAll();
  }

  public List<Superhero> getAll2() throws SuperheroException {
    return repository.getAll2();
  }

  public void save(Superhero hero) throws SuperheroException {
    repository.save(hero);
  }

  public void saveAll(List<Superhero> superheroes) throws SuperheroException {
    repository.saveall(superheroes);
  }
}
