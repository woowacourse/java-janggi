package janggi.domain.result;

import janggi.domain.Side;
import janggi.domain.piece.AlivePieces;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GameResultTest {

    @Test
    @DisplayName("기물 목록을 받으면 각 진영별 점수를 계산한 결과를 반환한다")
    void shouldReturnTeamScoreCalculatedBySide() {
        // given
        List<Piece> alivePieces = List.of(
                new TestPiece(PieceType.CHA, Side.CHO),
                new TestPiece(PieceType.PO, Side.HAN)
        );
        GameResult gameResult = GameResult.calculate(AlivePieces.from(alivePieces));

        // when & then
        Assertions.assertThat(gameResult.getScoreOf(Side.CHO)).isEqualTo(13);
        Assertions.assertThat(gameResult.getScoreOf(Side.HAN)).isEqualTo(8.5);
    }

    @Nested
    class GetWinnerTest {
        @ParameterizedTest
        @DisplayName("궁이 잡힌 진영이 존재한다면, 궁이 생존해있는 승리 진영을 반환한다")
        @MethodSource("provideSide")
        void shouldReturnWinnerSideWhenWinnerExists(Side winnerSide) {
            // given
            List<Piece> alivePieces = List.of(
                    new TestPiece(PieceType.CHA, winnerSide.switchSide()),
                    new TestPiece(PieceType.GUNG, winnerSide)
            );
            GameResult gameResult = GameResult.calculate(AlivePieces.from(alivePieces));

            // when & then
            Assertions.assertThat(gameResult.getWinner()).isEqualTo(winnerSide);
        }

        static List<Side> provideSide() {
            return List.of(
                    Side.HAN,
                    Side.CHO
            );
        }

        @Test
        @DisplayName("양 진영의 궁이 생존해있다면, 승리 진영을 NONE으로 반환한다")
        void shouldReturnWinnerSideAsNoneWhenGungOfBothSideAlive() {
            // given
            List<Piece> alivePieces = List.of(
                    new TestPiece(PieceType.GUNG, Side.CHO),
                    new TestPiece(PieceType.GUNG, Side.HAN)
            );
            GameResult gameResult = GameResult.calculate(AlivePieces.from(alivePieces));

            // when & then
            Assertions.assertThat(gameResult.getWinner()).isEqualTo(Side.NONE);
        }
    }
}
