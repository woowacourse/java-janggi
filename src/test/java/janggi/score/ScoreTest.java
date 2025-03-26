package janggi.score;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("두 score을 더한다.")
    @Test
    void testAddScore() {
        // given
        final Score score1 = new Score(5);
        final Score score2 = new Score(10);
        // when
        // then
        assertThat(score1.add(score2)).isEqualTo(new Score(15));
    }

    @DisplayName("두 score을 비교한다.")
    @Test
    void testIsBiggerThan() {
        // given
        final Score score1 = new Score(5);
        final Score score2 = new Score(10);
        // when
        // then
        assertThat(score2.isBiggerThan(score1)).isTrue();
    }
}
