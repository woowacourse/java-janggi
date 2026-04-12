package janggi.domain.result;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreResultTest {

    @Test
    @DisplayName("기물 목록을 받으면 각 진영의 기물 총점수를 계산한 결과를 반환한다")
    void shouldReturnScoreSumCalculatedBySide() {
        // given
        List<Piece> alivePieces = List.of(
                new TestPiece(PieceType.CHA, Side.CHO),
                new TestPiece(PieceType.PO, Side.HAN)
        );
        ScoreResult scoreResult = ScoreResult.calculate(alivePieces);

        // when & then
        Assertions.assertThat(scoreResult.getScoreOf(Side.CHO)).isEqualTo(13);
        Assertions.assertThat(scoreResult.getScoreOf(Side.HAN)).isEqualTo(8.5);
    }
}
