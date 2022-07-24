package ${projectPath}.${module}.dto.resp;

import java.util.List;

import com.onezhier.rock.client.Response;

import lombok.Data;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@Data
public class ${modelNameUpper}RespListDTO extends Response {

	<#list attributes as attribute>
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
}
