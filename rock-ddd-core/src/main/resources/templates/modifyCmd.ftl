package ${projectPath}.client.${aggregateRoot}.dto.req;

import com.fzd.rock.client.req.Command;

/**
* @Author ${author}
* @Date ${dateTime}
*/
public class ${modelNameUpper}ModifyCmd extends Command {
<#list attributes as attribute>
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
</#list>
}