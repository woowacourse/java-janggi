package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Board 점수 계산 테스트")
class ScoreCalculationTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> map) {
        return new Board(new Pieces(map));
    }

    @Test
    @DisplayName("한 팀 점수는 기물 합산 + 덤 1.5점이다")
    void hanScoreIncludesDeom() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.calculateScore(Team.HAN)).isEqualTo(1.5);
    }

    @Test
    @DisplayName("초 팀 점수에는 덤이 없다")
    void choScoreHasNoDeom() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.calculateScore(Team.CHO)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("기물 점수가 올바르게 합산된다")
    void pieceScoresAreCorrectlySummed() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT));
        map.put(pos(Column.B, Row.TWO), new Piece(Team.HAN, PieceType.CANNON));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.calculateScore(Team.HAN)).isEqualTo(21.5);
    }

    @Test
    @DisplayName("장군만 남으면 기물 부족이다")
    void generalOnlyIsInsufficient() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.hasInsufficientPieces(Team.HAN)).isTrue();
        assertThat(board.hasInsufficientPieces(Team.CHO)).isTrue();
    }

    @Test
    @DisplayName("장군 + 사만 남으면 기물 부족이다")
    void generalAndGuardIsInsufficient() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.D, Row.ONE), new Piece(Team.HAN, PieceType.GUARD));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.hasInsufficientPieces(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("공격 기물이 하나라도 있으면 기물 부족이 아니다")
    void withAttackingPieceIsNotInsufficient() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
        map.put(pos(Column.A, Row.THREE), new Piece(Team.HAN, PieceType.SOLDIER));
        map.put(pos(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL));
        Board board = boardWith(map);

        assertThat(board.hasInsufficientPieces(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("한 점수가 높으면 한 승리이다")
    void hanWinsWhenHigherScore() {
        assertThat(domain.state.GameResult.fromScore(73.5, 72.0))
                .isEqualTo(domain.state.GameResult.WIN_HAN);
    }

    @Test
    @DisplayName("초 점수가 높으면 초 승리이다")
    void choWinsWhenHigherScore() {
        assertThat(domain.state.GameResult.fromScore(10.0, 20.0))
                .isEqualTo(domain.state.GameResult.WIN_CHO);
    }

    @Test
    @DisplayName("점수가 같으면 무승부이다")
    void drawWhenEqualScore() {
        assertThat(domain.state.GameResult.fromScore(5.0, 5.0))
                .isEqualTo(domain.state.GameResult.DRAW);
    }
}
