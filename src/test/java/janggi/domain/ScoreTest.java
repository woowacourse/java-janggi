package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("점수(Score) 테스트")
class ScoreTest {

    @DisplayName("한나라 덤 1.5점이 적용된 점수를 반환한다.")
    @Test
    void applyHanBonus() {
        Score score = Score.of(72.0);

        Score result = score.applyHanBonus();

        assertThat(result.getValue()).isEqualTo(73.5);
    }

    @DisplayName("점수가 더 높은 쪽이 isHigherThan에서 true를 반환한다.")
    @Test
    void isHigherThan() {
        Score higher = Score.of(73.5);
        Score lower = Score.of(72.0);

        assertAll(
                () -> assertThat(higher.isHigherThan(lower)).isTrue(),
                () -> assertThat(lower.isHigherThan(higher)).isFalse()
        );
    }

    @DisplayName("점수가 동일하면 isHigherThan이 false를 반환한다.")
    @Test
    void isHigherThan_SameScore() {
        Score score1 = Score.of(72.0);
        Score score2 = Score.of(72.0);

        assertThat(score1.isHigherThan(score2)).isFalse();
    }
}
