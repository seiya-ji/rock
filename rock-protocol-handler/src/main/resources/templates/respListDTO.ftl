package ${projectPath}.${module}.dto.resp;

import java.util.List;

import com.onezhier.rock.client.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import java.lang.*;
import java.util.*;

/**
* @Author ${author}
* @Date ${dateTime}
*/
@Data
@ApiModel("${model.alias}列表响应")
public class ${modelNameUpper}RespListDTO extends Response {

	<#list attributes as attribute>
	@ApiModelProperty(value = "${attribute.displayName}")
    ${attribute.accessControl} ${attribute.type} ${attribute.name};
    </#list>
}
