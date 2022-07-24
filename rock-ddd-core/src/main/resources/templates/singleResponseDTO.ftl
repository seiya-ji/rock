package ${projectPath}.client.${aggregateRoot}.dto.resp;

import com.fzd.rock.client.Response;

/**
* @Author ${author}
* @Date ${dateTime}
*/
public class ${modelNameUpper}SingleResponseDTO extends Response {
<#list attributes as attribute>
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
</#list>
}