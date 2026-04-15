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

@DisplayName("CannonMovement 클래스 테스트")
class CannonMovementTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private BoardState boardWith(Map<Position, Piece> pieces) {
        return StubBoardState.of(pieces);
    }

    @Test
    @DisplayName("포는 비포 기물 하나를 넘어서 이동할 수 있다")
    void cannonCanJumpOverSingleScreen() {
        CannonMovement movement = new CannonMovement();
        Position source = pos(Column.E, Row.FOUR);
        BoardState board = boardWith(Map.of(
                pos(Column.E, Row.TWO), new Piece(Team.HAN, PieceType.SOLDIER),
                pos(Column.E, Row.ZERO), new Piece(Team.CHO, PieceType.GUARD)
        ));

        List<Position> destinations = movement.findReachablePositions(source, board);

        assertThat(destinations).contains(pos(Column.E, Row.ONE), pos(Column.E, Row.ZERO));
        assertThat(destinations).doesNotContain(pos(Column.E, Row.THREE), pos(Column.E, Row.TWO));
    }

    @Test
    @DisplayName("포는 넘을 기물이 없으면 이동할 수 없다")
    void cannonCannotMoveWithoutScreen() {
        CannonMovement movement = new CannonMovement();
        Position source = pos(Column.E, Row.FOUR);

        List<Position> destinations = movement.findReachablePositions(source, boardWith(Map.of()));

        assertThat(destinations).isEmpty();
    }

    @Test
    @DisplayName("포는 포를 넘거나 포를 도착지로 삼지 못한다")
    void cannonCannotUseCannonAsScreenOrTarget() {
        CannonMovement movement = new CannonMovement();
        Position source = pos(Column.E, Row.FOUR);

        BoardState screenIsCannon = boardWith(Map.of(
                pos(Column.E, Row.TWO), new Piece(Team.HAN, PieceType.CANNON)
        ));
        assertThat(movement.findReachablePositions(source, screenIsCannon)).isEmpty();

        BoardState targetIsCannon = boardWith(Map.of(
                pos(Column.E, Row.TWO), new Piece(Team.HAN, PieceType.SOLDIER),
                pos(Column.E, Row.ZERO), new Piece(Team.CHO, PieceType.CANNON)
        ));
        assertThat(movement.findReachablePositions(source, targetIsCannon)).doesNotContain(
                pos(Column.E, Row.ZERO));
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 가운데 기물을 넘을 수 있다")
    void cannonCanJumpOnPalaceDiagonal() {
        CannonMovement movement = new CannonMovement();
        Position source = pos(Column.D, Row.ZERO);
        BoardState board = boardWith(Map.of(
                pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.SOLDIER),
                pos(Column.F, Row.TWO), new Piece(Team.CHO, PieceType.GUARD)
        ));

        List<Position> destinations = movement.findReachablePositions(source, board);

        assertThat(destinations).contains(pos(Column.F, Row.TWO));
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 가운데 기물이 없으면 이동할 수 없다")
    void cannonCannotMoveOnPalaceDiagonalWithoutBridge() {
        CannonMovement movement = new CannonMovement();
        Position source = pos(Column.D, Row.ZERO);
        BoardState board = boardWith(Map.of(
                pos(Column.F, Row.TWO), new Piece(Team.CHO, PieceType.GUARD)
        ));

        List<Position> destinations = movement.findReachablePositions(source, board);

        assertThat(destinations).doesNotContain(pos(Column.F, Row.TWO));
    }
}
