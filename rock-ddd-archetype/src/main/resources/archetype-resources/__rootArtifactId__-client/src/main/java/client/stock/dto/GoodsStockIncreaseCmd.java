#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.client.stock.dto;

import com.onezhier.rock.client.extension.BizScenario;
import com.onezhier.rock.client.req.Command;
import java.lang.*;

import lombok.Data;



/**
 * 库位冻结请求
 *
 * @author 太乙
 * @version : LocationFreezeCmd.java, v0.1 2021/3/24 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Data
public class GoodsStockIncreaseCmd extends Command{
	
	private Long id;
	
	private Integer amount;
	
    private Long goodsId;

    private String batchNo;
    
    private Long accountId;

    private BizScenario bizScenario = BizScenario.valueOf("taskEngine", "allocateStock", "freeze");


}
