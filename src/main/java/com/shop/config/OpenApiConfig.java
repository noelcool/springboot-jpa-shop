package com.shop.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;


@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${shop.version}") String appVersion,
                           @Value("${shop.url}") String url, @Value("${spring.profiles.active}") String active) {
        Info info = new Info().title("shop - " + active).version(appVersion)
                .description("springboot jpa shop입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("noel").url("").email("noel@noel.com"))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        List<Server> servers = Arrays.asList(new Server().url(url).description("demo (" + active +")"));

        return new OpenAPI()
                .info(info)
                .servers(servers);
    }

}


