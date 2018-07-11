package com.labym.flood.config;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to JHipster.
 *
 * <p> Properties are configured in the application.yml file. </p>
 * <p> This class also load properties in the Spring Environment from the git.properties and META-INF/build-info.properties
 * files if they are found in the classpath.</p>
 */
@ConfigurationProperties(prefix = "flood", ignoreUnknownFields = false)
@PropertySources({
        @PropertySource(value = "classpath:git.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:META-INF/build-info.properties", ignoreResourceNotFound = true)
})

@Data
public class FloodProperties {

    private final Async async = new Async();

    private final Http http = new Http();

    private final Cache cache = new Cache();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    private final Swagger swagger = new Swagger();

    private final Metrics metrics = new Metrics();

    private final Logging logging = new Logging();

    private final CorsConfiguration cors = new CorsConfiguration();

    private final Social social = new Social();

    private final Gateway gateway = new Gateway();

    private final Registry registry = new Registry();

    @Data
    public static class Async {

        private int corePoolSize = FloodDefaults.Async.corePoolSize;

        private int maxPoolSize = FloodDefaults.Async.maxPoolSize;

        private int queueCapacity = FloodDefaults.Async.queueCapacity;

    }
    @Data
    public static class Http {

        public enum Version {V_1_1, V_2_0}

        private final Cache cache = new Cache();

        /**
         * Https has to be active with cipher suite define also
         */
        private boolean useUndertowUserCipherSuitesOrder = FloodDefaults.Http.useUndertowUserCipherSuitesOrder;

        /**
         * HTTP version, must be "V_1_1" (for HTTP/1.1) or V_2_0 (for (HTTP/2)
         */
        public Version version = FloodDefaults.Http.version;

        @Data
        public static class Cache {

            private int timeToLiveInDays = FloodDefaults.Http.Cache.timeToLiveInDays;

        }

    }

    public static class Cache {

        private final Hazelcast hazelcast = new Hazelcast();

        private final Ehcache ehcache = new Ehcache();

        private final Infinispan infinispan = new Infinispan();

        private final Memcached memcached = new Memcached();


        @Data
        public static class Hazelcast {

            private int timeToLiveSeconds = FloodDefaults.Cache.Hazelcast.timeToLiveSeconds;

            private int backupCount = FloodDefaults.Cache.Hazelcast.backupCount;

            private final ManagementCenter managementCenter = new ManagementCenter();

            @Data
            public static class ManagementCenter {

                private boolean enabled = FloodDefaults.Cache.Hazelcast.ManagementCenter.enabled;

                private int updateInterval = FloodDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;

                private String url =  FloodDefaults.Cache.Hazelcast.ManagementCenter.url;

            }


        }
        @Data
        public static class Ehcache {

            private int timeToLiveSeconds = FloodDefaults.Cache.Ehcache.timeToLiveSeconds;

            private long maxEntries = FloodDefaults.Cache.Ehcache.maxEntries;

        }
        @Data
        public static class Infinispan {

            private String configFile = FloodDefaults.Cache.Infinispan.configFile;

            private boolean statsEnabled = FloodDefaults.Cache.Infinispan.statsEnabled;

            private final Local local = new Local();

            private final Distributed distributed = new Distributed();

            private final Replicated replicated = new Replicated();


            @Data
            public static class Local {

                private long timeToLiveSeconds = FloodDefaults.Cache.Infinispan.Local.timeToLiveSeconds;

                private long maxEntries = FloodDefaults.Cache.Infinispan.Local.maxEntries;



            }
            @Data
            public static class Distributed {

                private long timeToLiveSeconds = FloodDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;

                private long maxEntries = FloodDefaults.Cache.Infinispan.Distributed.maxEntries;

                private int instanceCount = FloodDefaults.Cache.Infinispan.Distributed.instanceCount;

            }
            @Data
            public static class Replicated {

                private long timeToLiveSeconds = FloodDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;

                private long maxEntries = FloodDefaults.Cache.Infinispan.Replicated.maxEntries;

            }
        }
        @Data
        public static class Memcached {

            private boolean enabled = FloodDefaults.Cache.Memcached.enabled;

            /**
             * Comma or whitespace separated list of servers' addresses.
             */
            private String servers = FloodDefaults.Cache.Memcached.servers;

