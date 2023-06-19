package com.fredsonchaves.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves.infraestructure.configuration.json.Json;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    public ObjectMapper mapper() {
        return Json.getMapper();
    }
}
