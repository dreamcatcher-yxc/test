package org.yxc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @author yangxiuchu
 * @DATE 2017/12/10 14:10
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket petApi() {
        Contact contact = new Contact("杨秀初", "...", "1905719625@@qq.com");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("前台API接口")
                .description("前台API接口")
                .contact(contact)
                .version("1.0.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo);
    }
}
