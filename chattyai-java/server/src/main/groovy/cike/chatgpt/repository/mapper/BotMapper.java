package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.Bot;
import cike.chatgpt.repository.entity.BotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotMapper {
    long countByExample(BotExample example);

    int deleteByExample(BotExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bot record);

    int insertSelective(Bot record);

    List<Bot> selectByExample(BotExample example);

    Bot selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bot record, @Param("example") BotExample example);

    int updateByExample(@Param("record") Bot record, @Param("example") BotExample example);

    int updateByPrimaryKeySelective(Bot record);

    int updateByPrimaryKey(Bot record);
}