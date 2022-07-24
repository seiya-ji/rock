#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.client.stock.api;

import ${package}.client.stock.dto.GoodsStockQuery;
import ${package}.client.stock.dto.GoodsStockIncreaseCmd;
import ${package}.client.stock.dto.GoodsStockDecreaseCmd;
import ${package}.client.stock.dto.clientobject.GoodsStock;
import com.fzd.rock.client.resp.MultiResponse;
import com.fzd.rock.client.resp.SingleResponse;

/**
 * 库存服务
 *
 * @author jihb
 * @version : StockService.java, v0.1 2021/3/23 cg Exp ${symbol_dollar}${symbol_dollar}
 */
public interface StockService {
    /**
     * 查询库位库存
     *
     * @param query
     * @return
     */
    MultiResponse<GoodsStock> queryGoodsStock(GoodsStockQuery query);

    /**
     * 增加库存
     *
     * @param command
     * @return
     */
    SingleResponse<?> increase(GoodsStockIncreaseCmd command);

    /**
     * 减少库存
     *
     * @param command
     * @return
     */
    SingleResponse<?> decrease(GoodsStockDecreaseCmd command);

   
}
