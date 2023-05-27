package cike.chatgpt.controller

import cike.chatgpt.interceptor.Permission
import cike.chatgpt.interceptor.RequiredLogin
import cike.chatgpt.interceptor.SessionHolder
import cike.chatgpt.service.MemberService
import cike.chatgpt.service.MemberWalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequiredLogin(permission = Permission.MEMBER)
@RestController
@RequestMapping("member")
class MemberController {

    @Autowired
    private SessionHolder sessionHolder

    @Autowired
    private MemberWalletService memberWalletService

    @Autowired
    private MemberService memberService

    @GetMapping("info")
    CommonResponse<MemberInfo> memberInfo() {
        new CommonResponse<MemberInfo>(status: CommonResponse.Success,
                data: memberService.getMemberInfo(sessionHolder.getCurrentUserUID()))
    }

    @PostMapping("info")
    CommonResponse<String> modifyInfo(@RequestBody MemberInfo memberInfo) {
        memberService.modifyAvatarAndNickname(sessionHolder.getCurrentUserUID(), memberInfo.avatar, memberInfo.nickname)
    }

    @GetMapping("wallet")
    CommonResponse<WalletInfo> tokenWallet() {
        memberWalletService.getTokenWallet(sessionHolder.getCurrentUserUID())
    }

    @GetMapping("wallet/record")
    CommonResponse<PageList<WalletInfoRecord>> walletRecord(int currentPage, int pageSize) {
        new CommonResponse<PageList<WalletInfoRecord>>(status: CommonResponse.Success,
                data: memberWalletService.getTokenWalletRecord(sessionHolder.getCurrentUserUID(), currentPage, pageSize))
    }

    @GetMapping("invitationRecord")
    CommonResponse<PageList<InvitationMemberInfo>> invitationRecord(int currentPage, int pageSize) {
        new CommonResponse<PageList<InvitationMemberInfo>>(status: CommonResponse.Success,
                data: memberService.getInvitationMemberInfo(sessionHolder.getCurrentUserUID(), currentPage, pageSize))
    }

    @PostMapping("modifyPwd")
    CommonResponse<String> modifyPwd(@RequestBody ModifyPasswordParam param) {
        try {
            memberService.modifyPwd(sessionHolder.getCurrentUserUID(), param.oldPassword, param.newPassword)
            new CommonResponse<String>(status: CommonResponse.Success, message: "修改成功")
        } catch (Exception e) {
            new CommonResponse<String>(status: CommonResponse.Fail, message: e.message)
        }
    }
}

class InvitationMemberInfo {
    String nickname
    String username
    Date invitationDate
}

class MemberInfo {
    String nickname
    String username
    String avatar
    String invitationCode
}

class ModifyPasswordParam {
    String newPassword
    String oldPassword
}

class WalletInfo {
    int totalValue
    int availableValue
}

class WalletInfoRecord {
    long sid
    int token
    Date createTime
    String remark
}