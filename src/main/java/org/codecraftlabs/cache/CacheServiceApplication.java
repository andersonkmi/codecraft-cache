package org.codecraftlabs.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;

@SpringBootApplication
public class CacheServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/v1")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("Content-Type",
                                "X-Amz-Date",
                                "Authorization",
                                "authorization",
                                "X-Api-Key",
                                "x-access-key",
                                "x-secret-key",
                                "x-session-key");
            }
        };
    }
}
