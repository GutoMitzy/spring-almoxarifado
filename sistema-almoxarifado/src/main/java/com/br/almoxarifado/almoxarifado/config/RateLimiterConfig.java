package com.br.almoxarifado.almoxarifado.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RateLimiterConfig {
    @Value("${rate-limit.capacity}")
    private long tokensCapacity;
    @Value("${rate-limit.refill-tokens}")
    private long tokensRefillQuantity;
    @Value("${rate-limit.refill-duration}")
    private long tokensRefillTime;

    public final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String key) {
        Bucket bucket = buckets.computeIfAbsent(key, this::createBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createBucket(String key) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(tokensCapacity)
                .refillGreedy(tokensRefillQuantity, Duration.ofMinutes(tokensRefillTime))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public long getAvailableTokens(String client) {
        return buckets.get(client).getAvailableTokens();
    }

}
