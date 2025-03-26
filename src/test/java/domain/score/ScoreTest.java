package domain.score;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("점수 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScoreTest {

    @Test
    void 스코어를_더할_수_있다() {
        //given
        Score score1 = new Score(1);
        Score score2 = new Score(2);

        // when
        Score actual = score1.plus(score2);

        // then
        assertThat(actual).isEqualTo(new Score(3));
    }

    @Test
    void 스코어를_곱할_수_있다() {
        //given
        Score score2 = new Score(2);
        Score score5 = new Score(5);

        // when
        Score actual = score2.multiply(score5);

        // then
        assertThat(actual).isEqualTo(new Score(10));
    }
}