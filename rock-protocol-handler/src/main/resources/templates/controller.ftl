package ${projectPath}.${module}.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onezhier.rock.client.resp.SingleResponse;
import com.onezhier.rock.framework.controller.BaseController;
import ${projectPath}.${module}.convertor.${modelNameUpper}Convertor;
import ${projectPath}.${module}.service.${modelNameUpper}Service;
import ${projectPath}.${module}.dto.req.${modelNameUpper}ReqDTO;
import ${projectPath}.${module}.dto.req.query.${modelNameUpper}Query;
import ${projectPath}.${module}.dto.resp.${modelNameUpper}RespDTO;
import ${projectPath}.${module}.dto.resp.${modelNameUpper}RespListDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@RestController
@RequestMapping("${modelNameLower}")
@Api(tags = "中台应用角色接口", consumes = "application/json")
public class ${modelNameUpper}Controller extends BaseController<${modelNameUpper}RespListDTO, ${modelNameUpper}RespDTO,${modelNameUpper}ReqDTO, ${modelNameUpper}ReqDTO, ${modelNameUpper}Query>{

	@Autowired
    public ${modelNameUpper}Controller(${modelNameUpper}Service baseService,
			 ${modelNameUpper}Convertor convertor) {
		super(baseService, convertor);
	}



    
    
}
