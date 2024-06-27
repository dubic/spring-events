package com.dubic.springevents;

import com.dubic.springevents.relational.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Order(1)
@EnableAsync
@SpringBootApplication
public class SpringEventsApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcTemplate jdbcTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public SpringEventsApplication(JdbcTemplate jdbcTemplate, ApplicationEventPublisher eventPublisher) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventPublisher = eventPublisher;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringEventsApplication.class, args);
    }

    @Bean
    ApplicationListener<RepositoriesPopulatedEvent> applicationListener() {
        return event -> log.info("Has a person repo....{}", event.getRepositories().hasRepositoryFor(Person.class));
    }

    @Bean
    ApplicationListener<AuthorizationDeniedEvent> authorizationDeniedEvent() {
        return event -> {
            event.getAuthentication().get().getAuthorities().forEach(a -> log.info(a.toString()));
            log.info("Authorization Denied event :: {}", event.getAuthentication().get().getPrincipal());
        };
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> authenticationFailureBadCredentialsEvent() {
        return event -> {
            log.info("Authetication failed  :: {} :: {}", event.getAuthentication().getPrincipal(), event.getException().getMessage());
        };
    }

    @EventListener
    public void beforeSave(BeforeSaveEvent event) {
        log.info("Saving a {}....{}", event.getEntity().getClass().getSimpleName(), event.getEntity().toString());
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/person/secure").hasRole("ME")
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("dubic")
                .password("password")
                .roles("ME")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("initializing table");
//        this.jdbcTemplate.execute("""
//                CREATE TABLE PERSON (
//                  id INT AUTO_INCREMENT PRIMARY KEY,  -- Use INT for H2 auto-increment
//                  name VARCHAR(255) NOT NULL,
//                  age INT NOT NULL
//                );
//                """);
//        this.jdbcTemplate.execute("""
//                CREATE TABLE EMP (
//                  PERSON_ID INT PRIMARY KEY,  -- Use INT for H2 auto-increment
//                  company VARCHAR(255) NOT NULL,
//                  VERSION INT NOT NULL
//                );
//                """);
    }
}
