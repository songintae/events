package events.config;


import events.common.AuditorHolder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.AuditorAware;


@ComponentScan(
        basePackages = "events",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EventsJpaTestConfiguration.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuditorHolder.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuditorAware.class)}
        )
@EnableTestAutoConfiguration
@Configuration
public class EventsBootTestConfiguration {
}
