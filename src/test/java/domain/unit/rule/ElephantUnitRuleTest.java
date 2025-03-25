package domain.unit.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.position.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantUnitRuleTest {

    @Test
    @DisplayName("코끼리가 이동 가능한 루트를 계산한다.")
    void test2() {
        // given
        ElephantUnitRule elephantUnitRule = new ElephantUnitRule();

        // when
        List<Route> routes = elephantUnitRule.calculateAllRoute(Position.of(1, 9));

        // then
        assertThat(routes).hasSize(2);
    }
}