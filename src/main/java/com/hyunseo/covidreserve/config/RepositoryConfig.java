package com.hyunseo.covidreserve.config;

import com.hyunseo.covidreserve.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ihyeonseo
 */
@Configuration
public class RepositoryConfig {

    // 스프링에게 주입할 방법을 등록했음
    @Bean
    public EventRepository eventRepository() {
        return new EventRepository() {};
    }
}
