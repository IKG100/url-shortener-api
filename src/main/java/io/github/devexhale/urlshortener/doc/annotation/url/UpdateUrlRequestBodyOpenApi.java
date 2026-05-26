package io.github.devexhale.urlshortener.doc.annotation.url;

import io.github.devexhale.urlshortener.url.dto.operations.UpdateUrlRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(schema = @Schema(implementation = UpdateUrlRequest.class)))
public @interface UpdateUrlRequestBodyOpenApi {
}
