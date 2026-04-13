package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Pieces;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GameResult 클래스 테스트")
class GameResultTest {

    private Position position(Column column, Row row) {
        return new Position(column, row);
    }

    @Test
    @DisplayName("초기 스냅샷과 현재 기물을 비교해 팀별 최종 점수를 계산한다")
    void fromReturnsScoresFromCapturedPieces() {
        Pieces snapshot = new Pieces(Map.of(
                position(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.GENERAL),
                position(Column.B, Row.ZERO), new Piece(Team.HAN, PieceType.GUARD),
                position(Column.C, Row.ZERO), new Piece(Team.HAN, PieceType.SOLDIER),
                position(Column.A, Row.NINE), new Piece(Team.CHO, PieceType.GENERAL),
                position(Column.B, Row.NINE), new Piece(Team.CHO, PieceType.HORSE)
        ));
        Pieces current = new Pieces(Map.of(
                position(Column.D, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER),
                position(Column.C, Row.SEVEN), new Piece(Team.CHO, PieceType.GENERAL)
        ));

        GameResult gameResult = GameResult.from(snapshot, current, Team.CHO);

        assertThat(gameResult.winner()).isEqualTo(Team.CHO);
        assertThat(gameResult.choScore()).isEqualTo(3);
        assertThat(gameResult.hanScore()).isEqualTo(5);
    }
}
