package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.UserRole;
import cike.chatgpt.repository.entity.UserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {

  long countByExample(UserRoleExample example);

  int deleteByExample(UserRoleExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(UserRole record);

  int insertSelective(UserRole record);

  List<UserRole> selectByExample(UserRoleExample example);

  UserRole selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

  int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

  int updateByPrimaryKeySelective(UserRole record);

  int updateByPrimaryKey(UserRole record);
}