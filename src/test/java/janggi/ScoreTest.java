package janggi;

import janggi.player.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ScoreTest {

    @Test
    @DisplayName("두 점수중 더 큰 것을 확인할 수 있다")
    void canCheckGreaterScore() {
        // given
        final Score general = Score.general();
        final Score win = Score.win();

        // when
        // then
        assertAll(() -> {
            assertThat(general.isGreaterThan(win)).isTrue();
            assertThat(win.isGreaterThan(general)).isFalse();
        });
    }

    @Test
    @DisplayName("두 점수중 더 작은 것을 확인할 수 있다")
    void canCheckLessScore() {
        // given
        final Score general = Score.general();
        final Score win = Score.win();

        // when
        // then
        assertAll(() -> {
            assertThat(general.isLessThan(win)).isFalse();
            assertThat(win.isLessThan(general)).isTrue();
        });
    }
}
