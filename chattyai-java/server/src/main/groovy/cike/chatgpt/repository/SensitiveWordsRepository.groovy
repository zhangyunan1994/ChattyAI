package cike.chatgpt.repository

import cike.chatgpt.repository.entity.SensitiveWords
import cike.chatgpt.repository.entity.SensitiveWordsExample
import cike.chatgpt.repository.entity.SensitiveWordsHitRecord
import cike.chatgpt.repository.entity.SensitiveWordsHitRecordExample
import cike.chatgpt.repository.mapper.SensitiveWordsHitRecordMapper
import cike.chatgpt.repository.mapper.SensitiveWordsMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.text.SimpleDateFormat

@Repository
class SensitiveWordsRepository {

  @Autowired
  private SensitiveWordsHitRecordMapper hitRecordMapper

  @Autowired
  private SensitiveWordsMapper sensitiveWordsMapper

  List<SensitiveWordsHitRecord> findHitRecord(String searchText,
                                              String startTime,
                                              String endTime) {

    SensitiveWordsHitRecordExample example = new SensitiveWordsHitRecordExample()

    def criteria = example.createCriteria()


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    if (searchText) {
      criteria.andContentLike('%' + searchText + '%')
    }

    if (startTime) {
      criteria.andCreateTimeGreaterThanOrEqualTo(format.parse(startTime))
    }

    if (endTime) {
      criteria.andCreateTimeLessThanOrEqualTo(format.parse(endTime))
    }

    hitRecordMapper.selectByExample(example)
  }

  List<SensitiveWords> query(String searchText) {

    def example = new SensitiveWordsExample()

    if (searchText) {
      example.createCriteria().andCategoryLike('%' + searchText + '%')
      example.or().andWordLike('%' + searchText + '%')
    }

    sensitiveWordsMapper.selectByExample(example)
  }

  void add(SensitiveWords sensitiveWords) {
    sensitiveWordsMapper.insertSelective(sensitiveWords)
  }

  void modify(SensitiveWords sensitiveWords) {
    sensitiveWordsMapper.updateByPrimaryKeySelective(sensitiveWords)
  }

  void deleteSensitiveWords(int id) {
    sensitiveWordsMapper.deleteByPrimaryKey(id)
  }
}
