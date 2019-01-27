package events.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(
        basePackages = "events",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EventsJpaTestConfiguration.class)}
        )
@EntityScan(basePackages = "events")
@EnableJpaRepositories(basePackages = "events")
@EnableAutoConfiguration
@Configuration
public class EventsBootTestConfiguration {
}
