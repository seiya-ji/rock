package com.onezhier.rock.protocol.configuration;


import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

/**
 * @Author chino
 * @Date 2021/10/27 23:42
 */
@Slf4j
public class Config {

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        try {
            ClassTemplateLoader loader = new ClassTemplateLoader(ClassUtils.getDefaultClassLoader(),"/templates");
            configuration.setTemplateLoader(loader);
        } catch (Exception e) {
            log.error(e.getMessage() ,e);
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return configuration;
    }
}
