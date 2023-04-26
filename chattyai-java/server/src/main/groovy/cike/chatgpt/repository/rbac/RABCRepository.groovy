package cike.chatgpt.repository.rbac

import cike.chatgpt.config.SQLInstance

class RABCRepository {

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
}
