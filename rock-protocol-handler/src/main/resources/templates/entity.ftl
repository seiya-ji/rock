package ${projectPath}.${module}.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.onezhier.rock.protocol.annotation.UniqueConstraint;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.protocol.annotation.Model;
import com.onezhier.rock.protocol.annotation.Attribute;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* @Author ${author}
* @Date ${dateTime}
*/

@Entity
@Table(name = "${modelNameLower}")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@DynamicUpdate


@Model(group="${model.groupName}", description="${model.description}", alias="${model.alias}", logic =<#if model.logic>true<#else>false</#if> , autoAudit=<#if model.autoAudit>true<#else>false</#if>
 
<#if model.uniqueConstraints?? > 
<#if (model.uniqueConstraints?size>0) >
,
uniqueConstraints = {
<#list model.uniqueConstraints as uniqueConstraint>
@UniqueConstraint(attributes = {
<#list uniqueConstraint.attributes as attribute>
"${attribute}",
</#list>
}, prompt="${uniqueConstraint.prompt}")
</#list>
}
</#if>
</#if>
)

public class ${modelNameUpper} extends DO {

	<#list attributes as attribute>
	@Attribute(alias = "${attribute.name}",comment = "${attribute.description}",unique=<#if attribute.unique>true<#else>false</#if> ,notNull=<#if attribute.notNull>true<#else>false</#if> )
    @Column(name = "${attribute.name}")
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
	
   

   
}
