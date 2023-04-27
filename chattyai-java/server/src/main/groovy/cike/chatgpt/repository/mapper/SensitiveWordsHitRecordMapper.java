package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.SensitiveWordsHitRecord;
import cike.chatgpt.repository.entity.SensitiveWordsHitRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SensitiveWordsHitRecordMapper {
    long countByExample(SensitiveWordsHitRecordExample example);

    int deleteByExample(SensitiveWordsHitRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SensitiveWordsHitRecord record);

    int insertSelective(SensitiveWordsHitRecord record);

    List<SensitiveWordsHitRecord> selectByExample(SensitiveWordsHitRecordExample example);

    SensitiveWordsHitRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SensitiveWordsHitRecord record, @Param("example") SensitiveWordsHitRecordExample example);

    int updateByExample(@Param("record") SensitiveWordsHitRecord record, @Param("example") SensitiveWordsHitRecordExample example);

    int updateByPrimaryKeySelective(SensitiveWordsHitRecord record);

    int updateByPrimaryKey(SensitiveWordsHitRecord record);
}