package com.goit.urlshortener.url;

import com.goit.urlshortener.error.exception.ResourceNotFoundException;
import com.goit.urlshortener.url.controller.StatsController;
import com.goit.urlshortener.url.dto.statistics.StatsListUrlResponse;
import com.goit.urlshortener.url.dto.statistics.StatsVisitsUrlResponse;
import com.goit.urlshortener.url.service.StatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;


    @Test
    void allUrls_shouldReturnOk_withSuccessRequest() {
        StatsListUrlResponse response = new StatsListUrlResponse();
        when(statsService.getAllUrlsByUser()).thenReturn(response);
        ResponseEntity<StatsListUrlResponse> result = statsController.allUrls();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(statsService).getAllUrlsByUser();
    }

    @Test
    void activeUrls_shouldReturnOkAndResponseBody_withSuccessRequest() {
        StatsListUrlResponse response = new StatsListUrlResponse();
        when(statsService.getActiveUrlsByUser()).thenReturn(response);
        ResponseEntity<StatsListUrlResponse> result = statsController.activeUrls();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(statsService).getActiveUrlsByUser();
    }

    @Test
    void visitsByShortUrl_shouldReturnOkAndResponseBody_withSuccessRequest() {
        String shortUrlCode = "abc123";
        StatsVisitsUrlResponse response = new StatsVisitsUrlResponse();
        when(statsService.getVisitsByShortUrl(shortUrlCode)).thenReturn(response);
        ResponseEntity<StatsVisitsUrlResponse> result = statsController.visitsByShortUrl(shortUrlCode);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(statsService).getVisitsByShortUrl(shortUrlCode);
    }

    @Test
    void visitsByShortUrl_shouldThrowException_whenNotFound() {
        String shortUrlCode = "nonexistent";
        when(statsService.getVisitsByShortUrl(shortUrlCode))
                .thenThrow(new ResourceNotFoundException("URL not found"));
        assertThrows(ResourceNotFoundException.class, () -> statsController.visitsByShortUrl(shortUrlCode));
        verify(statsService).getVisitsByShortUrl(shortUrlCode);
    }
}
