package com.labym.flood.config.swagger;

import static springfox.documentation.builders.PathSelectors.regex;

import java.nio.ByteBuffer;
import java.util.*;
import javax.servlet.Servlet;


import com.google.common.collect.Lists;
import com.labym.flood.config.FloodConstants;
import com.labym.flood.config.FloodProperties;
import com.labym.flood.config.swagger.customizer.FloodSwaggerCustomizer;
import com.labym.flood.config.swagger.customizer.SwaggerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springfox Swagger configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue.
 * In that case, you can use the "no-swagger" Spring profile, so that this bean is ignored.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({
        ApiInfo.class,
        BeanValidatorPluginsConfiguration.class,
        Servlet.class,
        DispatcherServlet.class
})
@Profile(FloodConstants.SPRING_PROFILE_SWAGGER)
@AutoConfigureAfter(FloodProperties.class)
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

    public static final String STARTING_MESSAGE = "Starting Swagger";
    public static final String STARTED_MESSAGE = "Started Swagger in {} ms";
    public static final String MANAGEMENT_TITLE_SUFFIX = "Management API";
    public static final String MANAGEMENT_GROUP_NAME = "management";
    public static final String MANAGEMENT_DESCRIPTION = "Management endpoints documentation";

    private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    private final FloodProperties.Swagger properties;

    public SwaggerAutoConfiguration(FloodProperties floodProperties) {
        this.properties = floodProperties.getSwagger();
    }

    /**
     * Springfox configuration for the API Swagger docs.
     *
     * @param swaggerCustomizers Swagger customizers
     * @param alternateTypeRules alternate type rules
     * @return the Swagger Springfox configuration
     */
    @Bean
    @ConditionalOnMissingBean(name = "swaggerSpringfoxApiDocket")
    public Docket swaggerSpringfoxApiDocket(List<SwaggerCustomizer> swaggerCustomizers,
                                            ObjectProvider<AlternateTypeRule[]> alternateTypeRules) {
        log.debug(STARTING_MESSAGE);
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = createDocket();

        // Apply all SwaggerCustomizers orderly.
        swaggerCustomizers.forEach(customizer -> customizer.customize(docket));

        // Add all AlternateTypeRules if available in spring bean factory.
        // Also you can add your rules in a customizer bean above.
        Optional.ofNullable(alternateTypeRules.getIfAvailable()).ifPresent(docket::alternateTypeRules);

        watch.stop();
        log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
        return docket;
    }

    /**
     * Flood Swagger Customizer
     *
     * @return the Swagger Customizer of JHipster
     */
    @Bean
    public FloodSwaggerCustomizer floodSwaggerCustomizer() {
        return new FloodSwaggerCustomizer(properties);
    }

    /**
     * Springfox configuration for the management endpoints (actuator) Swagger docs.
     *
     * @param appName               the application name
     * @param managementContextPath the path to access management endpoints
     * @return the Swagger Springfox configuration
     */
    @Bean
    @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.ManagementServerProperties")
    @ConditionalOnProperty("management.endpoints.web.base-path")
    @ConditionalOnExpression("'${management.endpoints.web.base-path}'.length() > 0")
    @ConditionalOnMissingBean(name = "swaggerSpringfoxManagementDocket")
    public Docket swaggerSpringfoxManagementDocket(@Value("${spring.application.name:application}") String appName,
                                                   @Value("${management.endpoints.web.base-path}") String managementContextPath) {

        ApiInfo apiInfo = new ApiInfo(
                StringUtils.capitalize(appName) + " " + MANAGEMENT_TITLE_SUFFIX,
                MANAGEMENT_DESCRIPTION,
                properties.getVersion(),
                "",
                ApiInfo.DEFAULT_CONTACT,
                "",
                "",
                new ArrayList<>()
        );

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .hidden(false)
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());

        return createDocket()
                .apiInfo(apiInfo)
                .groupName(MANAGEMENT_GROUP_NAME)
                .host(properties.getHost())
                .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
                .forCodeGeneration(true)
                .globalOperationParameters(pars)
                .directModelSubstitute(ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .paths(regex(managementContextPath + ".*"))
                .build()
                .securitySchemes(Collections.singletonList(new ApiKey("Authorization", "Authorization", "header")))
                //.securityContexts(securityContexts())
                ;
    }

    protected Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }
    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("/api/.*"))
                        .build()
        );
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}