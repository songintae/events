package events.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

//    @Bean
//    public FilterRegistrationBean<AuditorFilter> auditorFilterFilterRegistrationBean() {
//        FilterRegistrationBean<AuditorFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(auditorFilter());
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }

    @Bean
    public AuditorFilter auditorFilter() {
        return new AuditorFilter();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
