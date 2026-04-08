package domain.state;

import domain.board.Board;
import domain.board.Column;
import domain.board.Pieces;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.rule.CheckmateDetector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("무승부 조건 감지 테스트")
class DrawDetectionTest {

    private final CheckmateDetector checkmateDetector = new CheckmateDetector();

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> map) {
        return new Board(new Pieces(map));
    }

    @Test
    @DisplayName("양 팀 모두 장군만 남으면 양 팀 기물 부족 조건이 충족된다")
    void bothTeamsInsufficientWhenOnlyGenerals() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.hasInsufficientPieces(Team.HAN)).isTrue();
        assertThat(board.hasInsufficientPieces(Team.CHO)).isTrue();
    }

    @Test
    @DisplayName("한 팀만 기물 부족이면 양 팀 조건이 충족되지 않는다")
    void notBothInsufficientWhenOneTeamHasAttackPiece() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.A, Row.NINE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(board.hasInsufficientPieces(Team.HAN)).isTrue();
        assertThat(board.hasInsufficientPieces(Team.CHO)).isFalse();
    }

    @Test
    @DisplayName("이동 가능한 수가 있으면 hasNoLegalMoves는 false이다")
    void hasLegalMovesReturnsFalse() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(checkmateDetector.hasNoLegalMoves(board, Team.HAN)).isFalse();
        assertThat(checkmateDetector.hasNoLegalMoves(board, Team.CHO)).isFalse();
    }

    @Test
    @DisplayName("장군이 궁성 코너에 갇혀 모든 이동지가 막히면 합법적인 수가 없다")
    void noLegalMovesWhenGeneralIsTrapped() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.D, Row.ZERO), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.D, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.hasNoLegalMoves(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("isCheckmate는 체크 상태 + 합법적인 수 없음일 때만 true이다")
    void checkmateRequiresBothCheckAndNoMoves() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.D, Row.ZERO), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.D, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        Board board = boardWith(map);

        assertThat(checkmateDetector.isCheckmate(board, Team.HAN)).isTrue();
        assertThat(checkmateDetector.hasNoLegalMoves(board, Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("체크는 아니지만 합법적인 수가 없으면 isCheckmate는 false, hasNoLegalMoves는 true이다")
    void noMovesWithoutCheckIsNotCheckmate() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.D, Row.ZERO), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.CHARIOT));
        map.put(pos(Column.D, Row.ONE), new Piece(Team.CHO, PieceType.GUARD));
        Board board = boardWith(map);

        assertThat(checkmateDetector.hasNoLegalMoves(board, Team.HAN)).isFalse();
    }
}
