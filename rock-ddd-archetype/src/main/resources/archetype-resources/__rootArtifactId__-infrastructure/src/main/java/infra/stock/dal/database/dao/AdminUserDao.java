#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.infra.stock.dal.database.dao;

import ${package}.infra.stock.dal.database.dataobj.AdminUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jihb
 * @version : AdminUserMapper.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Repository
public interface AdminUserDao extends JpaRepository<AdminUser,Long> {
}
