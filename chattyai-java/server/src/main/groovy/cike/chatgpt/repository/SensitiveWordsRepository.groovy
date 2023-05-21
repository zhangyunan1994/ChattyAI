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
      criteria.andUserMessageLike('%' + searchText + '%')
    }

    if (startTime) {
      criteria.andCreateTimeGreaterThanOrEqualTo(format.parse(startTime))
    }

    if (endTime) {
      criteria.andCreateTimeLessThanOrEqualTo(format.parse(endTime))
    }

    hitRecordMapper.selectByExample(example)
  }

  List<SensitiveWords> query(String searchText, String startTime, String endTime) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    def example = new SensitiveWordsExample()

    if (searchText) {
      def criteria = example.createCriteria()
      criteria.andCategoryLike('%' + searchText + '%')

      def or = example.or()
      or.andWordLike('%' + searchText + '%')

      if (startTime) {
        def startDate = format.parse(startTime)
        criteria.andCreatedAtGreaterThanOrEqualTo(startDate)
        or.andCreatedAtGreaterThanOrEqualTo(startDate)
      }

      if (endTime) {
        def endDate = format.parse(endTime)
        criteria.andCreatedAtLessThanOrEqualTo(endDate)
        or.andCreatedAtLessThanOrEqualTo(endDate)
      }
    }
    else if (endTime || startTime) {
      def criteria = example.createCriteria()

      if (startTime) {
        criteria.andCreatedAtGreaterThanOrEqualTo(format.parse(startTime))
      }

      if (endTime) {
        criteria.andCreatedAtLessThanOrEqualTo(format.parse(endTime))
      }
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
