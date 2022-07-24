#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.model;

import ${package}.client.stock.domainevent.StockAmountIncreaseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import com.onezhier.rock.framework.application.ApplicationContext;
import com.onezhier.rock.framework.domain.Entity;
import com.onezhier.rock.framework.domain.AggregatesRootObject;
import lombok.Data;
import java.lang.*;
import java.util.*;
import com.onezhier.rock.exception.BizException;


/**
 * 货物库存
 * @author jihb
 * @version : GoodsStock.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Data
@Entity
public class GoodsStock extends AggregatesRootObject{
	
	private Long id;
	
	private Long accountId;
	
	private Long goodsId;
	
	private Integer amount;
	
	private List<BatchStock> batchStockList = new LinkedList<>();
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public void create(GoodsStock stock) {
		// TODO
	}
	
	public void increase(Long accountId,Long goodsId,Integer amount,String batchNo) {
		if(this.accountId.longValue()!=accountId.longValue()) {
			throw new BizException("不属于该库存账户");
		}
		if(this.goodsId.longValue()!=goodsId.longValue()) {
			throw new BizException("不属于该货品");
		}
		//找到相应的批次库存,没用新建
		BatchStock batch = null;
		
		batch.increase(goodsId,amount);
		this.amount = this.amount.intValue()+amount.intValue();
		
		this.applicationContext.getEventBus().fire(new StockAmountIncreaseEvent());
	}
	
	public void decrease(Long accountId,Long goodsId,Integer amount,String batchNo) {
		// TODO
	}
	
	
	
	
    
}
