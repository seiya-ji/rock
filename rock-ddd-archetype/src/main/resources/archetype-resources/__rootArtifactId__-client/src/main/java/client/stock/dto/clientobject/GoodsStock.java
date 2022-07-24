#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.client.stock.dto.clientobject;


import lombok.Data;
import java.lang.*;
/**
 * @author 太乙
 * @version : GoodsStock.java, v0.1 2021/3/24 cg Exp $$
 */
@Data
public class GoodsStock {
	
    private Long id;
    private Long goodsId;
    private Integer amount;
    private Integer inTransitNum;
    private Integer freezeNum;

   
}
