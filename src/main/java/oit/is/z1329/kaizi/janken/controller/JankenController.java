package oit.is.z1329.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken_get() {
    return "janken.html";
  }

  @PostMapping("/janken")
  public String janken_post(@RequestParam String userName, ModelMap model) {
    model.addAttribute("userName", userName);
    return janken_get();
  }

}
