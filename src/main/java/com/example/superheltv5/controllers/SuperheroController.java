package com.example.superheltv5.controllers;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;
import com.example.superheltv5.services.SuperheroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

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

  // Thymeleaf template is used to display error message
  @ExceptionHandler(SuperheroException.class)
  public String handleError(Model model, Exception exception) {
    model.addAttribute("message",exception.getMessage());
    return "errorPage";
  }
}
