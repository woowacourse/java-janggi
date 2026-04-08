package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrthogonalOneStepRuleTest {

    private final MoveRule rule = new OrthogonalOneStepRule();

    @Test
    void 상하좌우_한칸_이동이_가능하다() {
        assertThat(rule.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 대각선_이동은_불가능하다() {
        assertThat(rule.canMove(new Position(5, 5), new Position(6, 6))).isFalse();
    }

    @Test
    void 경로는_빈_리스트이다() {
        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(2), new Column(3));

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(List.of());
    }
}
