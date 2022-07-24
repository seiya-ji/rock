#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.client.stock.dto;


import com.onezhier.rock.client.req.Command;
import lombok.Data;
import java.lang.*;

/**
 * 库位冻结请求
 *
 * @author 太乙
 * @version : LocationFreezeCmd.java, v0.1 2021/3/24 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Data
public class GoodsStockDecreaseCmd extends Command{
    
	private Long id;
	
	private Integer amount;
	
    private Long goodsId;

    private Long batchId;
}
