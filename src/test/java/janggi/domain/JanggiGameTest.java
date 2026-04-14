package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("궁이 잡히면 게임이 종료되고 승자가 결정된다.")
    void gameEndsWhenGungIsCaptured() {
        // given
        JanggiGame janggiGame = JanggiGame.createInitialJanggiGame();

        // when
        janggiGame.doGame(new Position(1, 4), new Position(2, 4));
        janggiGame.doGame(new Position(9, 7), new Position(8, 7));
        janggiGame.doGame(new Position(1, 1), new Position(1, 4));
        janggiGame.doGame(new Position(7, 7), new Position(6, 7));
        janggiGame.doGame(new Position(1, 4), new Position(1, 7));
        janggiGame.doGame(new Position(8, 7), new Position(7, 7));
        janggiGame.doGame(new Position(1, 7), new Position(1, 9));
        janggiGame.doGame(new Position(2, 10), new Position(3, 8));
        janggiGame.doGame(new Position(1, 9), new Position(5, 9));

        // then
        assertAll(
            () -> assertThat(janggiGame.isGameOver()).isTrue(),
            () -> assertThat(janggiGame.findWinner()).contains(TeamType.CHU)
        );
    }
}
