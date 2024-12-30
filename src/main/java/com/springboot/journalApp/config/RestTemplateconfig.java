package com.springboot.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateconfig
{
    @Bean
    public RestTemplate instance() {
        return new RestTemplate();
    }
}
