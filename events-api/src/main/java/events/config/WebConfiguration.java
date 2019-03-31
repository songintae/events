package events.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/v1/api/events/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver loginArgumentResolver() {
        return new LoginUserArgumentResolver();
    }

    @Bean
    FilterRegistrationBean<AuditorFilter> auditorFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuditorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(auditorFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public AuditorFilter auditorFilter() {
        return new AuditorFilter();
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
