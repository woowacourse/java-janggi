package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.List;
import org.junit.jupiter.api.Test;

class ForwardAndSideRuleTest {

    @Test
    void 한_진영에서_왼쪽_이동() {
        MoveRule rule = new ForwardAndSideRule(-1);
        assertThat(rule.canMove(new Position(5, 5), new Position(5, 4))).isTrue();
    }

    @Test
    void 한_진영에서_오른쪽_이동() {
        MoveRule rule = new ForwardAndSideRule(-1);
        assertThat(rule.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 한_진영에서_전진_이동() {
        MoveRule rule = new ForwardAndSideRule(-1);
        assertThat(rule.canMove(new Position(5, 5), new Position(4, 5))).isTrue();
    }

    @Test
    void 한_진영에서_후진_불가() {
        MoveRule rule = new ForwardAndSideRule(-1);
        assertThat(rule.canMove(new Position(5, 5), new Position(6, 5))).isFalse();
    }

    @Test
    void 한_진영에서_범위_밖은_거짓() {
        MoveRule rule = new ForwardAndSideRule(-1);
        assertThat(rule.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 초_진영에서_왼쪽_이동() {
        MoveRule rule = new ForwardAndSideRule(1);
        assertThat(rule.canMove(new Position(5, 5), new Position(5, 4))).isTrue();
    }

    @Test
    void 초_진영에서_오른쪽_이동() {
        MoveRule rule = new ForwardAndSideRule(1);
        assertThat(rule.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 초_진영에서_전진_이동() {
        MoveRule rule = new ForwardAndSideRule(1);
        assertThat(rule.canMove(new Position(5, 5), new Position(6, 5))).isTrue();
    }

    @Test
    void 초_진영에서_후진_불가() {
        MoveRule rule = new ForwardAndSideRule(1);
        assertThat(rule.canMove(new Position(5, 5), new Position(4, 5))).isFalse();
    }

    @Test
    void 초_진영에서_범위_밖은_거짓() {
        MoveRule rule = new ForwardAndSideRule(1);
        assertThat(rule.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 경로는_빈_리스트이다() {
        MoveRule rule = new ForwardAndSideRule(-1);
        Position source = new Position(new Row(5), new Column(5));
        Position destination = new Position(new Row(4), new Column(5));

        assertThat(rule.calculateRoute(source, destination)).isEqualTo(List.of());
    }
}
