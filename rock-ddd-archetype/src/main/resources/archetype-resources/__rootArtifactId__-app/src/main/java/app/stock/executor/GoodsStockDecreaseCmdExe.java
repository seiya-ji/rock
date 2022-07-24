#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.app.stock.executor;

import com.onezhier.rock.framework.application.executor.CommonExecutorI;
import com.onezhier.rock.client.resp.SingleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import ${package}.client.stock.dto.GoodsStockDecreaseCmd;

/**
 * @author jihb
 * @version : FreezeInventoryCmd.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Component
@AllArgsConstructor
public class GoodsStockDecreaseCmdExe implements CommonExecutorI<GoodsStockDecreaseCmd,SingleResponse> {

    
    /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public SingleResponse execute(GoodsStockDecreaseCmd cmd) {
    	 return SingleResponse.of(null);
    }
    
   
}
