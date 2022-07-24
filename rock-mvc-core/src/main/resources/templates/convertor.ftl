package ${projectPath}.${module}.convertor;

import org.mapstruct.Mapper;

import com.onezhier.rock.framework.convertor.ApplicationConvertorI;
import ${projectPath}.${module}.dao.entity.${modelNameUpper};
import ${projectPath}.${module}.dto.req.${modelNameUpper}ReqDTO;
import ${projectPath}.${module}.dto.req.query.${modelNameUpper}Query;
import ${projectPath}.${module}.dto.resp.${modelNameUpper}RespDTO;
import ${projectPath}.${module}.dto.resp.${modelNameUpper}RespListDTO;


@Mapper(componentModel = "spring")
public interface ${modelNameUpper}Convertor extends ApplicationConvertorI<${modelNameUpper}ReqDTO, ${modelNameUpper}, ${modelNameUpper}Query, ${modelNameUpper}RespListDTO, ${modelNameUpper}RespDTO>{

}
