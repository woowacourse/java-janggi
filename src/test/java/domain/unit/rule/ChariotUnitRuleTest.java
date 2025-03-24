package domain.unit.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.position.Route;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotUnitRuleTest {
    @Test
    @DisplayName("차가 갈 수 있는 엔드포인트를 모두 반환한다")
    void test1() {
        // given
        Position p = Position.of(0, 0);
        ChariotUnitRule rule = new ChariotUnitRule();

        // when
        List<Position> endPositions = rule.calculateEndPoints(p);

        // then
        assertThat(endPositions)
                .hasSize(17);
    }

    @Test
    @DisplayName("시작점과 끝점의 경로를 반환한다")
    void test2() {
        // given
        Position start = Position.of(0, 0);
        Position end = Position.of(0, 5);
        ChariotUnitRule rule = new ChariotUnitRule();

        // when
        Route path = rule.calculateRoute(start, end);

        // then
        assertThat(path.getPositions()).hasSize(5);
    }
}
