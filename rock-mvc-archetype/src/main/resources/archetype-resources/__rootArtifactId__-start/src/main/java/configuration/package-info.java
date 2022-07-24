#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 数据库配置在Nacos中必须以下格式进行配置
 *
 * ${symbol_pound}一主一从
 * spring.shardingsphere.datasource.names=master,slave
 * spring.shardingsphere.datasource.master.type=com.zaxxer.hikari.HikariDataSource
 * spring.shardingsphere.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.shardingsphere.datasource.master.jdbc-url=jdbc:mysql://rm-bp1b4auo9p1rrq69ueo.mysql.rds.aliyuncs.com:3306/tcwms?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull
 * spring.shardingsphere.datasource.master.username=mysqltest_ddl_user
 * spring.shardingsphere.datasource.master.password=DDL4FuW(Nke9DeiXUNYDgQWM
 *
 * spring.shardingsphere.datasource.slave.type=com.zaxxer.hikari.HikariDataSource
 * spring.shardingsphere.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver
 * spring.shardingsphere.datasource.slave.jdbc-url=jdbc:mysql://rm-bp1b4auo9p1rrq69ueo.mysql.rds.aliyuncs.com:3306/tcwms?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull
 * spring.shardingsphere.datasource.slave.username=mysqltest_ddl_user
 * spring.shardingsphere.datasource.slave.password=DDL4FuW(Nke9DeiXUNYDgQWM
 *
 * ${symbol_pound} 读写分离配置
 * spring.shardingsphere.masterslave.load-balance-algorithm-type=round_robin
 * spring.shardingsphere.masterslave.name=ms
 * spring.shardingsphere.masterslave.master-data-source-name=master
 * spring.shardingsphere.masterslave.slave-data-source-names=slave
 *
 * spring.shardingsphere.props.sql.show=true
 *
 *
 * @author 太乙
 * @version : package-info.java, v0.1 2021/3/26 cg Exp ${symbol_dollar}${symbol_dollar}
 */

package ${package}.configuration;
