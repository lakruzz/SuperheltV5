package com.example.superheltv5.controllers;

import com.example.superheltv5.repositories.SuperheroRepository;
import com.example.superheltv5.services.SuperheroException;
import com.example.superheltv5.services.SuperheroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("superhero")
public class SuperheroController {

  //private SuperheroRepository repo;
  private SuperheroService service;

  public SuperheroController(SuperheroService service) {
    this.service = service;
  }

 /*public SuperheroController(ApplicationContext context, @Value("${superhero.repository}") String impl) {
    repo = (SuperheroRepository) context.getBean(impl);
  }*/

  @RequestMapping("/all")
  public String getAll() {
    System.out.println(service.getAll().size());
    return "list";
  }

  @RequestMapping("/all2")
  public String getAll2() throws SuperheroException {
    System.out.println(service.getAll2().size());
    return "list";
  }

  @ExceptionHandler(SuperheroException.class)
  public String handleError(Model model, Exception exception) {
    model.addAttribute("message",exception.getMessage());
    return "errorPage";

  }

  @RequestMapping("/404.html")
  public String render404(Model model) {
    System.out.println("HTTP 404");
    model.addAttribute("message","not found, tine");
    // Add model attributes
    return "404";
  }
}
