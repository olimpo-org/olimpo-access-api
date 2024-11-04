package com.example.olimpoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class OlimpoapiApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(OlimpoapiApplication.class, args);
    }
    @Beanpublic WebMvcConfigurer corsConfigurer() {    
        return new WebMvcConfigurer() {        
            @Override        
            public void addCorsMappings(CorsRegistry registry) {            
                registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5500");        
            }    
        };
    }
}
