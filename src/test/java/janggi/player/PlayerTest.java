package janggi.player;

import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @Test
    @DisplayName("승리를 확인할 수 있다")
    void isWin() {
        // given
        final Player winner = Player.of(Team.CHO, Pieces.empty(), Score.general());
        final Player notYet = Player.of(Team.HAN, Pieces.empty(), Score.from(1));

        // when
        final boolean winResult = winner.isWin();
        final boolean notYetResult = notYet.isWin();

        // then
        assertThat(winResult).isTrue();
        assertThat(notYetResult).isFalse();
    }
}
