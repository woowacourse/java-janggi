package domain.piece.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.piece.move.area.FreeMoveConstraint;
import domain.piece.move.area.PalaceOnlyConstraint;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LimitedMoveRuleTest {

    @Test
    @DisplayName("이동이 가능하면 이동 경로를 반환한다")
    void getIntermediatePathTest() {
        // given
        List<Direction> directions = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        int moveCount = 3;
        LimitedMoveRule limitedMoveRule = new LimitedMoveRule(directions, moveCount, new FreeMoveConstraint());
        Position from = Position.of(0, 0);
        Position to = Position.of(0, 3);

        // when
        List<Position> actual = limitedMoveRule.getIntermediatePath(from, to);

        // then
        assertThat(actual)
                .containsExactly(Position.of(0, 1), Position.of(0, 2));
    }

    @Test
    @DisplayName("궁성에서만 이용 가능한 경우 궁성을 벗어나면 예외가 발생한다")
    void canMoveAreaException() {
        // given
        List<Direction> directions = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        int moveCount = 3;
        LimitedMoveRule limitedMoveRule = new LimitedMoveRule(directions, moveCount, new PalaceOnlyConstraint());
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 7);

        // when & then
        assertThatThrownBy(() -> limitedMoveRule.getIntermediatePath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정된 지역으로 이동할 수 없는 기물입니다.");
    }

    @Test
    @DisplayName("목표 위치로 이동할 수 없는 경우 예외가 발생한다")
    void isReachableException() {
        // given
        List<Direction> directions = List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        int moveCount = 3;
        LimitedMoveRule limitedMoveRule = new LimitedMoveRule(directions, moveCount, new FreeMoveConstraint());
        Position from = Position.of(1, 4);
        Position to = Position.of(2, 6);

        // when & then
        Assertions.assertThatThrownBy(() -> limitedMoveRule.getIntermediatePath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }
}
