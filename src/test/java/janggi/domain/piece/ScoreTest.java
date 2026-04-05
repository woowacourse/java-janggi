package janggi.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreTest {

    @Test
    @DisplayName("두 점수를 더한 새로운 점수를 반환한다.")
    void testAddScore() {
        // given
        Score score1 = new Score(13);
        Score score2 = new Score(7);

        // when
        Score result = score1.add(score2);

        // then
        assertThat(result).isEqualTo(new Score(20));
    }

    @Test
    @DisplayName("같은 값의 점수는 동등하다.")
    void testScoreEquality() {
        // given
        Score score1 = new Score(13);
        Score score2 = new Score(13);

        // when & then
        assertThat(score1).isEqualTo(score2);
    }
}
