package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayingTurnTest {

    @Test
    @DisplayName("첫 번째 차례는 초나라 팀이다.")
    void currentTeam() {
        PlayingTurn playingTurn = new PlayingTurn();

        playingTurn.currentTeam();

        assertAll(
            () -> assertThat(playingTurn.currentTeam()).isEqualTo(Team.CHO),
            () -> assertThat(playingTurn.currentRound()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("턴을 토스하면 다른 팀의 차례가 되고 라운드가 증가한다.")
    void toss() {
        PlayingTurn playingTurn = new PlayingTurn();

        playingTurn.toss();

        assertAll(
            () -> assertThat(playingTurn.currentTeam()).isEqualTo(Team.HAN),
            () -> assertThat(playingTurn.currentRound()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("라운드가 30회를 넘어서면 종료 상태가 된다.")
    void isEnded() {
        PlayingTurn playingTurn = new PlayingTurn();

        for (int i = 1; i <= 30; i++) {
            playingTurn.toss();
        }

        assertThat(playingTurn.isEnded()).isTrue();
    }
}
