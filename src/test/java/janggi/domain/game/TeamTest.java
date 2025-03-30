package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.game.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    @DisplayName("상대 팀을 구할 수 있다.")
    @Test
    void testOppositeTeam() {
        // given
        Team team = Team.HAN;
        // when
        Team opposite = team.opposite();
        // then
        assertThat(opposite).isEqualTo(Team.CHO);
    }
}
