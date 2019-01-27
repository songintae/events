package events.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver loginArgumentResolver() {
        return new LoginArgumentResolver();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
