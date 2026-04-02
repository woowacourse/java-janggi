package domain.rule;

import domain.board.Board;
import domain.board.Column;
import domain.board.Pieces;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CheckDetector 클래스 테스트")
class CheckDetectorTest {

    private final CheckDetector checkDetector = new CheckDetector();

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> pieceMap) {
        return new Board(new Pieces(pieceMap));
    }

    @Test
    @DisplayName("아무 기물도 장군을 위협하지 않으면 false를 반환한다")
    void noCheckWhenGeneralIsSafe() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isFalse();
        assertThat(checkDetector.isInCheck(board, Team.CHO)).isFalse();
    }

    @Test
    @DisplayName("공격 기물과 장군 사이에 기물이 있으면 장군이 아니다")
    void noCheckWhenBlockedByPiece() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.HAN, PieceType.SOLDIER));
        map.put(pos(Column.E, Row.NINE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("차가 장군과 같은 열에서 사이에 기물 없이 위협하면 장군이다")
    void checkByChariotOnSameColumn() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("차가 장군과 같은 행에서 사이에 기물 없이 위협하면 장군이다")
    void checkByChariotOnSameRow() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.A, Row.ONE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("포가 장군과 같은 열에서 기물 1개를 넘어 위협하면 장군이다")
    void checkByCannonJumpingOverOnePiece() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.THREE), new Piece(Team.HAN, PieceType.SOLDIER));
        map.put(pos(Column.E, Row.SIX), new Piece(Team.CHO, PieceType.CANNON));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("포가 장군과 같은 열에서 기물이 없으면 장군이 아니다")
    void noCheckByCannonWithNoPieceToJump() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.SIX), new Piece(Team.CHO, PieceType.CANNON));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("초 장군이 위협받는 상황도 확인한다")
    void checkAgainstChoGeneral() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkDetector.isInCheck(board, Team.CHO)).isTrue();
        assertThat(checkDetector.isInCheck(board, Team.HAN)).isFalse();
    }
}
