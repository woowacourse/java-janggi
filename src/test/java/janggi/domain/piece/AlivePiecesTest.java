package janggi.domain.piece;

import janggi.domain.Side;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlivePiecesTest {

    @Test
    @DisplayName("진영별 기물 점수의 총합을 반환한다")
    void shouldReturnScoreSumOfPiecesCalculatedBySide() {
        // given
        List<Piece> pieces = List.of(
                new TestPiece(PieceType.CHA, Side.CHO),
                new TestPiece(PieceType.PO, Side.HAN)
        );
        AlivePieces alivePieces = AlivePieces.from(pieces);

        // when & then
        Assertions.assertThat(alivePieces.calculateScoreSum(Side.CHO)).isEqualTo(13);
        Assertions.assertThat(alivePieces.calculateScoreSum(Side.HAN)).isEqualTo(7);
    }

    @Nested
    class IsEveryGungAliveTest {
        @Test
        @DisplayName("양 진영의 궁이 모두 살아있으면 True를 반환한다.")
        void shouldReturnTrueWhenGungOfBothSideAlive() {
            // given
            List<Piece> alivePieces = List.of(
                    new TestPiece(PieceType.GUNG, Side.CHO),
                    new TestPiece(PieceType.GUNG, Side.HAN)
            );

            // when & then
            Assertions.assertThat(AlivePieces.from(alivePieces).isEveryGungAlive()).isTrue();
        }

        @Test
        @DisplayName("한 진영의 궁이 잡혔다면 False를 반환한다.")
        void shouldReturnFalseWhenGungOfOneSideDied() {
            // given
            List<Piece> alivePieces = List.of(
                    new TestPiece(PieceType.CHA, Side.CHO),
                    new TestPiece(PieceType.GUNG, Side.HAN)
            );

            // when & then
            Assertions.assertThat(AlivePieces.from(alivePieces).isEveryGungAlive()).isFalse();
        }
    }
}
