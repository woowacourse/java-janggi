package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardState;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.movement.vo.Paths;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GuardMovement 클래스 테스트")
class GuardMovementTest {

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
    @DisplayName("사는 상하좌우 한 칸으로 이동할 수 있다")
    void guardMovesOneStepOrthogonally() {
        GuardMovement movement = new GuardMovement();
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
    @DisplayName("사는 보드 경계를 넘어서는 이동 후보를 만들지 않는다")
    void guardExcludesOutOfBoardMoves() {
        GuardMovement movement = new GuardMovement();

        Paths paths = movement.findPotentialPaths(pos(Column.A, Row.ZERO));

        assertThat(paths.asList()).hasSize(2);
    }
}
