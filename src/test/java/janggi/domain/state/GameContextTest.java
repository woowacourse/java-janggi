package janggi.domain.state;


import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameContextTest {

    @Nested
    class GungRemovedTest {

        @Test
        @DisplayName("초팀의 왕이 잡힌 경우, 한팀이 승리한다.")
        void winCho_WhenHanGungRemoved() {
            // given
            List<Piece> alivePieces = List.of(
                    new Gung(Side.CHO),
                    new Gung(Side.HAN),
                    new TestPiece(Side.CHO),
                    new TestPiece(Side.HAN)
            );
            GameContext gameContext = GameContext.createInProgress(alivePieces, Side.CHO);

            // when
            Piece removed = new Gung(Side.HAN);
            gameContext.update(removed);

            // then
            Assertions.assertThat(gameContext.isInProgress()).isFalse();
            Assertions.assertThat(gameContext.getWinner()).isEqualTo(Side.CHO);
        }

        @Test
        @DisplayName("한팀의 왕이 잡힌 경우, 초팀이 승리한다.")
        void winHan_WhenChoGungRemoved() {
            // given
            List<Piece> alivePieces = List.of(
                    new Gung(Side.CHO),
                    new Gung(Side.HAN),
                    new TestPiece(Side.CHO),
                    new TestPiece(Side.HAN)
            );
            GameContext gameContext = GameContext.createInProgress(alivePieces, Side.HAN);

            // when
            Piece removed = new Gung(Side.CHO);
            gameContext.update(removed);

            // then
            Assertions.assertThat(gameContext.isInProgress()).isFalse();
            Assertions.assertThat(gameContext.getWinner()).isEqualTo(Side.HAN);
        }

        @Test
        @DisplayName("양팀의 궁이 살아있는 경우, 게임은 계속 지속된다.")
        void continueGame_WhenNonGungRemoved() {
            // given
            List<Piece> alivePieces = List.of(
                    new Gung(Side.CHO),
                    new Gung(Side.HAN),
                    new TestPiece(Side.CHO),
                    new TestPiece(Side.HAN)
            );
            GameContext gameContext = GameContext.createInProgress(alivePieces, Side.HAN);

            // when
            Piece removed = EmptyPiece.getInstance();
            gameContext.update(removed);

            // then
            Assertions.assertThat(gameContext.isInProgress()).isTrue();
        }
    }
}
