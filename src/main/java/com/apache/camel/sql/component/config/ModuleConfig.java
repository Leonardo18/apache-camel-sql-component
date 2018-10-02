package com.apache.camel.sql.component.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
