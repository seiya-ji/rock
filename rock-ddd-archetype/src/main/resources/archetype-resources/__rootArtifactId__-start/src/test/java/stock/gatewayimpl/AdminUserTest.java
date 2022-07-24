//#set( $symbol_pound = '#' )
//#set( $symbol_dollar = '$' )
//#set( $symbol_escape = '\' )
///**
// * Copyright (c) 2018-2021 All Rights Reserved.
// */
//
//package ${package}.stock.gatewayimpl;
//
//import ${package}.ScmStartApplication;
//import ${package}.stock.gatewayimpl.database.AdminUserMapper;
//import ${package}.stock.gatewayimpl.database.dataobj.AdminUser;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @author 太乙
// * @version : AdminUserTest.java, v0.1 2021/3/25 cg Exp ${symbol_dollar}${symbol_dollar}
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ScmStartApplication.class,
//        webEnvironment = SpringBootTest.WebEnvironment.NONE,
//        properties = "spring.profiles.active=dev")
//public class AdminUserTest {
//
//    @Autowired
//    private AdminUserMapper userMapper;
//
//    @Test
//    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        List<AdminUser> userList = userMapper.selectList(null);
//        Assert.assertEquals(6576, userList.size());
//        userList.forEach(System.out::println);
//    }
//}
