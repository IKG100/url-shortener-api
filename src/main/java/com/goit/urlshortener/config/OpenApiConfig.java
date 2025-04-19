package com.goit.urlshortener.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * Configuration class for setting up OpenAPI documentation using Springdoc OpenAPI and Swagger.
 * Defines security scheme, error response examples, and provides custom OpenAPI customization.
 */
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class OpenApiConfig {

    /**
     * Template for error responses used in various HTTP error examples.
     */
    private static final LinkedHashMap<String, Object> ERROR_RESPONSE_TEMPLATE = new LinkedHashMap<String, Object>() {{
        put("status", 0);
        put("error", "");
        put("path", "/api/v1/**");
        put("message", "");
    }};

    /**
     * Example for a 400 Bad Request error response.
     */
    private static final Example BAD_REQUEST_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 400);
                put("error", "Bad Request");
                put("message", "Bad request appropriate message");
            }});

    /**
     * Example for a 401 Unauthorized error response.
     */
    private static final Example UNAUTHORIZED_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 401);
                put("error", "Unauthorized");
                put("message", "Unauthorized appropriate message");
            }});

    /**
     * Example for a 403 Forbidden error response.
     */
    private static final Example FORBIDDEN_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 403);
                put("error", "Forbidden");
                put("message", "Forbidden appropriate message");
            }});

    /**
     * Example for a 404 Not Found error response.
     */
    private static final Example NOT_FOUND_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 404);
                put("error", "Not found");
                put("message", "Resource not found appropriate message");
            }});

    /**
     * Example for a 409 Conflict error response.
     */
    private static final Example CONFLICT_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 409);
                put("error", "Conflict");
                put("message", "Conflict appropriate message");
            }});

    /**
     * Example for a 500 Internal Server Error response.
     */
    private static final Example INTERNAL_SERVER_ERROR_EXAMPLE = new Example()
            .value(new LinkedHashMap<>(ERROR_RESPONSE_TEMPLATE) {{
                put("status", 500);
                put("error", "Internal Server Error");
                put("message", "Internal server error appropriate message");
            }});

    /**
     * Bean that customizes OpenAPI documentation with general API info (title, version).
     *
     * @return the customized OpenAPI configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API documentation. URL SHORTENER Rest API.")
                        .version("1.0"));
    }

    /**
     * Customizes OpenAPI by adding predefined error response examples to the OpenAPI components.
     *
     * @return the OpenApiCustomizer to apply examples.
     */
    @Bean
    public OpenApiCustomizer customizerOpenApi() {
        return openApi -> openApi.getComponents()
                .addExamples("BadRequestExample", BAD_REQUEST_EXAMPLE)
                .addExamples("UnauthorizedExample", UNAUTHORIZED_EXAMPLE)
                .addExamples("ForbiddenExample", FORBIDDEN_EXAMPLE)
                .addExamples("NotFoundExample", NOT_FOUND_EXAMPLE)
                .addExamples("ConflictExample", CONFLICT_EXAMPLE)
                .addExamples("InternalServerErrorExample", INTERNAL_SERVER_ERROR_EXAMPLE);
    }
}