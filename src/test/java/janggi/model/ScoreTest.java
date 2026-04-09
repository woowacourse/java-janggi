package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("다른 Score가 들어오면 합산을 계산해 반환한다.")
    @Test
    void add() {
        //given
        Score score = new Score(10);
        Score otherScore = new Score(15);
        int expected = 25;

        //when
        Score actual = score.add(otherScore);

        //then
        assertThat(actual.value()).isEqualTo(expected);
    }

    @DisplayName("zero()는 0점을 반환한다.")
    @Test
    void zero() {
        assertThat(Score.zero()).isEqualTo(new Score(0));
    }
}
