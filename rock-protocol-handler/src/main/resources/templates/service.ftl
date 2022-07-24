package ${projectPath}.${module}.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.lang.*;
import java.util.*;

import org.apache.commons.collections.collection.TransformedCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onezhier.rock.exception.BizException;
import com.onezhier.rock.framework.application.UserContextHolder;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rocke.framework.service.BaseService;
import ${projectPath}.${module}.convertor.${modelNameUpper}Convertor;
import ${projectPath}.${module}.dao.repository.${modelNameUpper}Repository;
import ${projectPath}.${module}.dao.entity.${modelNameUpper};


/**
* @Author ${author}
* @Date ${dateTime}
*/
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class ${modelNameUpper}Service extends BaseService<${modelNameUpper}>{

	@Autowired
    public ${modelNameUpper}Service(${modelNameUpper}Repository respository,
    		${modelNameUpper}Convertor convertor) {
		super(respository, convertor);

		
	}


  




}
