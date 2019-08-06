package io.github.lfcamarati.resthateoas.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Configuration
public class RestHateoasConfig {

    @Bean
    public ResponseBodyAdvice responseBodyAdvice() {
        return new ResourceResponseBodyAdvice();
    }
}
