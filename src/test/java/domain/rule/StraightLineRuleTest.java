package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.List;
import org.junit.jupiter.api.Test;

class StraightLineRuleTest {

    private final MoveRule rule = new StraightLineRule();

    @Test
    void 직선_이동이_가능하다() {
        assertThat(rule.canMove(new Position(1, 4), new Position(1, 8))).isTrue();
    }

    @Test
    void 직선이_아닌_방향은_불가능하다() {
        assertThat(rule.canMove(new Position(5, 5), new Position(3, 3))).isFalse();
    }

    @Test
    void 같은_열의_행_방향_경로를_계산한다() {
        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(4), new Column(3));
        List<Position> expected = List.of(
                new Position(new Row(2), new Column(3)),
                new Position(new Row(3), new Column(3)));

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(expected);
    }

    @Test
    void 같은_행의_열_방향_경로를_계산한다() {
        Position source = new Position(new Row(1), new Column(3));
        Position destination = new Position(new Row(1), new Column(7));
        List<Position> expected = List.of(
                new Position(new Row(1), new Column(4)),
                new Position(new Row(1), new Column(5)),
                new Position(new Row(1), new Column(6)));

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(expected);
    }
}
