package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardState;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ChariotMovement 클래스 테스트")
class ChariotMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private BoardState boardWith(Map<Position, Piece> pieces) {
        return StubBoardState.of(pieces);
    }

    @Test
    @DisplayName("차는 빈 직선 경로로 이동할 수 있다")
    void chariotMovesAlongClearLine() {
        ChariotMovement movement = new ChariotMovement();
        Position source = pos(Column.E, Row.FOUR);

        List<Position> destinations = movement.findReachablePositions(source, boardWith(Map.of()));

        assertThat(destinations).contains(pos(Column.E, Row.ZERO), pos(Column.I, Row.FOUR));
    }

    @Test
    @DisplayName("차는 경로 중간에 기물이 있으면 그 너머로 이동할 수 없다")
    void chariotStopsAtBlockingPiece() {
        ChariotMovement movement = new ChariotMovement();
        Position source = pos(Column.E, Row.FOUR);
        BoardState board = boardWith(Map.of(
                pos(Column.E, Row.TWO), new Piece(Team.HAN, PieceType.SOLDIER)
        ));

        List<Position> destinations = movement.findReachablePositions(source, board);

        assertThat(destinations).contains(pos(Column.E, Row.THREE), pos(Column.E, Row.TWO));
        assertThat(destinations).doesNotContain(pos(Column.E, Row.ONE), pos(Column.E, Row.ZERO));
    }

    @Test
    @DisplayName("차는 궁성 중앙에서 대각선으로 이동할 수 있다")
    void chariotMovesDiagonallyFromPalaceCenter() {
        ChariotMovement movement = new ChariotMovement();
        Position source = pos(Column.E, Row.ONE);

        List<Position> destinations = movement.findReachablePositions(source, boardWith(Map.of()));

        assertThat(destinations).contains(
                pos(Column.D, Row.ZERO),
                pos(Column.F, Row.ZERO),
                pos(Column.D, Row.TWO),
                pos(Column.F, Row.TWO)
        );
    }

    @Test
    @DisplayName("차는 궁성 꼭짓점에서 대각선으로 이동할 수 있다")
    void chariotMovesDiagonallyFromPalaceCorner() {
        ChariotMovement movement = new ChariotMovement();
        Position source = pos(Column.D, Row.ZERO);

        List<Position> destinations = movement.findReachablePositions(source, boardWith(Map.of()));

        assertThat(destinations).contains(pos(Column.E, Row.ONE), pos(Column.F, Row.TWO));
    }
}
