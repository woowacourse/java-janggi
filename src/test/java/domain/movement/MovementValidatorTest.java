package domain.movement;

import domain.board.Board;
import domain.board.Column;
import domain.board.Pieces;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Board 이동 검증 테스트")
class MovementValidatorTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> pieceMap) {
        return new Board(new Pieces(pieceMap));
    }

    private Coordinate coord(String input) {
        return Coordinate.toCoordinate(input);
    }

    private Map<Position, Piece> pieces() {
        return new HashMap<>();
    }

    @Test
    @DisplayName("차는 빈 직선 경로로 이동할 수 있다")
    void chariotMovesThroughClearPath() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT));
        Board board = boardWith(map);

        Board moved = board.move(coord("a0 a9"), Team.HAN);

        assertThat(moved.isEmpty(pos(Column.A, Row.ZERO))).isTrue();
        assertThat(moved.findPieceByPosition(pos(Column.A, Row.NINE))).isPresent();
    }

    @Test
    @DisplayName("포는 뛰어넘을 기물이 없으면 이동할 수 없다")
    void cannonCannotMoveWithoutScreen() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
        Board board = boardWith(map);

        assertThatThrownBy(() -> board.move(coord("a0 a9"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("마는 이동 경로가 막히면 이동할 수 없다")
    void horseCannotMoveWhenBlocked() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.HORSE));
        map.put(pos(Column.E, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
        Board board = boardWith(map);

        assertThatThrownBy(() -> board.move(coord("e4 d2"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상은 열린 경로로 이동할 수 있다")
    void elephantMovesWhenPathIsClear() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.ELEPHANT));
        Board board = boardWith(map);

        Board moved = board.move(coord("e4 c1"), Team.HAN);

        assertThat(moved.isEmpty(pos(Column.E, Row.FOUR))).isTrue();
        assertThat(moved.findPieceByPosition(pos(Column.C, Row.ONE))).isPresent();
    }

    @Test
    @DisplayName("궁은 인접한 칸으로 이동할 수 있다")
    void generalMovesToAdjacentPosition() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.GENERAL));
        Board board = boardWith(map);

        Board moved = board.move(coord("e4 e5"), Team.HAN);

        assertThat(moved.isEmpty(pos(Column.E, Row.FOUR))).isTrue();
        assertThat(moved.findPieceByPosition(pos(Column.E, Row.FIVE))).isPresent();
    }

    @Test
    @DisplayName("사는 아군 기물이 있는 칸으로 이동할 수 없다")
    void guardCannotCaptureFriendlyPiece() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.D, Row.THREE), new Piece(Team.HAN, PieceType.GUARD));
        map.put(pos(Column.D, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER));
        Board board = boardWith(map);

        assertThatThrownBy(() -> board.move(coord("d3 d4"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HAN 졸은 후진할 수 없다")
    void hanSoldierCannotMoveBackward() {
        Map<Position, Piece> map = pieces();
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER));
        Board board = boardWith(map);

        assertThatThrownBy(() -> board.move(coord("e4 e3"), Team.HAN))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
