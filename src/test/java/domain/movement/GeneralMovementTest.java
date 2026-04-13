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
    @DisplayName("궁은 궁성 중앙에서 상하좌우와 대각선으로 이동할 수 있다")
    void generalMovesInsidePalaceWithDiagonals() {
        GeneralMovement movement = new GeneralMovement();
        Position center = pos(Column.E, Row.ONE);

        List<Position> destinations = movement.findReachablePositions(center, emptyBoard());

        assertThat(destinations).containsExactlyInAnyOrder(
                pos(Column.E, Row.ZERO),
                pos(Column.E, Row.TWO),
                pos(Column.D, Row.ONE),
                pos(Column.F, Row.ONE),
                pos(Column.D, Row.ZERO),
                pos(Column.F, Row.ZERO),
                pos(Column.D, Row.TWO),
                pos(Column.F, Row.TWO)
        );
    }

    @Test
    @DisplayName("궁은 궁성 밖에서는 이동할 수 없다")
    void generalCannotMoveOutsidePalace() {
        GeneralMovement movement = new GeneralMovement();
        Position topLeft = pos(Column.A, Row.ZERO);

        Paths paths = movement.findPotentialPaths(topLeft);

        assertThat(paths.asList()).isEmpty();
        assertThat(canReach(paths, pos(Column.A, Row.ONE))).isFalse();
    }
}
