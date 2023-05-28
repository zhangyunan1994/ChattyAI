package cike.chatgpt.service

import cike.chatgpt.component.chat.GPTStrategy
import cike.chatgpt.controller.CommonResponse
import cike.chatgpt.controller.MemberInfo
import cike.chatgpt.controller.PageList
import cike.chatgpt.controller.WalletInfo
import cike.chatgpt.controller.WalletInfoRecord
import cike.chatgpt.repository.MemberWalletRepository
import cike.chatgpt.repository.entity.MemberWallet
import cike.chatgpt.repository.entity.MemberWalletRecord
import cike.chatgpt.repository.enums.MemberWalletTypeEnum
import cike.chatgpt.repository.enums.WalletInfoRecordTypeEnum
import cike.chatgpt.utils.CollectionUtil
import cike.chatgpt.utils.RandomUtil
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class MemberWalletService {



    @Autowired
    private MemberWalletRepository memberWalletRepository

    CommonResponse<WalletInfo> getTokenWallet(String uid) {
        MemberWallet wallet = memberWalletRepository.getTokenWalletByUid(uid)
        if (wallet == null) {
            return new CommonResponse<WalletInfo>(status: CommonResponse.Fail, message: "未找到对应信息")
        } else {
            return new CommonResponse<WalletInfo>(status: CommonResponse.Success,
                    data: new WalletInfo(totalValue: wallet.totalValue, availableValue: wallet.availableValue))
        }
    }

    PageList<WalletInfoRecord> getTokenWalletRecord(String uid, int currentPage, int pageSize) {
        Page<MemberWalletRecord> page = PageHelper.startPage(currentPage, pageSize);
        def walletRecord = memberWalletRepository.getWalletDetailRecordByUidOrderById(uid)
        if (CollectionUtil.isEmpty(walletRecord)) {
            return PageList<WalletInfoRecord>.of(currentPage, pageSize, page.getTotal(), [] as List<WalletInfoRecord>)
        } else {
            def collect = walletRecord.stream().map( it -> {
                return new WalletInfoRecord(sid: it.sid, token: it.tokenCount, createTime: it.createTime, remark: WalletInfoRecordTypeEnum.getByCode(it.type).desc)
            }).collect(Collectors.toList())
            return PageList<WalletInfoRecord>.of(currentPage, pageSize, page.getTotal(), collect)
        }
    }

    void chattyAITokenSub(String uid, int token) {
        MemberWalletRecord walletRecord = new MemberWalletRecord()
        walletRecord.setUid(uid)
        walletRecord.setSid(RandomUtil.nextLong())
        walletRecord.setType((byte)3);
        walletRecord.setTokenCount(-token);
        memberWalletRepository.addMemberWalletRecord(walletRecord)
        memberWalletRepository.subMemberWalletToken(uid, token)
    }

    void chattyAITokenAdd(String uid, int token, WalletInfoRecordTypeEnum recordType) {
        MemberWalletRecord walletRecord = new MemberWalletRecord()
        walletRecord.setUid(uid)
        walletRecord.setSid(RandomUtil.nextLong())
        walletRecord.setType(recordType.code);
        walletRecord.setTokenCount(token);
        memberWalletRepository.addMemberWalletRecord(walletRecord)
        memberWalletRepository.addMemberWalletToken(uid, token)
    }

    void chattyAIRegisterToken(String uid, int token) {
        MemberWalletRecord walletRecord = new MemberWalletRecord()
        walletRecord.setUid(uid)
        walletRecord.setSid(RandomUtil.nextLong())
        walletRecord.setType(WalletInfoRecordTypeEnum.REGISTER.code);
        walletRecord.setTokenCount(token);
        memberWalletRepository.addMemberWalletRecord(walletRecord)

        MemberWallet wallet = new MemberWallet(uid: uid,
                type: MemberWalletTypeEnum.CHAT.code,
                totalValue: token,
                availableValue: token,
        )

        memberWalletRepository.addWallet(wallet)
    }
}
