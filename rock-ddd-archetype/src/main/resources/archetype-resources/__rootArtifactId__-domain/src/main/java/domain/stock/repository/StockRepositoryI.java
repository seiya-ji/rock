#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.repository;

import ${package}.domain.stock.model.GoodsStock;
/**
 * 
 * 仓库存储接口定义
 * @author jihb
 * @version : StockRepositoryI.java, v0.1 2021/3/26 cg Exp ${symbol_dollar}${symbol_dollar}
 */
public interface StockRepositoryI  {
	
   public GoodsStock  get(Long id);
   
   public void save(GoodsStock stock);
}
