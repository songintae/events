package events.config;

import events.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Import(EventsTestConfiguration.class)
@Configuration
public class EventsTestWebConfiguration implements WebMvcConfigurer {
    private AccountService accountService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor());
    }

    @Bean
    public HandlerInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor(accountService);
    }
}
