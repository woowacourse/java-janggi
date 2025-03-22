package domain.piece.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnlimitedMoveRuleTest {

    private final UnlimitedMoveRule unlimitedMoveRule = new UnlimitedMoveRule(
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));

    @Test
    @DisplayName("이동할 수 있는 경로의 중간지점들을 반환한다")
    void getIntermediatePathTest() {
        // given
        Position from = Position.of(3, 0);
        Position to = Position.of(6, 0);

        // when
        List<Position> intermediatePath = unlimitedMoveRule.getIntermediatePath(from, to);

        // then
        assertThat(intermediatePath).containsExactly(Position.of(4, 0), Position.of(5, 0));
    }

    @Test
    @DisplayName("이동할 수 있는 경로가 없으면 예외가 발생한다")
    void getIntermediatePathException() {
        // given
        Position from = Position.of(3, 3);
        Position to = Position.of(4, 0);

        // when && then
        assertThatThrownBy(() -> unlimitedMoveRule.getIntermediatePath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }
}
