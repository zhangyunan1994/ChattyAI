package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.MemberWalletRecord;
import cike.chatgpt.repository.entity.MemberWalletRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberWalletRecordMapper {

  long countByExample(MemberWalletRecordExample example);

  int deleteByExample(MemberWalletRecordExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(MemberWalletRecord record);

  int insertSelective(MemberWalletRecord record);

  List<MemberWalletRecord> selectByExample(MemberWalletRecordExample example);

  MemberWalletRecord selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") MemberWalletRecord record,
      @Param("example") MemberWalletRecordExample example);

  int updateByExample(@Param("record") MemberWalletRecord record, @Param("example") MemberWalletRecordExample example);

  int updateByPrimaryKeySelective(MemberWalletRecord record);

  int updateByPrimaryKey(MemberWalletRecord record);
}