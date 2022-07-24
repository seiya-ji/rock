#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.app.stock.service;

import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.client.resp.SingleResponse;
import ${package}.client.stock.api.StockService;
import ${package}.client.stock.dto.GoodsStockQuery;
import ${package}.client.stock.dto.GoodsStockIncreaseCmd;
import ${package}.client.stock.dto.GoodsStockDecreaseCmd;
import ${package}.client.stock.dto.clientobject.GoodsStock;
import ${package}.app.stock.executor.query.GoodsStockQueryExe;
//import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * 实物库存服务
 *
 * @author jihb
 * @version : InventoryServiceImpl.java, v0.1 2021/3/24 cg Exp ${symbol_dollar}${symbol_dollar}
 */
//@DubboService(version = "1.0.0", protocol = "dubbo", timeout = 1500)
public class StockServiceImpl implements StockService {

    @Autowired
    private GoodsStockQueryExe goodsStockQueryExe;
    
    
    
    /**
     * 查询库位库存
     *
     * @param query
     * @return
     */
    @Override
    public MultiResponse<GoodsStock> queryGoodsStock(GoodsStockQuery query){
    	//TODO
        try {
        	goodsStockQueryExe.execute(query);
        } catch (Exception e) {
            return MultiResponse.buildFailure("300", e.getMessage());
        }
        return MultiResponse.of(new ArrayList<>());
    }

    /**
     * 增加库存
     *
     * @param command
     * @return
     */
    @Override
    public SingleResponse<?> increase(GoodsStockIncreaseCmd command){
    	 //TODO
        return SingleResponse.buildSuccess();
    }

    /**
     * 减少库存
     *
     * @param command
     * @return
     */
    @Override
    public SingleResponse<?> decrease(GoodsStockDecreaseCmd command){
    	 //TODO
        return SingleResponse.buildSuccess();
    }
    
    
    
}
