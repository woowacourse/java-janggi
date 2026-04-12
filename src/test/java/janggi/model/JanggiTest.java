package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.board.PlayingBoard;
import janggi.model.turn.playing.ChoTurn;
import janggi.model.turn.playing.HanTurn;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @DisplayName("진행하던 게임의 다음 턴부터 시작한다.")
    @Test
    void continueFrom() {
        assertThat(Janggi.continueFrom(
                        new ChoTurn(PlayingBoard.of(Map.of()))
                ).getCurrentTeam()
        ).isEqualTo(Team.HAN);

        assertThat(Janggi.continueFrom(
                        new HanTurn(PlayingBoard.of(Map.of()))
                ).getCurrentTeam()
        ).isEqualTo(Team.CHO);
    }
}
