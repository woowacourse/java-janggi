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

@DisplayName("HorseMovement 클래스 테스트")
class HorseMovementTest {

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
    @DisplayName("마는 기본 L자 이동 후보를 가진다")
    void horseCanReachLShapedDestination() {
        HorseMovement movement = new HorseMovement();
        Position center = pos(Column.E, Row.FOUR);

        assertThat(canReach(movement.findPotentialPaths(center), pos(Column.D, Row.TWO))).isTrue();
    }

    @Test
    @DisplayName("후보 경로가 있어도 첫 중간 칸이 막히면 실제로는 도달할 수 없다")
    void blockedIntermediateMakesTargetUnreachable() {
        HorseMovement movement = new HorseMovement();
        Position source = pos(Column.E, Row.FOUR);
        Position target = pos(Column.D, Row.TWO);
        BoardState board = boardWithBlocked(pos(Column.E, Row.THREE));

        assertThat(canReach(movement.findPotentialPaths(source), target)).isTrue();
        assertThat(movement.findReachablePositions(source, board)).doesNotContain(target);
    }
}
