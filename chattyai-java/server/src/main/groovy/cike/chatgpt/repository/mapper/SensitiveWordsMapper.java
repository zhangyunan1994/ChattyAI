package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.SensitiveWords;
import cike.chatgpt.repository.entity.SensitiveWordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SensitiveWordsMapper {

  long countByExample(SensitiveWordsExample example);

  int deleteByExample(SensitiveWordsExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(SensitiveWords record);

  int insertSelective(SensitiveWords record);

  List<SensitiveWords> selectByExample(SensitiveWordsExample example);

  SensitiveWords selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") SensitiveWords record, @Param("example") SensitiveWordsExample example);

  int updateByExample(@Param("record") SensitiveWords record, @Param("example") SensitiveWordsExample example);

  int updateByPrimaryKeySelective(SensitiveWords record);

  int updateByPrimaryKey(SensitiveWords record);
}