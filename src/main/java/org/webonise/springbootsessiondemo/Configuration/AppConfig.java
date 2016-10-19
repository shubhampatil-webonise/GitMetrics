package org.webonise.springbootsessiondemo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    @Bean
    public Jedis getJedisConnection() {
        return new Jedis("localhost");
    }
}
