package cike.chatgpt.repository.rbac

import cike.chatgpt.config.SQLInstance
import cike.chatgpt.repository.entity.Role
import cike.chatgpt.repository.entity.UserRole
import cike.chatgpt.repository.enums.UserRoleEnum
import cike.chatgpt.repository.mapper.UserRoleMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class RABCRepository {

  @Autowired
  private UserRoleMapper userRoleMapper


  static Set<String> getUserPermission(String userId) {
    def sql = """
            select p.name name from permission p
            join role_permission rp on rp.permission_id = p.id and rp.is_grant=1
            join user_role ur on ur.role_id = rp.role_id
            where ur.user_id= ?
            """
    Set<String> result = []
    SQLInstance.sql.rows(sql, userId).each { row ->
      result.add(row.name as String)
    }
    return result
  }

  static Role findRoleByName(String name) {
    def row = SQLInstance.sql.firstRow("select id, name from role where name = ?", name)
    if (row) {
      return new Role(id: row.id as int, name: row.name)
    }
    return null
  }

  static UserRole findUserRoleByUid(String uid) {
    def row = SQLInstance.sql.firstRow("select id, user_id, role_id from user_role where user_id = ?", uid)
    if (row) {
      return new UserRole(id: row.id as int, userId: row.user_id, roleId: row.role_id as int)
    }
    return null
  }

  void setUserRole(String uid, UserRoleEnum userRoleEnum) {
    def currentUserRole = findUserRoleByUid(uid)
    Integer roleId = null
    if (userRoleEnum == UserRoleEnum.USER) {
      roleId = findRoleByName("user").id
    } else if (userRoleEnum == UserRoleEnum.ADMIN) {
      roleId = findRoleByName("admin").id
    }

    if (!currentUserRole) {
      currentUserRole = new UserRole(userId: uid, roleId: roleId)
      userRoleMapper.insertSelective(currentUserRole)
    } else if (currentUserRole.roleId != roleId) {
      currentUserRole.setRoleId(roleId)
      userRoleMapper.updateByPrimaryKeySelective(currentUserRole)
    }
  }
}
