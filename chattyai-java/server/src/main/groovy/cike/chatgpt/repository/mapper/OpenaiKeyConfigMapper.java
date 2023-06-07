package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.OpenaiKeyConfig;
import cike.chatgpt.repository.entity.OpenaiKeyConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenaiKeyConfigMapper {

  long countByExample(OpenaiKeyConfigExample example);

  int deleteByExample(OpenaiKeyConfigExample example);

  int deleteByPrimaryKey(Long id);

  int insert(OpenaiKeyConfig record);

  int insertSelective(OpenaiKeyConfig record);

  List<OpenaiKeyConfig> selectByExample(OpenaiKeyConfigExample example);

  OpenaiKeyConfig selectByPrimaryKey(Long id);

  int updateByExampleSelective(@Param("record") OpenaiKeyConfig record,
      @Param("example") OpenaiKeyConfigExample example);

  int updateByExample(@Param("record") OpenaiKeyConfig record, @Param("example") OpenaiKeyConfigExample example);

  int updateByPrimaryKeySelective(OpenaiKeyConfig record);

  int updateByPrimaryKey(OpenaiKeyConfig record);
}