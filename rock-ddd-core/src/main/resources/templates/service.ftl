package ${projectPath}.app.${aggregateRoot}.service;

import ${projectPath}.client.${aggregateRoot}.dto.req.${modelNameUpper}AddCmd;
import ${projectPath}.client.${aggregateRoot}.dto.req.${modelNameUpper}ModifyCmd;
import ${projectPath}.client.${aggregateRoot}.dto.req.${modelNameUpper}QueryDTO;
import ${projectPath}.client.${aggregateRoot}.dto.resp.${modelNameUpper}ListResponseDTO;
import ${projectPath}.client.${aggregateRoot}.dto.resp.${modelNameUpper}SingleResponseDTO;
import com.fzd.rock.application.BaseApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@RestController
@RequestMapping("/${modelNameLower}")
public class ${modelNameUpper}ServiceImpl extends BaseApplicationService<${modelNameUpper}ListResponseDTO, ${modelNameUpper}SingleResponseDTO, ${modelNameUpper}AddCmd, ${modelNameUpper}ModifyCmd, ${modelNameUpper}QueryDTO> {
}
