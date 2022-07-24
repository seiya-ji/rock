#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */
package ${package}.configuration;

import org.springframework.context.annotation.Configuration;
import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */

@Configuration
public class DefaultConfiguration {

	
//	@LoadBalanced
//	@Bean
//	@SentinelRestTemplate(urlCleanerClass = UrlCleaner.class, urlCleaner = "clean")
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}

	@LoadBalanced
	@Bean
	@SentinelRestTemplate
	public RestTemplate restTemplate1() {
		return new RestTemplate();
	}
   

}
