#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.factory;

import ${package}.domain.stock.model.GoodsStock;
import ${package}.domain.stock.repository.StockRepositoryI;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.onezhier.rock.framework.domain.DomainFactory;



/**
 * 聚合根工厂类
 * @author jihb
 * @version : StockFactory.java, v0.1 2021/3/26 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Component
public class StockFactory extends DomainFactory {
	
	@Autowired
	private StockRepositoryI stockRepositoryI;
	
   public GoodsStock reBuild(Long id) {
	   return null;
   }
   
}
