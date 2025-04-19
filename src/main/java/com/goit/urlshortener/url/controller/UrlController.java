package com.goit.urlshortener.url.controller;

import com.goit.urlshortener.error.ErrorResponse;
import com.goit.urlshortener.url.dto.operations.GetShortUrlRequest;
import com.goit.urlshortener.url.dto.operations.UpdateUrlRequest;
import com.goit.urlshortener.url.dto.operations.UrlResponse;
import com.goit.urlshortener.url.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing URL shortening and retrieval in the URL shortener application.
 * <p>
 * Provides endpoints for shortening long URLs, retrieving long URLs from short codes,
 * updating URL mappings, and deleting shortened URLs.
 * </p>
 */
@Tag(name = "2. URL shortener", description = "Endpoints for URL shortening and retrieval")
@RestController
@RequestMapping("/api/v1/url")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    /**
     * Shortens a long URL.
     * <p>
     * This endpoint accepts a long URL and returns a short version of it.
     * </p>
     *
     * @param request the {@link GetShortUrlRequest} containing the long URL and additional data
     * @return a {@link ResponseEntity} containing the short URL code and HTTP status
     */
    @Operation(
            summary = "Shorten a long URL",
            description = "Creates a short version of the provided long URL")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Short URL code returned",
                    content = @Content(schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data is not correct (e.g., incorrect long url or expires date)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    ref = "#/components/examples/BadRequestExample")
                    )),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ForbiddenExample",
                                    ref = "#/components/examples/ForbiddenExample"))),
    })
    @PostMapping
    public ResponseEntity<UrlResponse> shortFromLong(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = GetShortUrlRequest.class)))
            @RequestBody GetShortUrlRequest request) {
        UrlResponse response = urlService.getShortUrlCodeFromLongUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves the original long URL from a given short URL code.
     * <p>
     * This endpoint takes a short URL code and returns the corresponding long URL.
     * </p>
     *
     * @param shortUrlCode the code of the short URL
     * @return a {@link ResponseEntity} containing the long URL and HTTP status
     */
    @Operation(summary = "Retrieve a long URL", description = "Finds the original URL from a given short URL code")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Long URL returned",
                    content = @Content(schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Short url code is not correct.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    ref = "#/components/examples/BadRequestExample")))
    })
    @PostMapping("/{shortUrlCode}")
    public ResponseEntity<UrlResponse> longFromShort(@PathVariable String shortUrlCode) {
        UrlResponse response = urlService.getLongUrlFromShortUrl(shortUrlCode);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Updates the mapping of a shortened URL.
     * <p>
     * This endpoint allows modification of the existing URL mapping, including changes
     * to the long URL or expiration date.
     * </p>
     *
     * @param request the {@link UpdateUrlRequest} containing updated data for the short URL
     * @return a {@link ResponseEntity} containing the updated URL mapping and HTTP status
     */
    @Operation(summary = "Update a shortened URL", description = "Modifies the stored URL mapping")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Long URL returned",
                    content = @Content(schema = @Schema(implementation = UrlResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data is not correct (e.g., incorrect short URL code or expires date)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    ref = "#/components/examples/BadRequestExample"))),
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
    @PatchMapping
    public ResponseEntity<UrlResponse> updateUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdateUrlRequest.class)))
            @RequestBody UpdateUrlRequest request) {
        UrlResponse response = urlService.updateUrl(request);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Deletes a shortened URL.
     * <p>
     * This endpoint removes a URL mapping from the system based on the provided short URL code.
     * </p>
     *
     * @param shortUrlCode the code of the short URL to be deleted
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) if deletion is successful
     */
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
    @DeleteMapping("/{shortUrlCode}")
    public ResponseEntity<UrlResponse> deleteUrl(@PathVariable String shortUrlCode) {
        urlService.deleteUrl(shortUrlCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
