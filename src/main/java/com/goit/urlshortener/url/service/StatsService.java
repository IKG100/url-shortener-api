package com.goit.urlshortener.url.service;

import com.goit.urlshortener.error.exception.ResourceNotFoundException;
import com.goit.urlshortener.security.provider.SecurityContextProvider;
import com.goit.urlshortener.url.Url;
import com.goit.urlshortener.url.UrlMapper;
import com.goit.urlshortener.url.UrlRepository;
import com.goit.urlshortener.url.dto.statistics.StatsListUrlResponse;
import com.goit.urlshortener.url.dto.statistics.StatsUrlDto;
import com.goit.urlshortener.url.dto.statistics.StatsVisitsUrlResponse;
import com.goit.urlshortener.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.goit.urlshortener.util.MessageProvider.URL_NOT_FOUND_MESSAGE;

/**
 * Service for managing URL statistics in the URL shortener application.
 * <p>
 * Provides methods for retrieving statistics about URLs created by the authenticated user,
 * including all URLs, active URLs, and the number of visits for a specific short URL.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class StatsService {
    private final UrlRepository urlRepository;
    private final SecurityContextProvider contextProvider;
    private final UrlMapper urlMapper;

    /**
     * Retrieves statistics for all URLs created by the authenticated user.
     * <p>
     * This method returns a list of all URLs created by the user along with the total number of visits
     * for each URL. It is used to get an overview of the user's URLs and their visit statistics.
     * </p>
     *
     * @return a {@link StatsListUrlResponse} containing a list of all URLs and their visit statistics
     */
    @Transactional(readOnly = true)
    public StatsListUrlResponse getAllUrlsByUser() {
        User user = contextProvider.getAuthenticatedUser();
        List<Url> urls = urlRepository.findAllUrlsByUser(user.getId());
        List<StatsUrlDto> statsUrlDtos = createStatsUrlDtos(urls);
        long totalVisits = statsUrlDtos.stream()
                .map(StatsUrlDto::getVisits)
                .reduce(0L, Long::sum);
        return StatsListUrlResponse.createSuccessResponse(totalVisits, statsUrlDtos);
    }

    /**
     * Retrieves statistics for all active (non-expired) URLs created by the authenticated user.
     * <p>
     * This method returns a list of URLs that are still active and belong to the authenticated user,
     * along with their visit statistics.
     * </p>
     *
     * @return a {@link StatsListUrlResponse} containing a list of active URLs and their visit statistics
     */
    @Transactional(readOnly = true)
    public StatsListUrlResponse getActiveUrlsByUser() {
        User user = contextProvider.getAuthenticatedUser();
        List<Url> urls = urlRepository.findAllUrlsByUser(user.getId());
        List<StatsUrlDto> activeStatsUrlDtos = createActiveStatsUrlDtos(urls);
        long totalVisits = activeStatsUrlDtos.stream()
                .map(StatsUrlDto::getVisits)
                .reduce(0L, Long::sum);
        return StatsListUrlResponse.createSuccessResponse(totalVisits, activeStatsUrlDtos);
    }

    /**
     * Retrieves the visit count for a specific short URL identified by its short URL code.
     * <p>
     * This method returns the number of visits for a given short URL, provided the URL belongs
     * to the authenticated user.
     * </p>
     *
     * @param shortUrlCode the short URL code for which the visit count is to be retrieved
     * @return a {@link StatsVisitsUrlResponse} containing the visit count for the specified URL
     * @throws ResourceNotFoundException if the URL is not found or does not belong to the authenticated user
     */
    @Transactional(readOnly = true)
    public StatsVisitsUrlResponse getVisitsByShortUrl(String shortUrlCode) {
        User user = contextProvider.getAuthenticatedUser();
        Optional<Url> urlOptional = urlRepository.findUrlByShortUrlCode(shortUrlCode);

        if (urlOptional.isEmpty() || !urlOptional.get().getUser().getLogin().equals(user.getLogin())) {
            throw new ResourceNotFoundException(URL_NOT_FOUND_MESSAGE);
        }

        Url url = urlOptional.get();
        return StatsVisitsUrlResponse.createSuccessResponse(url.getVisits());
    }

    /**
     * Helper method to create a list of {@link StatsUrlDto} for all URLs, including their active status.
     * <p>
     * This method generates DTOs for all URLs, marking them as active or expired based on their expiration time.
     * </p>
     *
     * @param urls the list of URLs to process
     * @return a list of {@link StatsUrlDto} objects for each URL
     */
    private List<StatsUrlDto> createStatsUrlDtos(List<Url> urls) {
        List<StatsUrlDto> list = new ArrayList<>();

        for (Url url : urls) {
            LocalDateTime expiresAt = url.getExpiresAt();
            boolean isActive = expiresAt == null || expiresAt.isAfter(LocalDateTime.now());
            list.add(urlMapper.mapToStatsUrlDto(url, isActive));
        }

        return list;
    }

    /**
     * Helper method to create a list of {@link StatsUrlDto} for active (non-expired) URLs.
     * <p>
     * This method filters URLs to include only those that are still active and not expired.
     * </p>
     *
     * @param urls the list of URLs to process
     * @return a list of {@link StatsUrlDto} objects for active URLs
     */
    private List<StatsUrlDto> createActiveStatsUrlDtos(List<Url> urls) {
        List<StatsUrlDto> list = new ArrayList<>();

        for (Url url : urls) {
            LocalDateTime expiresAt = url.getExpiresAt();
            if (expiresAt == null || expiresAt.isAfter(LocalDateTime.now())) {
                list.add(urlMapper.mapToStatsUrlDto(url, true));
            }
        }

        return list;
    }
}
