#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.infra.stock.dal.database.dataobj;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author jihb
 * @version : AdminUser.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Data
@Entity
@Table(name="sm_admin")
public class AdminUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String adName;
    private String adRealName;
}
