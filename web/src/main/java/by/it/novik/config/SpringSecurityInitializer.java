package by.it.novik.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by Kate Novik.
 */
@Order(1)
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
