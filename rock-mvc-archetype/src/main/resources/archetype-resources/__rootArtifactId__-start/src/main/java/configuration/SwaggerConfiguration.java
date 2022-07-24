#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (c) 2018-2021 All Rights Reserved.
 */

package ${package}.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jihb
 * @date 2022年06月01日 11:33
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    private static final String splitor = ";";

    @Bean
    public Docket createRestApi() {
        String basePackages = "${package}";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage(basePackages))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 声明基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * 校验基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * 检验基础包实例
     *
     * @param requestHandler 请求处理类
     * @return
     */
    @SuppressWarnings("deprecation")
    private static Optional<? extends Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.fromNullable(requestHandler.declaringClass());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("${parentArtifactId}接口文档")
                .description("")
                .contact(new Contact("stephen.ji", "https://www.baidu.com/", "172220322@qq.com"))
                .version("1.0")
                .build();
    }
}
