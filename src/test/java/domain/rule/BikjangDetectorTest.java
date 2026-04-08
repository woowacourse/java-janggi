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

@DisplayName("BikjangDetector 클래스 테스트")
class BikjangDetectorTest {

    private final BikjangDetector bikjangDetector = new BikjangDetector();

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> pieceMap) {
        return new Board(new Pieces(pieceMap));
    }

    @Test
    @DisplayName("두 장군이 다른 열에 있으면 빅장이 아니다")
    void notBikjangWhenGeneralsOnDifferentColumns() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.D, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isFalse();
    }

    @Test
    @DisplayName("같은 열이지만 사이에 기물이 있으면 빅장이 아니다")
    void notBikjangWhenPieceBetweenGenerals() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isFalse();
    }

    @Test
    @DisplayName("같은 열이지만 사이에 적 기물이 있어도 빅장이 아니다")
    void notBikjangWhenEnemyPieceBetweenGenerals() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isFalse();
    }

    @Test
    @DisplayName("같은 열에서 사이에 기물이 없으면 빅장이다")
    void bikjangWhenGeneralsOnSameColumnWithNoPieceBetween() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isTrue();
    }

    @Test
    @DisplayName("e열이 아닌 다른 열에서도 빅장을 감지한다")
    void bikjangOnNonDefaultColumn() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.D, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.D, Row.SEVEN), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isTrue();
    }

    @Test
    @DisplayName("사이에 기물이 있다가 제거되면 빅장 상태가 된다")
    void bikjangAfterBlockingPieceIsRemoved() {
        // 사이 기물 없는 상태로 직접 구성
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.TWO), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.SEVEN), new Piece(Team.CHO, PieceType.GENERAL));
        // e3~e6 사이 비어있음
        Board board = boardWith(map);

        assertThat(bikjangDetector.isBikjang(board)).isTrue();
    }
}
