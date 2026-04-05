package janggi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ScoreTest {

    @Test
    void 점수는_음수가_될_수_없다(){
        assertThatThrownBy(() -> new Score(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("점수는 음수가 될 수 없습니다.");
    }

    @Test
    void 점수끼리_더하면_합산된_점수를_반환한다(){
        Score score1 = new Score(1);
        Score score2 = new Score(1);

        Score result = score1.plus(score2);

        assertThat(result).isEqualTo(new Score(2));
    }

    @Test
    void 점수가_더_크면_true를_반환한다(){
        Score higher = new Score(2);
        Score lower = new Score(1);

        boolean result = higher.isGreaterThan(lower);

        assertThat(result).isTrue();
    }

    @Test
    void 점수가_더_작으면_false를_반환한다(){
        Score lower = new Score(1);
        Score higher = new Score(2);

        boolean result = lower.isGreaterThan(higher);

        assertThat(result).isFalse();
    }
}