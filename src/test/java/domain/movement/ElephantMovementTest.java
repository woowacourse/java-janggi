package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardState;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ElephantMovement 클래스 테스트")
class ElephantMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private boolean canReach(Paths paths, Position target) {
        return paths.asList().stream().anyMatch(path -> path.positions().getLast().equals(target));
    }

    private BoardState boardWithBlocked(Position blockedPosition) {
        return StubBoardState.of(Map.of(blockedPosition, new Piece(Team.HAN, PieceType.SOLDIER)));
    }

    @Test
    @DisplayName("상은 열린 경로로 이동할 수 있다")
    void elephantCanReachOpenDestination() {
        ElephantMovement movement = new ElephantMovement();
        Position center = pos(Column.E, Row.FOUR);

        assertThat(movement.findReachablePositions(center, StubBoardState.empty()))
                .contains(pos(Column.C, Row.ONE), pos(Column.G, Row.SEVEN));
    }

    @Test
    @DisplayName("후보 경로가 있어도 중간 칸 둘 중 하나가 막히면 실제로는 도달할 수 없다")
    void blockedIntermediateMakesTargetUnreachable() {
        ElephantMovement movement = new ElephantMovement();
        Position source = pos(Column.E, Row.FOUR);
        Position target = pos(Column.C, Row.ONE);
        BoardState board = boardWithBlocked(pos(Column.D, Row.TWO));

        assertThat(canReach(movement.findPotentialPaths(source), target)).isTrue();
        assertThat(movement.findReachablePositions(source, board)).doesNotContain(target);
    }
}
