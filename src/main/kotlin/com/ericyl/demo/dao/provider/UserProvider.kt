package com.ericyl.demo.dao.provider

import org.apache.commons.lang3.StringUtils

class UserProvider {

    fun selectBySomething(map: Map<String, String>): String {
        val ZHNAME_SQL = if (StringUtils.isNotBlank(map["zhName"]))
            "AND u.zhname like '%'||#{zhName}||'%'"
        else ""

        val RID_SQL = if (StringUtils.isNotBlank(map["rid"]))
            "AND r.rid = #{rid}"
        else ""

        val USERNAME_SQL = if (StringUtils.isNotBlank(map["username"]))
            "AND u.username like '%'||#{username}||'%'"
        else ""

        return """
            SELECT u.user_id,
                   u.username,
                   u.zhname,
                   u.status,
                   d.dept_name,
                   TO_NUMBER(NVL(TRIM(REGEXP_REPLACE(LISTAGG(r.root, ', ') WITHIN GROUP ( ORDER BY r.root), '[0,]')), '0')) root,
                   LISTAGG(r.role_name, ', ') WITHIN GROUP ( ORDER BY r.rid)                     role_name
            FROM users u,
                 user_role ur,
                 role r,
                 dept d
            WHERE u.did = d.did(+)
              AND u.user_id = ur.user_id(+)
              AND ur.rid = r.rid(+)
              AND u.did in (SELECT d.did FROM dept d START WITH d.did = #{did} CONNECT BY PRIOR d.did = d.parent_id)
              $ZHNAME_SQL
              $RID_SQL
              $USERNAME_SQL
            GROUP BY u.user_id, u.username, u.zhname, u.status, d.dept_name
        """.trimIndent()
    }

}