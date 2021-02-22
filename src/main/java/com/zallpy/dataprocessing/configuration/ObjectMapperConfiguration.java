package com.zallpy.dataprocessing.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
        .setDateFormat(new StdDateFormat())
        .registerModule(new JavaTimeModule());
    }
}
