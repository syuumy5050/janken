package oit.is.z1329.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * FROM matchinfo;")
  ArrayList<MatchInfo> selectAllMatchInfo();

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Select("SELECT * FROM matchinfo WHERE isActive = #{isActive};")
  ArrayList<MatchInfo> selectAllByIsActive(boolean isActive);

  @Select("SELECT * FROM matchinfo WHERE user1 = #{user1} AND user2 = #{user2} AND isActive = #{isActive}")
  ArrayList<MatchInfo> selectMatchInfo(int user1, int user2, boolean isActive);

  @Update("UPDATE matchinfo SET user1 = #{user1}, user2 = #{user2}, isActive = #{isActive} WHERE id = #{id};")
  void updateById(MatchInfo matchInfo);
}
