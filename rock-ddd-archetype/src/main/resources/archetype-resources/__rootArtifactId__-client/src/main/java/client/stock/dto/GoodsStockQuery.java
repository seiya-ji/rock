#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.client.stock.dto;

import java.util.List;
import com.fzd.rock.client.req.Query;
import java.lang.*;

import lombok.Data;

/**
 * 库区库位查询
 * @author 太乙
 * @version : GoodsStockQuery.java, v0.1 2021/3/24 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Data
public class GoodsStockQuery extends Query{
    /**
     * 仓库账号ID
     */
    private Long accountId;
    /**
     * 商品IDs
     */
    private List<Long> goodsId;

    
}
