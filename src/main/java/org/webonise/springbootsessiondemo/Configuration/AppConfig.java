package org.webonise.springbootsessiondemo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }
}
