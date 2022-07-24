package ${projectPath}.client.${aggregateRoot}.dto.req;

import com.fzd.rock.client.req.PageQuery;

/**
* @Author ${author}
* @Date ${dateTime}
*/
public class ${modelNameUpper}QueryDTO extends PageQuery {
<#list attributes as attribute>
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
</#list>
}