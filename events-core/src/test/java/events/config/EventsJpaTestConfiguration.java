package events.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ConditionalOnMissingBean(value = EventsBootTestConfiguration.class)
@EntityScan(basePackages = "events")
@EnableJpaRepositories(basePackages = "events")
@EnableAutoConfiguration
@Configuration
public class EventsJpaTestConfiguration {
}
