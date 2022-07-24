#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.app.stock.executor;

import org.springframework.stereotype.Component;
import com.onezhier.rock.framework.application.executor.CommonExecutorI;
import com.onezhier.rock.client.resp.SingleResponse;
import ${package}.domain.stock.factory.StockFactory;
import ${package}.domain.stock.model.GoodsStock;
import ${package}.client.stock.dto.GoodsStockIncreaseCmd;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jihb
 * @version : LocationUnFreezeCmdExe.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Component
public class GoodsStockIncreaseCmdExe implements CommonExecutorI<GoodsStockIncreaseCmd,SingleResponse> {
	
	@Autowired
	public StockFactory stockFactory;
	
	 /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public SingleResponse execute(GoodsStockIncreaseCmd cmd) {
    	GoodsStock stock = stockFactory.reBuild(cmd.getId());
    	stock.increase(cmd.getAccountId(),cmd.getGoodsId(),cmd.getAmount(),cmd.getBatchNo());
    	return SingleResponse.buildSuccess();
    }
    
}
