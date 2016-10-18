package org.webonise.gitmetrics.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JsonParserConfig {

    @Bean("resultMap")
    @Scope("prototype")
    public Map<String, Object> resultMap() {
        return new HashMap();
    }
}
