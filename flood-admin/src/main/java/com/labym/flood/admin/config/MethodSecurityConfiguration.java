package com.labym.flood.admin.config;

import com.labym.flood.security.FloodMethodSecurityExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private final FloodMethodSecurityExpressionHandler methodSecurityExpressionHandler = new FloodMethodSecurityExpressionHandler();

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return this.methodSecurityExpressionHandler;
    }
}
