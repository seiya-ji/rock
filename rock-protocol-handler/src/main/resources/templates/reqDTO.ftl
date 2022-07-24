package ${projectPath}.${module}.dto.req;

import java.util.List;

import com.onezhier.rock.client.Request;

import lombok.Data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.*;
import java.util.*;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@Data
@ApiModel("${model.alias}请求")
public class ${modelNameUpper}ReqDTO extends Request {

	<#list attributes as attribute>
	@ApiModelProperty(value = "${attribute.displayName}")
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
}
