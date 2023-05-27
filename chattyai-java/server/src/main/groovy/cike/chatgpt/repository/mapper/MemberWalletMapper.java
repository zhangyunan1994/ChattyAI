package cike.chatgpt.repository.mapper;

import cike.chatgpt.repository.entity.MemberWallet;
import cike.chatgpt.repository.entity.MemberWalletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberWalletMapper {
    long countByExample(MemberWalletExample example);

    int deleteByExample(MemberWalletExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberWallet record);

    int insertSelective(MemberWallet record);

    List<MemberWallet> selectByExample(MemberWalletExample example);

    MemberWallet selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberWallet record, @Param("example") MemberWalletExample example);

    int updateByExample(@Param("record") MemberWallet record, @Param("example") MemberWalletExample example);

    int updateByPrimaryKeySelective(MemberWallet record);

    int updateByPrimaryKey(MemberWallet record);
}