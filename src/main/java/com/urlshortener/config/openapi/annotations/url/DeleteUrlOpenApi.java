package com.urlshortener.config.openapi.annotations.url;

import com.urlshortener.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Delete a shortened URL", description = "Removes a URL mapping from the system")
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Url has been deleted successfully",
                content = @Content()),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "ForbiddenExample",
                                ref = "#/components/examples/ForbiddenExample"))),
        @ApiResponse(
                responseCode = "404",
                description = "URL not found",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "NotFoundExample",
                                ref = "#/components/examples/NotFoundExample")))
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DeleteUrlOpenApi {
}
