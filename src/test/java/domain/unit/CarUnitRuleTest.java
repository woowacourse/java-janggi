package domain.unit;

import domain.Route;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarUnitRuleTest {
    @Test
    @DisplayName("차가 갈 수 있는 엔드포인트를 모두 반환한다")
    void test1() {
        // given
        Point p = new Point(0, 0);
        CarUnitRule rule = new CarUnitRule();

        // when
        List<Point> endPoints = rule.calculateEndPoints(p);

        // then
        Assertions.assertThat(endPoints)
                .hasSize(17);
    }

    @Test
    @DisplayName("시작점과 끝점의 경로를 반환한다")
    void test2() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(0, 5);
        CarUnitRule rule = new CarUnitRule();

        // when
        Route path = rule.calculateRoute(start, end);

        // then
        Assertions.assertThat(path.getPoints()).hasSize(5);
    }
}
