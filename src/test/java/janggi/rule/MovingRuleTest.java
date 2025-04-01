package janggi.rule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.moveStrategy.rule.MoveVector;
import janggi.moveStrategy.rule.MovingRule;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovingRuleTest {

    @DisplayName("moveUnit의 합을 반환한다.")
    @Test
    void testSumUnit() {
        // given
        final MovingRule movingRule = new MovingRule(List.of(new MoveVector(1, 0), new MoveVector(-1, 0)));
        // when
        final MoveVector actual = movingRule.sumUnit();
        // then
        assertThat(actual).isEqualTo(new MoveVector(0, 0));
    }

    @DisplayName("대각선인지 확인한다.")
    @Test
    void testIsDiagonal() {
        // given
        final MovingRule movingRule = new MovingRule(List.of(new MoveVector(1, -1)));
        // when
        // then
        assertThat(movingRule.isDiagonal()).isTrue();
    }
}
