package com.artlanguage.starter.realtimeserver;

import com.artlanguage.starter.controllers.ChatsController;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan("com.artlanguage.starter.services")
@ComponentScan("com.artlanguage.starter.repository")
@ComponentScan(basePackageClasses = ChatsController.class)
@EntityScan("com.artlanguage.starter.models")
@EnableJpaRepositories(value = "com.artlanguage.starter.repository")
@SpringBootApplication
public class RealTimeServerApplication {

    @Value("${rt-server.host}")
    private String host;

    @Value("${rt-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        return new SocketIOServer(config);
    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/getChatMessages").allowedOrigins("*");
                registry.addMapping("/chatsList").allowedOrigins("*");
                registry.addMapping("/retrieveLightCustomerProfileByDisplayName").allowedOrigins("*");


                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*").allowedHeaders("*");
                registry.addMapping("/**").allowedOrigins("*");
                registry.addMapping("/**");
            }
        };
    }



    public static void main(String[] args) {
        SpringApplication.run(RealTimeServerApplication.class, args);
    }
}
