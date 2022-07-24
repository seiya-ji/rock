package ${projectPath}.${module}.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.onezhier.rock.framework.annotation.UniqueKey;
import com.onezhier.rock.framework.dal.db.DO;

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
@com.onezhier.rock.framework.annotation.Entity(logic = true,uniqueKeys = {@UniqueKey(attributes = {"tenantId","name"})})
public class ${modelNameUpper} extends DO {

	<#list attributes as attribute>
    @Column(name = "${attribute.name}")
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
	
   

   
}
