package ${projectPath}.infra.${aggregateRoot}.dal.database.dataobj;

import com.fzd.rock.dal.db.DO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@Data
@Entity
@Table(name = "${modelNameLower}")
public class ${modelNameUpper}Entity extends DO {
<#list attributes as attribute>
    @Column(name = "${attribute.name}")
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
</#list>
}