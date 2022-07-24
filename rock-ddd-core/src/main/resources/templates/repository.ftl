package ${projectPath}.infra.${aggregateRoot}.dal.database.dao;

import ${projectPath}.infra.${aggregateRoot}.dal.database.dataobj.${modelNameUpper}Entity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* @Author ${author}
* @Date ${dateTime}
*/
public interface ${modelNameUpper}Dao extends JpaRepository<${modelNameUpper}Entity,Long> {
}
