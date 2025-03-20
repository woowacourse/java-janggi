package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 플레이어는_팀을_가진다() {
        Player player = new Player(Team.BLUE);

        Assertions.assertThat(player.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 플레이어는_기물을_가진다() {
        Player player = new Player(Team.BLUE);

        Assertions.assertThat(player.getTeam()).isEqualTo(Team.BLUE);
    }
}
