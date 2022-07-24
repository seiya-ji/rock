#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.domain.stock.extention.impl;

import com.onezhier.rock.framework.domain.extension.Extension;
import ${package}.domain.stock.extention.InventoryAllocationExtPtI;
import lombok.extern.slf4j.Slf4j;
import java.lang.*;

/**
 * @author 太乙
 * @version : FirstInFirstOutExt.java, v0.1 2021/3/26 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Slf4j
@Extension(bizId = "taskEngine", useCase = "allocateStock", scenario = "freeze")
public class FirstInFirstOutExtImpl implements InventoryAllocationExtPtI {
    @Override
    public Object allocate(String placeholder) {
        log.info("先进先出");
        return null;
    }
}
