package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @DisplayName("두 플레이어의 이름이 중복되면 예외가 발생한다")
    @Test
    void from_DuplicatedNames_ThrowsException() {
        assertThatThrownBy(() -> Players.from("중복이름", "중복이름"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("현재 턴을 가진 플레이어를 정확히 찾아 반환한다")
    @Test
    void currentPlayer_ReturnsPlayerMatchingTurn() {
        Players players = Players.from("초나라", "한나라");
        players.nextTurn();

        Player current = players.currentPlayer();

        boolean isHanPlayer = current.side() == Side.HAN;
        assertThat(isHanPlayer).isTrue();
    }

    @DisplayName("선택한 기물이 현재 턴을 가진 플레이어의 기물인지 판별한다")
    @Test
    void isCurrentSidePiece_MatchesTurnAndPieceSide_ReturnsTrue() {
        Players players = Players.from("초나라", "한나라");
        Turn turn = Turn.init();
        Piece choPiece = new Piece(Side.CHO, PieceType.SOLDIER, "0");

        assertThat(players.isCurrentSidePiece(choPiece)).isTrue();
    }
}
