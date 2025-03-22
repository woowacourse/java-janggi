package janggi.rule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovingRuleTest {

    @DisplayName("moveUnit의 합을 반환한다.")
    @Test
    void testSumAllVectors() {
        // given
        final MovingRule movingRule = new MovingRule(List.of(new MoveVector(1, 0), new MoveVector(-1, 0)));
        // when
        final MoveVector actual = movingRule.sumAllVectors();
        // then
        assertThat(actual).isEqualTo(new MoveVector(0, 0));
    }
}
