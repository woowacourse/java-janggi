package domain.piece.move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Path;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedMoveRuleTest {

    private FixedMoveRule fixedMoveRule;

    @BeforeEach
    void beforeEach() {
        List<Path> paths = List.of(
                new Path(List.of(Direction.UP, Direction.UP, Direction.RIGHT, Direction.RIGHT)),
                new Path(List.of(Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN)),
                new Path(List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN)),
                new Path(List.of(Direction.RIGHT, Direction.LEFT, Direction.UP, Direction.UP)),
                new Path(List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.UP))
        );
        fixedMoveRule = new FixedMoveRule(paths);
    }


    @Test
    @DisplayName("이동할 수 있는 경로의 중간지점들을 반환한다")
    void getIntermediatePathTest() {
        // given
        Position from = Position.of(3, 3);
        Position to = Position.of(1, 1);

        // when
        List<Position> intermediatePath = fixedMoveRule.getIntermediatePath(from, to);

        // then
        assertThat(intermediatePath).containsExactly(Position.of(2, 3), Position.of(2, 2), Position.of(2, 1));
    }

    @Test
    @DisplayName("이동할 수 있는 경로가 없으면 예외가 발생한다")
    void getIntermediatePathException(){
        // given
        Position from = Position.of(3, 3);
        Position to = Position.of(4, 0);

        // when && then
        assertThatThrownBy(() -> fixedMoveRule.getIntermediatePath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

}
