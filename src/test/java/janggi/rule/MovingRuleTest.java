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
        final MovingRule movingRule = new MovingRule(List.of(new Vector(1, 0), new Vector(-1, 0)));
        // when
        final Vector actual = movingRule.sumAllVectors();
        // then
        assertThat(actual).isEqualTo(new Vector(0, 0));
    }
}
