package domain;

import java.time.Clock;
import java.time.Instant;

public record GameDeadline(Instant instant) {

    public static GameDeadline of(Instant instant) {
        return new GameDeadline(instant);
    }

    public boolean isExpired(Clock clock) {
        return !clock.instant().isBefore(instant);
    }
}

