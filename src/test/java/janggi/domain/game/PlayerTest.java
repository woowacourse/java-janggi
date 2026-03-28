package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @DisplayName("주어진 턴이 자신의 진영 턴인지 확인한다")
    @Test
    void isMyTurn_ReturnsTrueIfTurnMatches() {
        Player player = new Player("플레이어1", Side.CHO);
        Turn turn = Turn.init();

        assertThat(player.isMyTurn(turn)).isTrue();
    }

    @DisplayName("주어진 기물이 자신의 진영 기물인지 확인한다")
    @Test
    void isOwnPiece_ReturnsTrueIfPieceMatchesSide() {
        Player player = new Player("플레이어1", Side.CHO);
        Piece piece = new Piece(Side.CHO, PieceType.CHO_SOLDIER, "0");

        assertThat(player.isOwnPiece(piece)).isTrue();
    }
}
