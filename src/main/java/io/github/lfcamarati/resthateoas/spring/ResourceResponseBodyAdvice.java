package io.github.lfcamarati.resthateoas.spring;

import io.github.lfcamarati.resthateoas.core.Resource;
import io.github.lfcamarati.resthateoas.core.ResourceBuilder;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice
public class ResourceResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return Resource.class.isAssignableFrom(methodParameter.getMethod().getReturnType());
    }

    @Override
    public Object beforeBodyWrite(Object resource, MethodParameter methodParameter, MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {

        List<String> accept = serverHttpRequest.getHeaders().get(HttpHeaders.ACCEPT);

        if(accept == null || accept.isEmpty() || !accept.contains("application/hal+json")) {
            return resource;
        }

        return new ResourceBuilder().create(resource);
    }
}
