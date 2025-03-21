package game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import piece.Team;

class PlayerTest {

    @Test
    void 플레이어는_팀을_가진다() {
        Player player = new Player(Team.BLUE);

        assertThat(player.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 플레이어는_기물을_가진다() {
        Player player = new Player(Team.BLUE);

        assertThat(player.getTeam()).isEqualTo(Team.BLUE);
    }

}
