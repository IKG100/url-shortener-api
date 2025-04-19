package com.goit.urlshortener.url;

import com.goit.urlshortener.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url_code", length = 50)
    private String shortUrlCode;

    /**
     * The original long URL that needs to be shortened.
     */
    @Column(name = "long_url", length = 2000, nullable = false)
    private String longUrl;


    /**
     * The number of visits this short URL has received.
     */
    @Column
    private long visits;

    /**
     * The timestamp when the URL was created.
     * Automatically generated and set by the database upon insertion.
     * This field is immutable after creation.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating when the URL will expire.
     * This field is optional and can be null if no expiration date is specified.
     */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    /**
     * The user associated with this URL.
     * Represents a Many-to-One relationship linking each URL to a User entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
