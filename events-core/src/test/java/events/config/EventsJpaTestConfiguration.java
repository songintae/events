package events.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;


@ConditionalOnMissingBean(value = EventsBootTestConfiguration.class)
@EnableTestAutoConfiguration
@Configuration
public class EventsJpaTestConfiguration {
}
