#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.app.stock.executor.query;

import com.onezhier.rock.client.resp.MultiResponse;
import com.onezhier.rock.framework.application.executor.CommonExecutorI;
import ${package}.client.stock.dto.GoodsStockQuery;
import ${package}.client.stock.dto.clientobject.GoodsStock;
import ${package}.infra.stock.dal.database.dao.AdminUserDao;
import ${package}.infra.stock.dal.database.dataobj.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jihb
 * @version : AreaInventoryQueryExe.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
 */
@Component
public class GoodsStockQueryExe implements CommonExecutorI<GoodsStockQuery,MultiResponse>{

    @Autowired
    private AdminUserDao adminUserDao;
    
    /**
     * 执行
     *
     * @param cmd 指令
     * @return 执行结果
     */
    public MultiResponse<GoodsStock> execute(GoodsStockQuery cmd) {

        List<AdminUser> userList = adminUserDao.findAll();
        userList.forEach(System.out::println);

        return MultiResponse.of(new ArrayList<>());
    }
}
