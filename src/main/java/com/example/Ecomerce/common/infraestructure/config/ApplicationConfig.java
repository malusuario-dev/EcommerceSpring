package com.example.Ecomerce.common.infraestructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
//@EnableScheduling

@EnableAsync
@Configuration
@EnableCaching
public class ApplicationConfig {
}
