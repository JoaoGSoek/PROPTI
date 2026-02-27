package com.projetoJG.desafioP.D.config; // Ajuste para seu pacote real

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Essa anotação é OBRIGATÓRIA para o Spring achar essa classe
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas as rotas da API
                .allowedOrigins("http://localhost:5173", "http://localhost:3000") // Permite o Vue (geralmente porta 5173)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}