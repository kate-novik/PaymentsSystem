package by.it.novik.config;

import by.it.novik.services.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created by Kate Novik.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("by.it.novik.services")
@Import({HibernateConfig.class})
public class ServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
