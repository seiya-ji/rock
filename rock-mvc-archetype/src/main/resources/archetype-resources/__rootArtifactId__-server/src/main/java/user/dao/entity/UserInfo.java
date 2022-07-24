#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.onezhier.rock.protocol.annotation.Attribute;
import com.onezhier.rock.protocol.annotation.UniqueConstraint;
import com.onezhier.rock.protocol.annotation.Model;
import com.onezhier.rock.framework.dal.db.DO;
import ${package}.user.dto.enums.UserJobStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "er_user_info")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Model(logic = true,uniqueConstraints = {@UniqueConstraint(attributes = {"tenantId","name"})})
public class UserInfo extends DO{

   
    @Column(name = "tenant_id")
    private Long tenantId;
    

    @Column(name = "name")
    private String name;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING) 
    private UserJobStatus state;

    @Column(name = "password")
    private String password;

    @Column(name = "dept_id")
    private Long deptId;

   
}
