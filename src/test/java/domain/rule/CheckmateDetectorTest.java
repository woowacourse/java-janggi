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

@DisplayName("CheckmateDetector 클래스 테스트")
class CheckmateDetectorTest {

    private final CheckmateDetector checkmateDetector = new CheckmateDetector();

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> pieceMap) {
        return new Board(new Pieces(pieceMap));
    }

    @Test
    @DisplayName("체크 상태가 아니면 외통이 아니다")
    void notCheckmateWhenNotInCheck() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("체크 상태이지만 장군이 이동해 피할 수 있으면 외통이 아니다")
    void notCheckmateWhenGeneralCanEscape() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("아군이 공격 기물을 잡아 체크를 막을 수 있으면 외통이 아니다")
    void notCheckmateWhenCanCaptureAttacker() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.D, Row.ONE), new Piece(Team.HAN, PieceType.GUARD));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.TWO), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("아군이 공격 경로를 막을 수 있으면 외통이 아니다")
    void notCheckmateWhenCanBlock() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.D, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.NINE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("모든 이동이 장군 상태를 피하지 못하면 외통이다")
    void checkmateWhenNoEscape() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.D, Row.ZERO), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.D, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("초 진영도 외통이 정상적으로 작동한다.")
    void checkmateAgainstChoGeneral() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.CHARIOT));
        map.put(pos(Column.A, Row.SEVEN), new Piece(Team.HAN, PieceType.CHARIOT));
        map.put(pos(Column.A, Row.EIGHT), new Piece(Team.HAN, PieceType.CHARIOT));
        map.put(pos(Column.A, Row.NINE), new Piece(Team.HAN, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.CHO)).isTrue();
    }
}
