#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.model;

import com.onezhier.rock.framework.domain.Entity;
import lombok.Data;
import java.lang.*;
import com.onezhier.rock.exception.BizException;

@Data
@Entity
public class BatchStock {
	
	private String batchNo;
	
	private Long goodsId;
	
	private Integer amount;
	
	protected void increase(Long goodsId,Integer amount) {
		if(this.goodsId.longValue()!=goodsId.longValue()) {
			throw new BizException("不属于该货品");
		}
		this.amount = amount.intValue()+this.amount.intValue();
	}

    
}