            private int expiration = FloodDefaults.Cache.Memcached.expiration;

            private boolean useBinaryProtocol = FloodDefaults.Cache.Memcached.useBinaryProtocol;
        }
    }
    @Data
    public static class Mail {

        private boolean enabled = FloodDefaults.Mail.enabled;

        private String from = FloodDefaults.Mail.from;

        private String baseUrl = FloodDefaults.Mail.baseUrl;


    }
    @Data
    public static class Security {

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        private final Authentication authentication = new Authentication();

        private final RememberMe rememberMe = new RememberMe();


        @Data
        public static class ClientAuthorization {

            private String accessTokenUri = FloodDefaults.Security.ClientAuthorization.accessTokenUri;

            private String tokenServiceId = FloodDefaults.Security.ClientAuthorization.tokenServiceId;

            private String clientId = FloodDefaults.Security.ClientAuthorization.clientId;

            private String clientSecret = FloodDefaults.Security.ClientAuthorization.clientSecret;


        }
        @Data
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }
            @Data
            public static class Jwt {

                private String secret = FloodDefaults.Security.Authentication.Jwt.secret;

                private long tokenValidityInSeconds = FloodDefaults.Security.Authentication.Jwt
                        .tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe = FloodDefaults.Security.Authentication.Jwt
                        .tokenValidityInSecondsForRememberMe;

            }
        }
        @Data
        public static class RememberMe {

            @NotNull
            private String key = FloodDefaults.Security.RememberMe.key;

        }
    }
    @Data
    public static class Swagger {

        private String title = FloodDefaults.Swagger.title;

        private String description = FloodDefaults.Swagger.description;

        private String version = FloodDefaults.Swagger.version;

        private String termsOfServiceUrl = FloodDefaults.Swagger.termsOfServiceUrl;

        private String contactName = FloodDefaults.Swagger.contactName;

        private String contactUrl = FloodDefaults.Swagger.contactUrl;

        private String contactEmail = FloodDefaults.Swagger.contactEmail;

        private String license = FloodDefaults.Swagger.license;

        private String licenseUrl = FloodDefaults.Swagger.licenseUrl;

        private String defaultIncludePattern = FloodDefaults.Swagger.defaultIncludePattern;

        private String host = FloodDefaults.Swagger.host;

        private String[] protocols = FloodDefaults.Swagger.protocols;

        private boolean useDefaultResponseMessages = FloodDefaults.Swagger.useDefaultResponseMessages;


    }
    @Data
    public static class Metrics {

        private final Jmx jmx = new Jmx();

        private final Logs logs = new Logs();


        @Data
        public static class Jmx {

            private boolean enabled = FloodDefaults.Metrics.Jmx.enabled;

        }
        @Data
        public static class Logs {

            private boolean enabled = FloodDefaults.Metrics.Logs.enabled;

            private long reportFrequency = FloodDefaults.Metrics.Logs.reportFrequency;

        }
    }
    @Data
    public static class Logging {

        private final Logstash logstash = new Logstash();

        @Data
        public static class Logstash {

            private boolean enabled = FloodDefaults.Logging.Logstash.enabled;

            private String host = FloodDefaults.Logging.Logstash.host;

            private int port = FloodDefaults.Logging.Logstash.port;

            private int queueSize = FloodDefaults.Logging.Logstash.queueSize;


        }
    }
    @Data
    public static class Social {

        private String redirectAfterSignIn = FloodDefaults.Social.redirectAfterSignIn;

    }
    @Data
    public static class Gateway {

        private final RateLimiting rateLimiting = new RateLimiting();



        private Map<String, List<String>> authorizedMicroservicesEndpoints = FloodDefaults.Gateway
                .authorizedMicroservicesEndpoints;

        @Data
        public static class RateLimiting {

            private boolean enabled = FloodDefaults.Gateway.RateLimiting.enabled;

            private long limit = FloodDefaults.Gateway.RateLimiting.limit;

            private int durationInSeconds = FloodDefaults.Gateway.RateLimiting.durationInSeconds;


        }
    }
    @Data
    public static class Registry {

        private String password = FloodDefaults.Registry.password;

    }
}