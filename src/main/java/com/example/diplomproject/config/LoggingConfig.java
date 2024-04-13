package com.example.diplomproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LoggingConfig {

    @Bean
    @Primary
    public Logger logger() {
        return LoggerFactory.getLogger("MyLogger");
    }
}