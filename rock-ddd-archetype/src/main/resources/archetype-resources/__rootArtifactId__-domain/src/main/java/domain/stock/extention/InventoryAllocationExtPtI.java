#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.extention;

import com.onezhier.rock.framework.domain.extension.ExtensionPointI;
import java.lang.*;

/**
 * 扩展点
 * 涉及三部分：1.业务(bizId);2.用例(useCase);3.场景(scenario)
 * @author 太乙
 * @version : InventoryAllocationExtPt.java, v0.1 2021/3/26 cg Exp ${symbol_dollar}${symbol_dollar}
 */
public interface InventoryAllocationExtPtI extends ExtensionPointI {
    /**
     * 库位分配策略
     *
     * @param placeholder
     * @return
     */
    Object allocate(String placeholder);
}
