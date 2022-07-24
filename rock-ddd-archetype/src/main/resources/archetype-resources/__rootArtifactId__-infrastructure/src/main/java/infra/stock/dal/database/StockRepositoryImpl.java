#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.infra.stock.dal.database;

import org.springframework.stereotype.Component;

import ${package}.domain.stock.repository.StockRepositoryI;
import ${package}.domain.stock.model.GoodsStock;

/**
 * @author jihb
 * @version : StockRepositoryImpl.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Component
public class StockRepositoryImpl implements StockRepositoryI {
	
	public GoodsStock  get(Long id) {
		return null;
	}
	   
	 public void save(GoodsStock stock) {
		 
	 }
}
