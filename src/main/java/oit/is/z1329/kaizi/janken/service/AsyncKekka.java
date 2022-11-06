package oit.is.z1329.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1329.kaizi.janken.model.Match;
import oit.is.z1329.kaizi.janken.model.MatchInfo;
import oit.is.z1329.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1329.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Transactional
  public void syncInsertMatch(MatchInfo matchInfo, String user2Hand, boolean isActive) {
    Match match = new Match();
    match.setUser1(matchInfo.getUser1());
    match.setUser2(matchInfo.getUser2());
    match.setUser1Hand(matchInfo.getUser1Hand());
    match.setUser2Hand(user2Hand);
    match.setIsActive(isActive);
    matchMapper.insertMatch(match);
    dbUpdated = true;
  }

  @Transactional
  public void syncUpdateMatch(Match match, boolean isActive) {
    match.setIsActive(isActive);
    matchMapper.updateById(match);
  }

  @Async
  public void asyncShowMatch(SseEmitter emitter) {
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(1000);
          continue;
        }
        System.out.println("asyncShowMatch1");
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        Match activeMatch = null;
        ArrayList<Match> matches = matchMapper.selectAllMatches();
        for (Match match : matches) {
          if (match.getIsActive()) {
            System.out.println("asyncShowMatch2");
            activeMatch = match;
            emitter.send(activeMatch);
            break;
          }
        }

        System.out.println("asyncShowMatch3");
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;

        if (activeMatch != null) {
          System.out.println("asyncShowMatch4");
          this.syncUpdateMatch(activeMatch, false);
        }
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncMatch complete");
  }

}
