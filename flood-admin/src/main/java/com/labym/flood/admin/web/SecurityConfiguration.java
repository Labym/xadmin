package com.labym.flood.admin.web;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;

@SecurityDefinition(
        apiKeyAuthDefinitions={
                @ApiKeyAuthDefinition(
                        in=ApiKeyAuthDefinition.ApiKeyLocation.HEADER,
                        key = "Bear",
                        name = "Authentication"
                )
        }
)
public @interface SecurityConfiguration {
}
