package ${projectPath}.${module}.dto.req;

import java.util.List;

import com.onezhier.rock.client.Request;

import lombok.Data;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@Data
public class ${modelNameUpper}ReqDTO extends Request {

	<#list attributes as attribute>
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
}
