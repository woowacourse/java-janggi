package domain;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameDeadlineTest {

    @Test
    void 현재시각이_마감_이전이면_만료되지_않았다() {
        Clock clock = Clock.fixed(Instant.parse("2026-04-06T10:00:00Z"), ZoneId.of("UTC"));
        GameDeadline deadline = GameDeadline.of(Instant.parse("2026-04-06T10:00:01Z"));

        assertThat(deadline.isExpired(clock)).isFalse();
    }

    @Test
    void 현재시각이_마감과_같거나_이후면_만료되었다() {
        Clock clock = Clock.fixed(Instant.parse("2026-04-06T10:00:00Z"), ZoneId.of("UTC"));
        GameDeadline deadline = GameDeadline.of(Instant.parse("2026-04-06T10:00:00Z"));

        assertThat(deadline.isExpired(clock)).isTrue();
    }
}

