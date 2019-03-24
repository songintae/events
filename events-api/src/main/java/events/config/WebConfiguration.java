package events.config;

import events.common.AuditorHolder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private AuditorHolder auditorHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditorInterceptor());
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/v1/api/events/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver loginArgumentResolver() {
        return new LoginArgumentResolver();
    }

    @Bean
    public AuditorInterceptor auditorInterceptor() {
        return new AuditorInterceptor(auditorHolder);
    }

    @Bean
    AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
