package cike.chatgpt.repository

import cike.chatgpt.repository.entity.MemberWallet
import cike.chatgpt.repository.entity.MemberWalletExample
import cike.chatgpt.repository.entity.MemberWalletRecord
import cike.chatgpt.repository.entity.MemberWalletRecordExample
import cike.chatgpt.repository.enums.MemberWalletTypeEnum
import cike.chatgpt.repository.mapper.MemberWalletMapper
import cike.chatgpt.repository.mapper.MemberWalletRecordMapper
import cike.chatgpt.utils.CollectionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MemberWalletRepository {

  @Autowired
  private MemberWalletMapper mapper

  @Autowired
  private MemberWalletRecordMapper walletRecordMapper

  MemberWallet getTokenWalletByUid(String uid) {
    def example = new MemberWalletExample()
    example.createCriteria()
        .andUidEqualTo(uid)
        .andTypeEqualTo(MemberWalletTypeEnum.CHAT.code)

    def memberWallets = mapper.selectByExample(example)
    CollectionUtil.getFirstElseNull(memberWallets)
  }

  void addWallet(MemberWallet memberWallet) {
    mapper.insertSelective(memberWallet)
  }

  List<MemberWalletRecord> getWalletDetailRecordByUidOrderById(String uid) {
    def example = new MemberWalletRecordExample()
    example.createCriteria().andUidEqualTo(uid)
    example.setOrderByClause(" id desc")
    walletRecordMapper.selectByExample(example)
  }

  void addMemberWalletRecord(MemberWalletRecord memberWalletRecord) {
    walletRecordMapper.insertSelective(memberWalletRecord)
  }

  void subMemberWalletToken(String uid, int token) {
    def wallet = getTokenWalletByUid(uid)
    MemberWallet memberWallet = new MemberWallet()
    memberWallet.setId(wallet.getId())
    memberWallet.setAvailableValue(wallet.availableValue - token)
    mapper.updateByPrimaryKeySelective(memberWallet)
  }

  void addMemberWalletToken(String uid, int token) {
    def wallet = getTokenWalletByUid(uid)
    MemberWallet memberWallet = new MemberWallet()
    memberWallet.setId(wallet.getId())
    memberWallet.setAvailableValue(wallet.availableValue + token)
    memberWallet.setTotalValue(wallet.totalValue + token)
    mapper.updateByPrimaryKeySelective(memberWallet)
  }
}
