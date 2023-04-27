package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.ChatgptMessageRecord;
import cike.chatgpt.repository.entity.ChatgptMessageRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatgptMessageRecordMapper {
    long countByExample(ChatgptMessageRecordExample example);

    int deleteByExample(ChatgptMessageRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ChatgptMessageRecord record);

    int insertSelective(ChatgptMessageRecord record);

    List<ChatgptMessageRecord> selectByExample(ChatgptMessageRecordExample example);

    ChatgptMessageRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ChatgptMessageRecord record, @Param("example") ChatgptMessageRecordExample example);

    int updateByExample(@Param("record") ChatgptMessageRecord record, @Param("example") ChatgptMessageRecordExample example);

    int updateByPrimaryKeySelective(ChatgptMessageRecord record);

    int updateByPrimaryKey(ChatgptMessageRecord record);
}