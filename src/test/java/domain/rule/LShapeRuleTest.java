package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class LShapeRuleTest {

    private final MoveRule rule = new LShapeRule();

    @Test
    void 정상_범위_입력() {
        assertThat(rule.canMove(new Position(5, 5), new Position(6, 7))).isTrue();
    }

    @Test
    void 정상_범위가_아니면_거짓() {
        assertThat(rule.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 행_방향_경로를_계산한다() {
        Position source = new Position(3, 3);
        Position mid = new Position(4, 3);
        Position destination = new Position(5, 4);

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(List.of(mid));
    }

    @Test
    void 열_방향_경로를_계산한다() {
        Position source = new Position(3, 3);
        Position mid = new Position(3, 4);
        Position destination = new Position(4, 5);

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(List.of(mid));
    }
}
