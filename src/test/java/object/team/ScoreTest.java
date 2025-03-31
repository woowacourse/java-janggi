package object.team;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    @DisplayName("점수를 더한다.")
    void test1() {
        // given
        Score score = new Score(0);

        // when
        Score addedScore = score.add(3);

        // then
        assertThat(addedScore.getScore()).isEqualTo(3);
    }
}
