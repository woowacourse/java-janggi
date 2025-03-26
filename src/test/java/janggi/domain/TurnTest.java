package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @DisplayName("다음 움직일 차례인 팀을 반환한다.")
    @Test
    void nextTurnTeamTest() {
        Turn turn = Turn.startWith(Team.BLUE);
        assertAll(
                () -> assertThat(turn.next()).isEqualTo(Team.RED),
                () -> assertThat(turn.next()).isEqualTo(Team.BLUE)
        );
    }
}