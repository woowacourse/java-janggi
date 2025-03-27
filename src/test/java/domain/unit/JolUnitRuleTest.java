package domain.unit;

import domain.position.Position;
import domain.position.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolUnitRuleTest {
    @Test
    @DisplayName("졸은 수직/수평 한칸만 움직일 수 있다")
    void test1() {
        // given
        JolUnitRule jolUnitRule = new JolUnitRule();

        // when
        List<Route> routes = jolUnitRule.calculateAllRoute(new Position(0, 0));

        // then
        Assertions.assertThat(routes).isNotEmpty();
        Assertions.assertThat(routes).containsOnly(
                Route.of(List.of(new Position(1, 0))),
                Route.of(List.of(new Position(0, 1)))
        );
    }
}
