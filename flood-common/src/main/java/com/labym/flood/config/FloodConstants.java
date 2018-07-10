package com.labym.flood.config;

public interface FloodConstants {

    String PHONE_PATTERN="^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    String EMAIL_PATTERN="[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    // Spring profiles for development, test and production, see https://www.jhipster.tech/profiles/
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    String SPRING_PROFILE_TEST = "test";
    String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used when deploying to Amazon ECS
    String SPRING_PROFILE_AWS_ECS = "aws-ecs";
    // Spring profile used to disable swagger
    String SPRING_PROFILE_SWAGGER = "swagger";
    // Spring profile used to disable running liquibase
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
    // Spring profile used when deploying to Kubernetes and OpenShift
    String SPRING_PROFILE_K8S = "k8s";
}
