package oit.is.z1329.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import oit.is.z1329.kaizi.janken.model.Janken;
import oit.is.z1329.kaizi.janken.model.Match;
import oit.is.z1329.kaizi.janken.model.MatchInfo;
import oit.is.z1329.kaizi.janken.model.MatchInfoMapper;
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

  @Autowired
  MatchInfoMapper matchInfoMapper;

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

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {
    User user2 = userMapper.selectUserById(id);
    model.addAttribute("user2", user2);
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam Integer id, @RequestParam String hand, ModelMap model, Principal prin) {
    User user1 = userMapper.selectUserByName(prin.getName());
    User user2 = userMapper.selectUserById(id);
    MatchInfo matchInfo = new MatchInfo();

    matchInfo.setUser1(user1.getId());
    matchInfo.setUser2(user2.getId());
    matchInfo.setUser1Hand(hand);
    matchInfo.setIsActive(true);
    matchInfoMapper.insertMatchInfo(matchInfo);

    return "wait.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("entry", this.entry);
    model.addAttribute("janken", janken);
    return "janken.html";
  }

}
