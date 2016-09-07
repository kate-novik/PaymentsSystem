package by.it.novik.config;

import by.it.novik.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Kate Novik.
 */
@Configuration
@ComponentScan("by.it.novik.services")
public class ServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }


}
