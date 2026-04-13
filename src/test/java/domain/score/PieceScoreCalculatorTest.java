package domain.score;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceScoreCalculatorTest {

    private final PieceScoreCalculator pieceScoreCalculator = new PieceScoreCalculator();

    @Test
    void 현재_보드의_기물을_기준으로_팀별_점수를_계산한다() {
        final Board board = new Board(Map.of(
                Position.of(0, 0), Piece.of(TeamColor.CHO, PieceType.ROOK),
                Position.of(0, 1), Piece.of(TeamColor.CHO, PieceType.PAWN),
                Position.of(9, 0), Piece.of(TeamColor.HAN, PieceType.CANNON),
                Position.of(9, 1), Piece.of(TeamColor.HAN, PieceType.GUARD)
        ));

        final PieceScore pieceScore = pieceScoreCalculator.calculate(board);

        assertThat(pieceScore.cho()).isEqualTo(15.0);
        assertThat(pieceScore.han()).isEqualTo(11.5);
    }

    @Test
    void 한나라에는_기본_가산점_1점5를_추가한다() {
        final Board board = new Board(Map.of(
                Position.of(0, 4), Piece.of(TeamColor.CHO, PieceType.KING),
                Position.of(9, 4), Piece.of(TeamColor.HAN, PieceType.KING)
        ));

        final PieceScore pieceScore = pieceScoreCalculator.calculate(board);

        assertThat(pieceScore.cho()).isEqualTo(0.0);
        assertThat(pieceScore.han()).isEqualTo(1.5);
    }
}
