package events.config;

import events.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Import(EventsBootTestConfiguration.class)
@Configuration
public class EventsTestWebConfiguration implements WebMvcConfigurer {
    private AccountService accountService;

    @Bean
    FilterRegistrationBean<BasicAuthFilter> basicAuthFilterRegistrationBean() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(basicAuthFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Bean
    public BasicAuthFilter basicAuthFilter() {
        return new BasicAuthFilter(accountService);
    }
}
