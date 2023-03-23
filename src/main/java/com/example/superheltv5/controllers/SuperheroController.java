package com.example.superheltv5.controllers;

import com.example.superheltv5.dto.SuperheroesCreationDto;
import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;
import com.example.superheltv5.services.SuperheroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("superhero")
public class SuperheroController {

  private SuperheroService service;

  public SuperheroController(SuperheroService service) {
    this.service = service;
  }

  @RequestMapping("/all")
  public String getAll(Model model) {
    List<Superhero> heroList = service.getAll();
    model.addAttribute("result", heroList);
    return "list";
  }

  @RequestMapping("/all2")
  public String getAll2(Model model) throws SuperheroException {
    List<Superhero> heroList = service.getAll2();
    model.addAttribute("result", heroList);
    return "list";
  }

  @GetMapping("/create")
  public String showForm(Model model) {
    //create new empty Person object
    model.addAttribute("hero", new Superhero());
    return "createHeroForm";
  }

  @PostMapping("/save")
  public String heroSave(@ModelAttribute Superhero hero, Model model) throws SuperheroException {
    //save populated Superhero object in database
    service.save(hero);

    //find hero again in database and show updated list
    List<Superhero> heroList = service.getAll2();
    model.addAttribute("result", heroList);
    return "list";
  }

  @GetMapping("/create2")
  public String showCreateForm(Model model) {
    SuperheroesCreationDto heroesForm = new SuperheroesCreationDto();

    for (int i = 1; i <= 3; i++) {
      heroesForm.addHero(new Superhero());
    }

    model.addAttribute("form", heroesForm);
    return "createHeroForm2";
  }

  @PostMapping("/save2")
  public String saveHeroes(@ModelAttribute SuperheroesCreationDto form, Model model) throws SuperheroException {
    System.out.println(form.getSuperheroes().size());
    service.saveAll(form.getSuperheroes());
    model.addAttribute("result", service.getAll2());
    //return "redirect:/books/all";
    return "redirect:/superhero/all2";
  }

  // Thymeleaf template is used to display error message
  @ExceptionHandler(SuperheroException.class)
  public String handleError(Model model, Exception exception) {
    model.addAttribute("message",exception.getMessage());
    return "errorPage";
  }
}
