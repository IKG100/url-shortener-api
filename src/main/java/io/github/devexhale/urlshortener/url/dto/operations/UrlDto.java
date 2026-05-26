package io.github.devexhale.urlshortener.url.dto.operations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.devexhale.urlshortener.url.dto.BaseUrlResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema
public class UrlDto extends BaseUrlResponseDto {

    @Schema(example = "user123")
    private String author;
}
