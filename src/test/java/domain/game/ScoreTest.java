package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    void 점수에_다른_점수를_더할_수_있다() {
        // given
        Score score1 = new Score(13);
        Score score2 = new Score(13);
        // when & then
        assertThat(score1.add(score2)).isEqualTo(new Score(26));
    }
}
