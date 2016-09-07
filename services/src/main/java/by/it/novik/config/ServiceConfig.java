package by.it.novik.config;

import by.it.novik.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Kate Novik.
 */
@Configuration
@ComponentScan("by.it.novik.services")
public class ServiceConfig {

    @Bean(name = "userDetailsService")
    public MyUserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }


}
