package events.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;



@ComponentScan(
        basePackages = "events",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EventsJpaTestConfiguration.class)}
        )
@EnableTestAutoConfiguration
@Configuration
public class EventsBootTestConfiguration {
}
