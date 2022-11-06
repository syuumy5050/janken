package oit.is.z1329.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.beans.factory.annotation.Autowired;

import oit.is.z1329.kaizi.janken.model.Janken;
import oit.is.z1329.kaizi.janken.model.Match;
import oit.is.z1329.kaizi.janken.model.MatchInfo;
import oit.is.z1329.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1329.kaizi.janken.model.MatchMapper;
import oit.is.z1329.kaizi.janken.model.User;
import oit.is.z1329.kaizi.janken.model.UserMapper;
import oit.is.z1329.kaizi.janken.service.AsyncKekka;
import oit.is.z1329.kaizi.janken.model.Entry;

@Controller
public class JankenController {

  @Autowired
  Entry entry;

  @Autowired
  AsyncKekka asyncKekka;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String janken_get(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUser();
    ArrayList<MatchInfo> matchInfo = matchInfoMapper.selectAllByIsActive(true);
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("users", users);
    model.addAttribute("matchInfo", matchInfo);
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
    User user2 = userMapper.selectById(id);
    model.addAttribute("user2", user2);
    return "match.html";
  }

  @GetMapping("/fight")
  @Transactional
  public String fight(@RequestParam Integer id, @RequestParam String hand, ModelMap model, Principal prin) {
    User user1 = userMapper.selectByName(prin.getName());
    User user2 = userMapper.selectById(id);

    boolean isFindMatchInfo = false;

    System.out.println("fight1");
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectAllByIsActive(true);
    for (MatchInfo mi : matchInfos) {
      if (mi.getUser1() == user2.getId()
          && mi.getUser2() == user1.getId()) {
        System.out.println("fight2");
        asyncKekka.syncInsertMatch(mi, hand, true);
        mi.setIsActive(false);
        matchInfoMapper.updateById(mi);
        isFindMatchInfo = true;
      }
    }

    System.out.println("fight3");
    if (!isFindMatchInfo) {
      System.out.println("fight4");
      MatchInfo matchInfo = new MatchInfo();
      matchInfo.setUser1(user1.getId());
      matchInfo.setUser2(user2.getId());
      matchInfo.setUser1Hand(hand);
      matchInfo.setIsActive(true);
      matchInfoMapper.insertMatchInfo(matchInfo);
    }

    return "wait.html";
  }

  @GetMapping("/wait")
  public SseEmitter waitEmitter() {
    System.out.println("wait1");
    final SseEmitter sseEmitter = new SseEmitter();
    asyncKekka.asyncShowMatch(sseEmitter);
    System.out.println("wait2");
    return sseEmitter;
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String hand, ModelMap model) {
    Janken janken = new Janken(hand);
    model.addAttribute("entry", this.entry);
    model.addAttribute("janken", janken);
    return "janken.html";
  }

}
