package oit.is.z1329.kaizi.janken.controller;

// import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import oit.is.z1329.kaizi.janken.model.Janken;
import oit.is.z1329.kaizi.janken.model.Match;
import oit.is.z1329.kaizi.janken.model.MatchMapper;
import oit.is.z1329.kaizi.janken.model.User;
import oit.is.z1329.kaizi.janken.model.UserMapper;
import oit.is.z1329.kaizi.janken.model.Entry;

@Controller
public class JankenController {

  @Autowired
  Entry entry;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/janken")
  public String janken_get(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUser();
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  // DIコンテナ使ったもの
  // @GetMapping("/janken")
  // public String janken_get(ModelMap model, Principal prin) {
  // String loginUser = prin.getName();
  // this.entry.addUser(loginUser);
  // model.addAttribute("entry", this.entry);
  // return "janken.html";
  // }

  @PostMapping("/janken")
  public String janken_post(@RequestParam String userName, ModelMap model) {
    model.addAttribute("userName", userName);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("entry", this.entry);
    model.addAttribute("janken", janken);
    return "janken.html";
  }

}
