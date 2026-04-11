package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardState;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GeneralMovement 클래스 테스트")
class GeneralMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(path -> path.positions().getLast().equals(target));
    }

    private BoardState emptyBoard() {
        return StubBoardState.empty();
    }

    @Test
    @DisplayName("궁은 상하좌우 한 칸으로 이동할 수 있다")
    void generalMovesOneStepOrthogonally() {
        GeneralMovement movement = new GeneralMovement();
        Position center = pos(Column.E, Row.FOUR);

        List<Position> destinations = movement.findReachablePositions(center, emptyBoard());

        assertThat(destinations).containsExactlyInAnyOrder(
                pos(Column.E, Row.THREE),
                pos(Column.E, Row.FIVE),
                pos(Column.D, Row.FOUR),
                pos(Column.F, Row.FOUR)
        );
    }

    @Test
    @DisplayName("궁은 보드 경계를 넘어서는 이동 후보를 만들지 않는다")
    void generalExcludesOutOfBoardMoves() {
        GeneralMovement movement = new GeneralMovement();
        Position topLeft = pos(Column.A, Row.ZERO);

        Paths paths = movement.findPotentialPaths(topLeft);

        assertThat(paths.asList()).hasSize(2);
        assertThat(canReach(paths, pos(Column.A, Row.ONE))).isTrue();  // down
        assertThat(canReach(paths, pos(Column.B, Row.ZERO))).isTrue(); // right
    }
}
