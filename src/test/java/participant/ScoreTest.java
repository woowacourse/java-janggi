package participant;

import static org.assertj.core.api.Assertions.assertThat;

import core.Score;
import org.junit.jupiter.api.Test;

class ScoreTest {

    private static final double HANDICAP_VALUE = 1.5;

    @Test
    void 점수를_더한다() {
        // given
        Score delta = new Score(20);
        Score beforeScore = new Score(10);
        // when
        Score afterScore = beforeScore.add(delta);
        // then
        double expected = beforeScore.value() + delta.value();
        assertThat(afterScore.value()).isEqualTo(expected);
    }

    @Test
    void 핸디캡을_부여하면_1_5점이_증가한다() {
        // given
        Score beforeScore = new Score(10);
        // when
        Score afterScore = beforeScore.addHandicap();
        // then
        double expected = beforeScore.value() + HANDICAP_VALUE;
        assertThat(afterScore.value()).isEqualTo(expected);
    }
}